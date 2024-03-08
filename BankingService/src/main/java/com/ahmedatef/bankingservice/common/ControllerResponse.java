package com.ahmedatef.bankingservice.common;

import lombok.*;

import java.lang.reflect.Constructor;
import java.util.List;

@Getter
public class ControllerResponse<T> {
    private final List<T> data;
    private final int count;
    private final String message;

    private ControllerResponse(List<T> data, int count, String message) {
        this.data = data;
        this.count = count;
        this.message = message;
    }

    @SneakyThrows
    public static <T> ControllerResponse<T> createExceptionResponse(String message) {
        return new ControllerResponse<>(null, 0, message);
    }

    public static <T> ControllerResponse<T> of(List<T> data) {
        return new ControllerResponse<>(data, data.size(), null);
    }
}
