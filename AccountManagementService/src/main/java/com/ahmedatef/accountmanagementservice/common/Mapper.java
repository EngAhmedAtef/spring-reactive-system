package com.ahmedatef.accountmanagementservice.common;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;

public final class Mapper {
    private static final ObjectMapper objectMapper = new ObjectMapper();

    @SneakyThrows
    public static <F, T> T map(F object, Class<T> targetType) {
        String fromJson = objectMapper.writeValueAsString(object);
        return objectMapper.readValue(fromJson, targetType);
    }

}
