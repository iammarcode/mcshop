package com.marcoecommerce.shop.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.marcoecommerce.shop.mapper.impl.CustomerMapper;
import com.marcoecommerce.shop.model.customer.CustomerDto;
import com.marcoecommerce.shop.model.customer.CustomerEntity;
import com.marcoecommerce.shop.service.CustomerService;
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

@WebMvcTest(CustomerController.class)
public class CustomerControllerUnitTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private CustomerService customerService;
    @MockBean
    private CustomerMapper customerMapper;

    @Autowired
    private ObjectMapper objectMapper;

    private CustomerEntity customerAEntity;
    private CustomerEntity customerBEntity;
    private CustomerDto customerADto;
    private CustomerDto customerBDto;

    @BeforeEach
    public void setUp() {
        customerAEntity = TestDataUtil.createCustomerEntityA();
        customerBEntity = TestDataUtil.createCustomerEntityB();
        customerADto = TestDataUtil.createCustomerDtoA();
        customerBDto = TestDataUtil.createCustomerDtoB();

        Mockito.when(customerMapper.toEntity(customerADto)).thenReturn(customerAEntity);
        Mockito.when(customerMapper.toEntity(customerBDto)).thenReturn(customerBEntity);
        Mockito.when(customerMapper.toDto(customerAEntity)).thenReturn(customerADto);
        Mockito.when(customerMapper.toDto(customerBEntity)).thenReturn(customerBDto);
    }

    @Test
    public void givenCustomer_whenCallCreateCustomer_thenReturnJsonObject() throws Exception {
        // given
        Mockito.when(customerService.create(customerAEntity)).thenReturn(customerAEntity);

        // then
        String customerJson = objectMapper.writeValueAsString(customerADto);
        mockMvc.perform(
                MockMvcRequestBuilders.post("/customer")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(customerJson)
        ).andExpect(
                MockMvcResultMatchers.status().isCreated()
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.email").value(customerAEntity.getNickname())
        );
    }

    @Test
    public void givenCustomer_whenCallGetCustomer_thenReturnJsonArray() throws Exception {
        // given
        Mockito.when(customerService.findAll()).thenReturn(List.of(customerADto, customerBDto));

        // then
        mockMvc.perform(
                MockMvcRequestBuilders.get("/customer")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(
                MockMvcResultMatchers.status().isOk()
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.length()").value(2)
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$[0].email").value(customerAEntity.getNickname())
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$[1].email").value(customerBEntity.getNickname())
        );
    }

    @Test
    public void givenCustomer_whenFindById_thenReturn200() throws Exception {
        // given
        Long customerId = 1L;
        Mockito.when(customerService.findById(customerId)).thenReturn(customerADto);

        // then
        String customerDtoJson = objectMapper.writeValueAsString(customerADto);
        mockMvc.perform(
                MockMvcRequestBuilders.get("/customer/" + customerId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(customerDtoJson)
        ).andExpect(
                MockMvcResultMatchers.status().isOk()
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.email").value(customerAEntity.getNickname())
        );
    }

    @Test
    public void givenNonexistentCustomer_whenUpdate_thenReturn404() throws Exception {
        // given
        Long nonexistentCustomerId = 1L;
        Mockito.when(customerService.isExist(nonexistentCustomerId)).thenReturn(false);
        String customerDtoJson = objectMapper.writeValueAsString(customerADto);

        // then
        mockMvc.perform(
                MockMvcRequestBuilders.patch("/customer/" + nonexistentCustomerId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(customerDtoJson)
        ).andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    public void givenCustomer_whenUpdate_thenReturn200() throws Exception {
        // given
        Long customerId = 1L;
        Mockito.when(customerService.updateInfo(customerId, customerADto)).thenReturn(customerADto);
        Mockito.when(customerService.isExist(customerId)).thenReturn(true);

        String customerDtoJson = objectMapper.writeValueAsString(customerADto);

        // then
        mockMvc.perform(
                MockMvcRequestBuilders.patch("/customer/" + customerId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(customerDtoJson)
        ).andExpect(
                MockMvcResultMatchers.status().isOk()
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.email").value(customerAEntity.getNickname())
        );
    }

    @Test
    public void givenNoCustomer_whenCallDelete_thenReturn404() throws Exception {
        // given
        Long nonexistentCustomerId = 1L;
        Mockito.when(customerService.isExist(nonexistentCustomerId)).thenReturn(false);

        // then
        mockMvc.perform(
                MockMvcRequestBuilders.delete("/customer/" + nonexistentCustomerId)
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    public void givenCustomer_whenCallDelete_thenReturn204() throws Exception {
        Long existentCustomerId = 1L;
        Mockito.when(customerService.isExist(existentCustomerId)).thenReturn(true);

        mockMvc.perform(
                MockMvcRequestBuilders.delete("/customer/" + existentCustomerId)
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(MockMvcResultMatchers.status().isNoContent());
    }
}
