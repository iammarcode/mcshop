package com.marcoecommerce.shop.demo;


import com.marcoecommerce.shop.model.customer.CustomerEntity;
import com.marcoecommerce.shop.service.CustomerService;
import com.marcoecommerce.shop.utils.TestDataUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class DemoRestTest {

    private final TestRestTemplate restTemplate;
    private final CustomerService customerService;

    @Autowired
    public DemoRestTest(TestRestTemplate restTemplate, CustomerService customerService) {
        this.restTemplate = restTemplate;
        this.customerService = customerService;
    }

    @Test
    void exampleTest() {
        CustomerEntity customerEntityA = TestDataUtil.createCustomerEntityA();
        customerService.save(customerEntityA);

        CustomerEntity body = restTemplate.getForObject("/customers/" + customerEntityA.getId(), CustomerEntity.class);
        assertThat(body).isEqualTo(customerEntityA);
    }
}
