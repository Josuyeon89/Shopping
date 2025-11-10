package com.kt.common;

public class Preconditions {
    public static void vaildate (boolean expression, ErrorCode errorCode) {
        if (!expression) {
            throw new CustomException(errorCode);
        }
    }
}
