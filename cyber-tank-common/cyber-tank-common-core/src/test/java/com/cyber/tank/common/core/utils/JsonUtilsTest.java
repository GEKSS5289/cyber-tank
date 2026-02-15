package com.cyber.tank.common.core.utils;

import com.fasterxml.jackson.core.type.TypeReference;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * JsonUtilsTest 的核心定义。
 */
class JsonUtilsTest {

    @Test
    void shouldSerializeAndDeserializeObject() {
        Map<String, Object> payload = Map.of("name", "tank", "level", 7);

        String json = JsonUtils.toJson(payload);
        Map<String, Object> result = JsonUtils.toObject(json, new TypeReference<>() {
        });

        assertEquals("tank", result.get("name"));
        assertEquals(7, ((Number) result.get("level")).intValue());
    }

    @Test
    void shouldDeserializeListByTypeReference() {
        String json = "[\"a\",\"b\",\"c\"]";

        List<String> list = JsonUtils.toObject(json, new TypeReference<>() {
        });

        assertEquals(List.of("a", "b", "c"), list);
    }

    @Test
    void shouldThrowWhenInvalidJson() {
        assertThrows(IllegalArgumentException.class, () -> JsonUtils.toObject("{invalid}", Map.class));
    }
}
