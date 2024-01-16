package com.marcoecommerce.shop.service;

import com.marcoecommerce.shop.model.order.OrderEntity;
import com.marcoecommerce.shop.model.order.OrderStatus;
import com.marcoecommerce.shop.repository.OrderRepository;
import com.marcoecommerce.shop.service.impl.OrderServiceImpl;
import com.marcoecommerce.shop.utils.TestDataUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class OrderServiceImplUnitTest {

    @Mock
    private OrderRepository orderRepository;

    @InjectMocks
    private OrderServiceImpl orderService;

    private OrderEntity orderA;
    private OrderEntity orderB;

    @BeforeEach
    void setUp() {
        orderA = TestDataUtil.createOrderEntityA();
        orderA.setId(1L);
        orderB = TestDataUtil.createOrderEntityB();
        orderA.setId(2L);
    }

    @Test
    public void givenOrder_whenSaveOrder_thenReturnOrder() {
        // given
        when(orderRepository.save(orderA)).thenReturn(orderA);

        // when
        OrderEntity orderSaved = orderService.create(orderA);

        // then
        verify(orderRepository, times(1)).save(any(OrderEntity.class));
        assertEquals(orderA, orderSaved);
    }

    @Test
    public void givenOrders_whenFindAllOrders_thenReturnAllOrders() {
        // given
        when(orderRepository.findById(orderA.getId())).thenReturn(Optional.of(orderA));

        // when
        Optional<OrderEntity> orderFound = orderService.findById(orderA.getId());

        // then
        verify(orderRepository, times(1)).findById(any(Long.class));
        assertTrue(orderFound.isPresent());
        assertEquals(orderA, orderFound.get());
    }

    @Test
    public void givenOrder_whenFindById_thenReturnOrder() {
        // given
        when(orderRepository.findAll()).thenReturn(List.of(orderA, orderB));

        // when
        List<OrderEntity> result = orderService.findAll();

        // then
        verify(orderRepository, times(1)).findAll();
        assertTrue(result.containsAll(List.of(orderA, orderB)));
    }

    @Test
    public void givenOrder_whenIsExist_thenReturnTrue() {
        // given
        when(orderRepository.existsById(orderA.getId())).thenReturn(true);

        // when
        boolean result = orderService.isExist(orderA.getId());

        // then
        verify(orderRepository, times(1)).existsById(any(Long.class));
        assertTrue(result);
    }

    @Test
    public void whenDeleteById_thenSuccessful() {
        // given
        // when
        orderService.deleteById(orderA.getId());

        // then
        verify(orderRepository, times(1)).deleteById(any(Long.class));
    }

    @Test
    public void givenOrder_whenUpdateOrder_thenReturnUpdatedOrder() {
        // given
        when(orderRepository.findById(orderA.getId())).thenReturn(Optional.of(orderA));
        when(orderRepository.save(orderA)).thenReturn(orderA);

        // when
        orderA.setStatus(OrderStatus.DELIVERED);
        OrderEntity orderUpdated = orderService.update(orderA.getId(), orderA);

        // then
        verify(orderRepository, times(1)).save(any(OrderEntity.class));
        verify(orderRepository, times(1)).findById(any(Long.class));
        assertEquals(OrderStatus.DELIVERED, orderUpdated.getStatus());
    }

}
