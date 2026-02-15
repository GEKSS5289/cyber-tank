package com.cyber.tank.common.core.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

/**
 * JSON 工具类。
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
/**
 * JsonUtils 的核心定义。
 */
public final class JsonUtils {

    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    /**
     * toJson 方法。
     * @param obj 参数。
     * @return 执行结果。
     */
    public static String toJson(Object obj) {
        try {
            return OBJECT_MAPPER.writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            throw new IllegalArgumentException("对象序列化为 JSON 失败", e);
        }
    }

    /**
     * toObject 方法。
     * @param json 参数。
     * @param clazz 参数。
     * @return 执行结果。
     */
    public static <T> T toObject(String json, Class<T> clazz) {
        try {
            return OBJECT_MAPPER.readValue(json, clazz);
        } catch (JsonProcessingException e) {
            throw new IllegalArgumentException("JSON 反序列化失败", e);
        }
    }

    /**
     * toObject 方法。
     * @param json 参数。
     * @param typeReference 参数。
     * @return 执行结果。
     */
    public static <T> T toObject(String json, TypeReference<T> typeReference) {
        try {
            return OBJECT_MAPPER.readValue(json, typeReference);
        } catch (JsonProcessingException e) {
            throw new IllegalArgumentException("JSON 反序列化失败", e);
        }
    }
}
