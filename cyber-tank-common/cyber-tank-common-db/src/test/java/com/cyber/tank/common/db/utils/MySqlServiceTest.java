package com.cyber.tank.common.db.utils;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.Serializable;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class MySqlServiceTest {

    private final BaseMapper<TestEntity> mapper = mock(BaseMapper.class);
    private MySqlService mySqlService;

    @BeforeEach
    void setUp() {
        mySqlService = new MySqlService();
    }

    @Test
    void shouldInsertBatch() {
        when(mapper.insert(any())).thenReturn(1);

        int affected = mySqlService.insertBatch(mapper, List.of(new TestEntity(1L, "a"), new TestEntity(2L, "b")));

        assertEquals(2, affected);
    }

    @Test
    void shouldReturnEmptyListWhenMapperReturnsNull() {
        when(mapper.selectList(any(LambdaQueryWrapper.class))).thenReturn(null);

        List<TestEntity> list = mySqlService.selectList(mapper, wrapper -> wrapper.eq(TestEntity::getName, "a"));

        assertTrue(list.isEmpty());
    }

    @Test
    void shouldCountWithWrapper() {
        when(mapper.selectCount(any(LambdaQueryWrapper.class))).thenReturn(5L);

        long count = mySqlService.count(mapper, wrapper -> wrapper.eq(TestEntity::getName, "a"));

        assertEquals(5L, count);
    }

    @Test
    void shouldSelectPage() {
        Page<TestEntity> page = new Page<>(1, 10);
        page.setRecords(List.of(new TestEntity(1L, "a")));
        when(mapper.selectPage(any(Page.class), any(LambdaQueryWrapper.class))).thenReturn(page);

        Page<TestEntity> result = mySqlService.selectPage(mapper, 1, 10, wrapper -> wrapper.eq(TestEntity::getName, "a"));

        assertEquals(1, result.getRecords().size());
        verify(mapper).selectPage(any(Page.class), any(LambdaQueryWrapper.class));
    }

    static class TestEntity implements Serializable {
        private final Long id;
        private final String name;

        TestEntity(Long id, String name) {
            this.id = id;
            this.name = name;
        }

        public Long getId() {
            return id;
        }

        public String getName() {
            return name;
        }
    }
}
