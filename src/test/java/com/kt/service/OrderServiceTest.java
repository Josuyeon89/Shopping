package com.kt.service;

import com.kt.domain.order.Order;
import com.kt.domain.product.Product;
import com.kt.domain.user.Gender;
import com.kt.domain.user.Role;
import com.kt.domain.user.User;
import com.kt.repository.order.OrderRepository;
import com.kt.repository.orderproduct.OrderProductRepository;
import com.kt.repository.product.ProductRepository;
import com.kt.repository.user.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class OrderServiceTest {

    @Autowired
    private OrderService orderService;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private OrderProductRepository orderProductRepository;

    // 동시성 제어할 때 -> Lock을 걸어서 처리해야한다.
    /*
     * 1. 비관적 락(Pessimistic Lock) : DB에서 지원해주는 Lock, SELECT ... FOR UPDATE -> 화장실에 한 명이 들어가면 나올때까지 계속 대기
     *                               단점 : 시간이 오래 걸림, 무한 대기로 인해 데드락 발생 가능성
     * 2. 낙관적 락(Optimistic Lock) : 버전 관리, 문제가 발생할 확률이 낮다는 전제하에 동시 접근 허용, 이후 충돌 발생 시 롤백 처리
     *                               작업이 끝나고 처음 상태와 끝났을 때의 상태를 비교. 두 버전이 다르다면 작업 실패 -> 재시도
     * 3. 분산락(Distributed Lock) : ex)Redis, 타임아웃 설정. 화장실에 한명씩 들어가는데 앞 사람이 오래걸리면 그냥 끌고 나와서 내가 들어감
     *                             타임아웃 설정 후 설정 시간이 지나면 삭제
     * */

    @BeforeEach
    void setUp() {
        orderRepository.deleteAll();
        productRepository.deleteAll();
        userRepository.deleteAll();
        orderProductRepository.deleteAll();
    }

    @Test
    @Transactional
    void 주문_생성 () {
        // given
        User user = userRepository.save(
                new User(
                        "testuser",
                        "password",
                        "Test User",
                        "email",
                        "010-0000-0000",
                        Gender.FEMALE,
                        LocalDate.now(),
                        LocalDateTime.now(),
                        LocalDateTime.now(),
                        Role.USER
                ));

        Product product = productRepository.save(new Product(
                "테스트 상품명",
                100_000L,
                10L
        ));

        orderService.create(
                user.getId(),
                product.getId(),
                "수신자 이름",
                "수신자 주소",
                "010-1234-1234",
                2L
        );

        // then
        Product foundedProduct = productRepository.findByIdOrThrow(product.getId());
        Optional<Order> foundedOrder = orderRepository.findAll().stream().findFirst();

        assertThat(foundedProduct.getStock()).isEqualTo(8L);

        assertThat(foundedProduct.getOrderProducts()).isNotEmpty();

        assertThat(foundedOrder).isPresent();
    }

    @Test
    void 동시에_100명_주문 () throws InterruptedException {
        ArrayList<User> userList = new ArrayList<User>();
        for(int i=0 ; i<100 ; i++){
            userList.add(new User(
                    "testuser"+i,
                    "password",
                    "Test User"+i,
                    "email"+i,
                    "010-2222-1111"+i,
                    Gender.FEMALE,
                    LocalDate.now(),
                    LocalDateTime.now(),
                    LocalDateTime.now(),
                    Role.USER
            ));
        }

        List<User> user = userRepository.saveAll(userList);

        Product product = productRepository.save(new Product(
                "테스트 상품명",
                100_000L,
                10L
        ));
        productRepository.flush();

        // 동시 주문위해 쓰레드 100개
        ExecutorService executorService = Executors.newFixedThreadPool(100);
        CountDownLatch countDownLatch = new CountDownLatch(100);   //100개의 작업이 끝날때까지 기다림
        AtomicInteger successCount = new AtomicInteger(0);
        AtomicInteger failCount = new AtomicInteger(0);


        // 100명의 유저가 동시에 같은 상품을 1개씩 주문 시도
        for(int i=0 ; i<100 ; i++){
            int finalI = i;
            executorService.submit(() -> {
                try {
                    var targetUser = user.get(finalI);
                    orderService.create(
                            targetUser.getId(),
                            product.getId(),
                            targetUser.getName(),
                            "수신자 주소 "+finalI,
                            "010-1234-12"+finalI,
                            1L
                    );
                    successCount.incrementAndGet();
                } catch (RuntimeException e) {
                    e.printStackTrace();
                    failCount.incrementAndGet();
                } finally {
                    countDownLatch.countDown();
                }
            });
        }

        countDownLatch.await();
        executorService.shutdown();

        Product foundedProduct = productRepository.findByIdOrThrow(product.getId());

        System.out.println("성공한 주문 수 :"+successCount.get());
        System.out.println("실패한 주문 수 :"+failCount.get());
        System.out.println("남은 재고 수 : "+foundedProduct.getStock());

        // 추가 테스트
        assertThat(successCount.get())
                .as("재고 10개이므로 성공 주문 수는 10이어야 한다")
                .isEqualTo(10);

        assertThat(failCount.get())
                .as("나머지 90명은 실패해야 한다")
                .isEqualTo(90);

        assertThat(foundedProduct.getStock())
                .as("모든 재고가 소진되어야 한다")
                .isEqualTo(0L);

        assertThat(orderRepository.count())
                .as("실제 생성된 주문 개수도 성공 건수와 같아야 한다")
                .isEqualTo(10L);
    }
}