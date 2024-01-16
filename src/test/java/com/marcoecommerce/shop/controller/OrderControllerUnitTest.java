package com.marcoecommerce.shop.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.marcoecommerce.shop.mapper.impl.OrderMapper;
import com.marcoecommerce.shop.model.order.OrderDto;
import com.marcoecommerce.shop.model.order.OrderEntity;
import com.marcoecommerce.shop.model.order.OrderDto;
import com.marcoecommerce.shop.service.OrderService;
import com.marcoecommerce.shop.utils.TestDataUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.List;
import java.util.Optional;

@WebMvcTest(OrderController.class)
public class OrderControllerUnitTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private OrderService orderService;
    @MockBean
    private OrderMapper orderMapper;

    @Autowired
    private ObjectMapper objectMapper;

    private OrderEntity orderAEntity;
    private OrderEntity orderBEntity;
    private OrderDto orderADto;
    private OrderDto orderBDto;

    @BeforeEach
    public void setUp() {
        orderAEntity = TestDataUtil.createOrderEntityA();
        orderBEntity = TestDataUtil.createOrderEntityB();
        orderADto = TestDataUtil.createOrderDtoA();
        orderBDto = TestDataUtil.createOrderDtoB();

        Mockito.when(orderMapper.mapFrom(orderADto)).thenReturn(orderAEntity);
        Mockito.when(orderMapper.mapFrom(orderBDto)).thenReturn(orderBEntity);
        Mockito.when(orderMapper.mapTo(orderAEntity)).thenReturn(orderADto);
        Mockito.when(orderMapper.mapTo(orderBEntity)).thenReturn(orderBDto);
    }

    @Test
    public void givenOrder_whenCallCreateOrder_thenReturnJsonObject() throws Exception {
        // given
        Mockito.when(orderService.create(orderAEntity)).thenReturn(orderAEntity);

        // then
        String orderJson = objectMapper.writeValueAsString(orderADto);
        mockMvc.perform(
                MockMvcRequestBuilders.post("/orders")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(orderJson)
        ).andExpect(
                MockMvcResultMatchers.status().isCreated()
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.total").value(orderAEntity.getTotal())
        );
    }

    @Test
    public void givenOrders_whenCallGetOrders_thenReturnJsonArray() throws Exception {
        // given
        Mockito.when(orderService.findAll()).thenReturn(List.of(orderAEntity, orderBEntity));

        // then
        mockMvc.perform(
                MockMvcRequestBuilders.get("/orders")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(
                MockMvcResultMatchers.status().isOk()
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.length()").value(2)
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$[0].total").value(orderAEntity.getTotal())
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$[1].total").value(orderBEntity.getTotal())
        );
    }

    @Test
    public void givenOrder_whenFindById_thenReturn200() throws Exception {
        // given
        Long orderId = 1L;

        Mockito.when(orderService.findById(orderId)).thenReturn(Optional.of(orderAEntity));

        // then
        String orderDtoJson = objectMapper.writeValueAsString(orderADto);
        mockMvc.perform(
                MockMvcRequestBuilders.get("/orders/" + orderId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(orderDtoJson)
        ).andExpect(
                MockMvcResultMatchers.status().isOk()
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.total").value(orderAEntity.getTotal())
        );
    }

    @Test
    public void givenNonexistentOrder_whenUpdate_thenReturn404() throws Exception {
        // given
        Long nonexistentOrderId = 1L;
        Mockito.when(orderService.isExist(nonexistentOrderId)).thenReturn(false);
        String orderDtoJson = objectMapper.writeValueAsString(orderADto);

        // then
        mockMvc.perform(
                MockMvcRequestBuilders.patch("/orders/" + nonexistentOrderId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(orderDtoJson)
        ).andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    public void givenOrder_whenUpdate_thenReturn200() throws Exception {
        Long orderId = 1L;
        Mockito.when(orderService.update(orderId, orderAEntity)).thenReturn(orderBEntity);
        Mockito.when(orderService.isExist(orderId)).thenReturn(true);

        String orderDtoJson = objectMapper.writeValueAsString(orderADto);

        mockMvc.perform(
                MockMvcRequestBuilders.patch("/orders/" + orderId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(orderDtoJson)
        ).andExpect(
                MockMvcResultMatchers.status().isOk()
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.total").value(orderBEntity.getTotal())
        );
    }

    @Test
    public void givenNoOrder_whenCallDelete_thenReturn404() throws Exception {
        Long nonexistentOrderId = 1L;
        Mockito.when(orderService.isExist(nonexistentOrderId)).thenReturn(false);

        mockMvc.perform(
                MockMvcRequestBuilders.delete("/orders/" + nonexistentOrderId)
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(MockMvcResultMatchers.status().isNotFound());
    }


    @Test
    public void givenOrder_whenCallDelete_thenReturn204() throws Exception {
        Long existentOrderId = 1L;
        Mockito.when(orderService.isExist(existentOrderId)).thenReturn(true);

        mockMvc.perform(
                MockMvcRequestBuilders.delete("/orders/" + existentOrderId)
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(MockMvcResultMatchers.status().isNoContent());
    }
}
