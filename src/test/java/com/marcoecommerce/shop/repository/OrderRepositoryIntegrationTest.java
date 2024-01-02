package com.marcoecommerce.shop.repository;


import com.marcoecommerce.shop.model.order.OrderEntity;
import com.marcoecommerce.shop.model.order.OrderStatus;
import com.marcoecommerce.shop.utils.TestDataUtil;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Optional;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class OrderRepositoryIntegrationTest {
    @Autowired
    private OrderRepository underTest;

    @Test
    public void testThatOrderCanBeCreatedAndRecalled() {
        // Given
        OrderEntity orderEntity = TestDataUtil.createOrderEntityA();
        underTest.save(orderEntity);

        // When
        Optional<OrderEntity> result = underTest.findById(orderEntity.getId());

        // Then
        assertThat(result).isPresent();
        assertThat(result.get()).isEqualTo(orderEntity);
    }

    @Test
    public void testThatMultipleOrdersCanBeCreatedAndRecalled() {
        // Given
        OrderEntity orderEntityA = TestDataUtil.createOrderEntityA();
        underTest.save(orderEntityA);
        OrderEntity orderEntityB = TestDataUtil.createOrderEntityB();
        underTest.save(orderEntityB);
        OrderEntity orderEntityC = TestDataUtil.createOrderEntityC();
        underTest.save(orderEntityC);

        // When
        Iterable<OrderEntity> result = underTest.findAll();

        // Then
        assertThat(result)
                .hasSize(3).
                containsExactly(orderEntityA, orderEntityB, orderEntityC);
    }

    @Test
    public void testThatOrderCanBeUpdated() {
        // Given
        OrderEntity orderEntityA = TestDataUtil.createOrderEntityA();
        underTest.save(orderEntityA);
        orderEntityA.setStatus(OrderStatus.CANCELED);
        underTest.save(orderEntityA);

        // When
        Optional<OrderEntity> result = underTest.findById(orderEntityA.getId());

        // Then
        assertThat(result).isPresent();
        assertThat(result.get().getStatus()).isEqualTo(OrderStatus.CANCELED);
    }

    @Test
    public void testThatOrderCanBeDeleted() {
        // Given
        OrderEntity orderEntityA = TestDataUtil.createOrderEntityA();
        underTest.save(orderEntityA);

        // When
        underTest.deleteById(orderEntityA.getId());
        Optional<OrderEntity> result = underTest.findById(orderEntityA.getId());

        // Then
        assertThat(result).isEmpty();
    }
}
