package com.marcoecommerce.shop.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.marcoecommerce.shop.model.order.OrderDto;
import com.marcoecommerce.shop.model.order.OrderEntity;
import com.marcoecommerce.shop.model.order.OrderStatus;
import com.marcoecommerce.shop.service.OrderService;
import com.marcoecommerce.shop.utils.TestDataUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@SpringBootTest
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class OrderControllerIntegrationTest {
    private final ObjectMapper objectMapper;
    private final MockMvc mockMvc;
    private final OrderService orderService;

    @Autowired
    public OrderControllerIntegrationTest(MockMvc mockMvc, OrderService orderService) {
        this.orderService = orderService;
        this.objectMapper = new ObjectMapper();
        this.mockMvc = mockMvc;
    }

    @Test
    public void testCreateOrderSuccessfully() throws Exception {
        OrderEntity testOrderA = TestDataUtil.createOrderEntityA();
        String orderJson = objectMapper.writeValueAsString(testOrderA);

        mockMvc.perform(
                MockMvcRequestBuilders.post("/orders")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(orderJson)
        ).andExpect(
                MockMvcResultMatchers.status().isCreated()
        );
    }


    @Test
    public void testCreateOrderListAndFindCreatedOrderList() throws Exception {
        OrderEntity testOrderA = TestDataUtil.createOrderEntityA();
        OrderEntity savedOrderA = orderService.save(testOrderA);

        mockMvc.perform(
                MockMvcRequestBuilders.get("/orders")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(
                MockMvcResultMatchers.status().isOk()
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.length()").value(1)
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$[0].id").value(savedOrderA.getId())
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$[0].status").value(savedOrderA.getStatus().toString())
        );
    }

    @Test
    public void testCreateAndFindCreatedOrder() throws Exception {
        OrderEntity testOrderA = TestDataUtil.createOrderEntityA();
        OrderEntity savedOrder = orderService.save(testOrderA);

        mockMvc.perform(
                MockMvcRequestBuilders.get("/orders/" + savedOrder.getId())
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(
                MockMvcResultMatchers.status().isOk()
        );
    }



    @Test
    public void testFullUpdateNonexistentOrder() throws Exception {
        OrderDto testOrderDtoA = TestDataUtil.createOrderDtoA();
        String orderDtoJson = objectMapper.writeValueAsString(testOrderDtoA);

        mockMvc.perform(
                MockMvcRequestBuilders.put("/orders/9999")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(orderDtoJson)
        ).andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    public void testFullUpdateExistingOrder() throws Exception {
        OrderEntity testOrderEntityA = TestDataUtil.createOrderEntityA();
        OrderEntity savedOrder = orderService.save(testOrderEntityA);

        OrderDto testOrderDtoB = TestDataUtil.createOrderDtoB();
        testOrderDtoB.setId(savedOrder.getId());

        String orderDtoUpdateJson = objectMapper.writeValueAsString(testOrderDtoB);

        mockMvc.perform(
                MockMvcRequestBuilders.put("/orders/" + savedOrder.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(orderDtoUpdateJson)
        ).andExpect(
                MockMvcResultMatchers.status().isOk()
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.id").value(savedOrder.getId())
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.status").value(testOrderDtoB.getStatus().toString())
        );
    }

    @Test
    public void testPartialUpdateExistingOrder() throws Exception {
        OrderEntity testOrderEntityA = TestDataUtil.createOrderEntityA();
        OrderEntity savedOrder = orderService.save(testOrderEntityA);

        OrderDto testOrderDtoA = TestDataUtil.createOrderDtoA();
        testOrderDtoA.setStatus(OrderStatus.CANCELED);
        String orderDtoJson = objectMapper.writeValueAsString(testOrderDtoA);

        mockMvc.perform(
                MockMvcRequestBuilders.patch("/orders/" + savedOrder.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(orderDtoJson)
        ).andExpect(
                MockMvcResultMatchers.status().isOk()
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.id").value(savedOrder.getId())
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.status").value(OrderStatus.CANCELED.toString())
        );
    }

    @Test
    public void testDeleteNonexistentOrder() throws Exception {
        mockMvc.perform(
                MockMvcRequestBuilders.delete("/orders/999")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(MockMvcResultMatchers.status().isNoContent());
    }

    @Test
    public void testDeleteExistingOrder() throws Exception {
        OrderEntity testOrderEntityA = TestDataUtil.createOrderEntityA();
        OrderEntity savedOrder = orderService.save(testOrderEntityA);

        mockMvc.perform(
                MockMvcRequestBuilders.delete("/orders/" + savedOrder.getId())
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(MockMvcResultMatchers.status().isNoContent());
    }






}
