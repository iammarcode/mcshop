package com.marcoecommerce.shop.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.marcoecommerce.shop.mapper.impl.CustomerMapper;
import com.marcoecommerce.shop.model.customer.CustomerDto;
import com.marcoecommerce.shop.model.customer.CustomerEntity;
import com.marcoecommerce.shop.service.CustomerService;
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
public class CustomerControllerIntegrationTest {
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private CustomerService customerService;

    @Test
    public void testCreateCustomerSuccessfully() throws Exception {
        CustomerEntity testCustomerA = TestDataUtil.createCustomerEntityA();
        String customerJson = objectMapper.writeValueAsString(testCustomerA);

        mockMvc.perform(
                MockMvcRequestBuilders.post("/customers")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(customerJson)
        ).andExpect(
                MockMvcResultMatchers.status().isCreated()
        );
    }


    @Test
    public void testCreateCustomerListAndFindCreatedCustomerList() throws Exception {
        CustomerEntity testCustomerA = TestDataUtil.createCustomerEntityA();
        CustomerEntity savedCustomerA = customerService.save(testCustomerA);

        mockMvc.perform(
                MockMvcRequestBuilders.get("/customers")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(
                MockMvcResultMatchers.status().isOk()
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.length()").value(1)
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$[0].id").value(savedCustomerA.getId())
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$[0].name").value(savedCustomerA.getName())
        );
    }

    @Test
    public void testCreateAndFindCreatedCustomer() throws Exception {
        CustomerEntity testCustomerA = TestDataUtil.createCustomerEntityA();
        CustomerEntity savedCustomer = customerService.save(testCustomerA);

        mockMvc.perform(
                MockMvcRequestBuilders.get("/customers/" + savedCustomer.getId())
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(
                MockMvcResultMatchers.status().isOk()
        );
    }



    @Test
    public void testFullUpdateNonexistentCustomer() throws Exception {
        CustomerDto testCustomerDtoA = TestDataUtil.createCustomerDtoA();
        String customerDtoJson = objectMapper.writeValueAsString(testCustomerDtoA);

        mockMvc.perform(
                MockMvcRequestBuilders.put("/customers/9999")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(customerDtoJson)
        ).andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    public void testFullUpdateExistingCustomer() throws Exception {
        CustomerEntity testCustomerEntityA = TestDataUtil.createCustomerEntityA();
        CustomerEntity savedCustomer = customerService.save(testCustomerEntityA);

        CustomerDto testCustomerDtoB = TestDataUtil.createCustomerDtoB();
        testCustomerDtoB.setId(savedCustomer.getId());

        String customerDtoUpdateJson = objectMapper.writeValueAsString(testCustomerDtoB);

        mockMvc.perform(
                MockMvcRequestBuilders.put("/customers/" + savedCustomer.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(customerDtoUpdateJson)
        ).andExpect(
                MockMvcResultMatchers.status().isOk()
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.id").value(savedCustomer.getId())
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.name").value(testCustomerDtoB.getName())
        );
    }

    @Test
    public void testPartialUpdateExistingCustomer() throws Exception {
        CustomerEntity testCustomerEntityA = TestDataUtil.createCustomerEntityA();
        CustomerEntity savedCustomer = customerService.save(testCustomerEntityA);

        CustomerDto testCustomerDtoA = TestDataUtil.createCustomerDtoA();
        testCustomerDtoA.setName("UPDATED");
        String customerDtoJson = objectMapper.writeValueAsString(testCustomerDtoA);

        mockMvc.perform(
                MockMvcRequestBuilders.patch("/customers/" + savedCustomer.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(customerDtoJson)
        ).andExpect(
                MockMvcResultMatchers.status().isOk()
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.id").value(savedCustomer.getId())
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.name").value("UPDATED")
        );
    }

    @Test
    public void testDeleteNonexistentCustomer() throws Exception {
        mockMvc.perform(
                MockMvcRequestBuilders.delete("/customers/999")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(MockMvcResultMatchers.status().isNoContent());
    }

    @Test
    public void testDeleteExistingCustomer() throws Exception {
        CustomerEntity testCustomerEntityA = TestDataUtil.createCustomerEntityA();
        CustomerEntity savedCustomer = customerService.save(testCustomerEntityA);

        mockMvc.perform(
                MockMvcRequestBuilders.delete("/customers/" + savedCustomer.getId())
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(MockMvcResultMatchers.status().isNoContent());
    }






}
