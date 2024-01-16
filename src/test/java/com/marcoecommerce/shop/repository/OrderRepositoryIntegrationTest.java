package com.marcoecommerce.shop.repository;


import com.marcoecommerce.shop.model.order.OrderEntity;
import com.marcoecommerce.shop.model.order.OrderStatus;
import com.marcoecommerce.shop.model.user.UserEntity;
import com.marcoecommerce.shop.utils.TestDataUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class OrderRepositoryIntegrationTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private OrderRepository underTest;
    
    private UserEntity userA;
    private OrderEntity orderA;
    private OrderEntity orderB;
    
    @BeforeEach
    public void setUp() {
        userA = TestDataUtil.createUserEntityA();
        orderA = TestDataUtil.createOrderEntityA();
        orderB = TestDataUtil.createOrderEntityB();
    }

    @Test
    public void givenOrder_whenFindById_thenReturnOrder() {
        // given
        OrderEntity orderSaved = underTest.save(orderA);

        // when
        Optional<OrderEntity> result = underTest.findById(orderSaved.getId());

        // then
        assertThat(result).isPresent();
        assertThat(result.get()).isEqualTo(orderSaved);
    }


    @Test
    public void givenMultipleOrders_whenFindAll_thenReturnAll() {
        // given
        underTest.saveAll(List.of(orderA, orderB));

        // when
        Iterable<OrderEntity> result = underTest.findAll();

        // then
        assertThat(result)
                .hasSize(2).
                containsExactly(orderA, orderB);
    }

    @Test
    public void givenOrder_whenUpdateOrder_thenReturnUpdatedOrder() {
        // given
        OrderEntity orderSaved = underTest.save(orderA);

        // when
        orderSaved.setStatus(OrderStatus.CANCELED);
        OrderEntity result = underTest.save(orderSaved);

        // then
        assertThat(result.getStatus()).isEqualTo(OrderStatus.CANCELED);
    }

    @Test
    public void givenOrder_whenDeleteOrderById_thenOrderDeletedUserNotDeleted() {
        // given
        userA.addOrder(orderA);
        userA.addOrder(orderB);
        UserEntity userSaved = userRepository.save(userA);

        assertThat(userSaved.getOrderList().size()).isEqualTo(2);
        assertThat(underTest.count()).isEqualTo(2);

        // when
        OrderEntity orderDeleted = userSaved.getOrderList().get(0);
        userSaved.removeOrder(orderDeleted);
        UserEntity userUpdated = userRepository.save(userSaved);

        // then
        assertThat(userUpdated.getOrderList().size()).isEqualTo(1);
        assertThat(underTest.count()).isEqualTo(1);
    }
}
