package com.marcoecommerce.shop.repository;


import com.marcoecommerce.shop.model.order.OrderEntity;
import com.marcoecommerce.shop.model.customer.CustomerEntity;
import com.marcoecommerce.shop.utils.TestDataUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;




//    Optional<T> findById(ID id);
//
//        boolean existsById(ID id);
//
//        Iterable<T> findAll();
//
//        Iterable<T> findAllById(Iterable<ID> ids);
//
//        long count();
//
//        void deleteById(ID id);
//
//        void delete(T entity);
//
//        void deleteAllById(Iterable<? extends ID> ids);
//
//        void deleteAll(Iterable<? extends T> entities);
//
//        void deleteAll();

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class OrderRepositoryIntegrationTest {

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private OrderRepository underTest;

    @Autowired
    private TestEntityManager entityManager;
    
    private CustomerEntity customerA;
    private OrderEntity orderA;
    private OrderEntity orderB;
    
    @BeforeEach
    public void setUp() {
        customerA = TestDataUtil.createCustomerEntityA();
        orderA = TestDataUtil.createOrderEntityA();
        orderB = TestDataUtil.createOrderEntityB();
    }

    @Test
    public void givenOrder_whenFindOrder_thenReturnSavedOrder() {
        // given
        OrderEntity orderSaved = underTest.save(orderA);

        // when
        OrderEntity result = entityManager.find(OrderEntity.class, orderSaved.getId());

        // then
        assertThat(result).isEqualTo(orderSaved);
    }

    @Test
    public void givenOrder_whenUpdateOrder_thenReturnUpdatedOrder() {
        // given
        OrderEntity orderSaved = entityManager.persistAndFlush(orderA);

        // when
        orderSaved.setTotal(BigDecimal.valueOf(1000));
        OrderEntity result = underTest.save(orderSaved);

        // then
        assertThat(result.getTotal()).isEqualTo(BigDecimal.valueOf(1000));
    }

    @Test
    public void givenOrder_whenFindById_thenReturnOrder() {
        // given
        OrderEntity orderSaved = entityManager.persistAndFlush(orderA);

        // when
        Optional<OrderEntity> result = underTest.findById(orderSaved.getId());

        // then
        assertThat(result).isPresent();
        assertThat(result.get()).isEqualTo(orderSaved);
    }

    @Test
    public void givenOrder_whenExistById_thenReturnTrue() {
        // given
        OrderEntity orderSaved = entityManager.persistAndFlush(orderA);

        // when
        boolean result = underTest.existsById(orderSaved.getId());

        // then
        assertThat(result).isTrue();
    }


    @Test
    public void givenMultipleOrders_whenFindAll_thenReturnAll() {
        // given
        entityManager.persistAndFlush(orderA);
        entityManager.persistAndFlush(orderB);

        // when
        Iterable<OrderEntity> result = underTest.findAll();

        // then
        assertThat(result)
                .hasSize(2).
                containsExactly(orderA, orderB);
    }

    @Test
    public void givenMultipleOrders_whenFindAllByIds_thenReturnAll() {
        // given
        entityManager.persistAndFlush(orderA);
        entityManager.persistAndFlush(orderB);

        // when
        Iterable<OrderEntity> result = underTest.findAllById(List.of(orderA.getId(), orderB.getId()));

        // then
        assertThat(result)
                .hasSize(2).
                containsExactly(orderA, orderB);
    }

    @Test
    public void givenMultipleOrders_whenCount_thenReturnCountNumber() {
        // given
        entityManager.persistAndFlush(orderA);
        entityManager.persistAndFlush(orderB);

        // when
        long count = underTest.count();

        // then
        assertThat(count).isEqualTo(2);
    }

    @Test
    public void givenMultipleOrders_whenDeleteAllByIds_thenReturnAll() {
        // given
        entityManager.persistAndFlush(orderA);
        entityManager.persistAndFlush(orderB);

        // when
        underTest.deleteAllById(List.of(orderA.getId(), orderB.getId()));
        long result = underTest.count();

        // then
        assertThat(result).isEqualTo(0);
    }


    // delete from parent side
    @Test
    public void givenOrder_whenDeleteOrderFromCustomer_thenOrderDeleted() {
        // given
        customerA.addOrder(orderA);
        customerA.addOrder(orderB);
        CustomerEntity customerSaved = entityManager.persistAndFlush(customerA);

        assertThat(underTest.count()).isEqualTo(2);

        // when
        OrderEntity orderNeedDeleted = customerSaved.getOrderList().get(0);
        customerSaved.removeOrder(orderNeedDeleted);
        customerRepository.save(customerSaved);
        underTest.deleteById(orderNeedDeleted.getId());

        // then
        assertThat(underTest.count()).isEqualTo(1);
    }
}
