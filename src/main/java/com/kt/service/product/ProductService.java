package com.kt.service.product;

import com.kt.domain.product.Product;
import com.kt.repository.product.ProductRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@Transactional  // jpa
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    public void create(String name, Long price, Long quantity) {
        new Product(
                name,
                price,
                quantity
        );
    }


    public void update(Long id, String name, Long price, Long quantity) {
        var product = productRepository.findByIdOrThrow(id);

        product.update(name, price, quantity);
    }

    public void soldOut (Long id) {
        var product = productRepository.findByIdOrThrow(id);

        product.soldOut();
    }

    public void delete(Long id) {
        var product = productRepository.findByIdOrThrow(id);

        product.deleted();
    }

    public void inActivate(Long id) {
        var product = productRepository.findByIdOrThrow(id);

        product.inActivated();
    }

    public void activate(Long id) {
        var product = productRepository.findByIdOrThrow(id);
        product.activated();
    }

    public void decreaseStock(Long id, Long quantity) {
        var product = productRepository.findByIdOrThrow(id);

        product.decreaseStock(quantity);
    }

    public void increaseStock(Long id, Long quantity) {
        var product = productRepository.findByIdOrThrow(id);

        product.increaseStock(quantity);
    }
}
