package com.ahmedatef.notificationservice.common;

import lombok.Getter;
import lombok.SneakyThrows;

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
