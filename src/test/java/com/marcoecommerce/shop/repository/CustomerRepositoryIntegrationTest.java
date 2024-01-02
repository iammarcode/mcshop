package com.marcoecommerce.shop.repository;


import com.marcoecommerce.shop.model.customer.CustomerEntity;
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
public class CustomerRepositoryIntegrationTest {

    @Autowired
    private CustomerRepository underTest;

    @Test
    public void testThatCustomerCanBeCreatedAndRecalled() {
        // Given
        CustomerEntity customerEntity = TestDataUtil.createCustomerEntityA();
        underTest.save(customerEntity);

        // When
        Optional<CustomerEntity> result = underTest.findById(customerEntity.getId());

        // Then
        assertThat(result).isPresent();
        assertThat(result.get()).isEqualTo(customerEntity);
    }

    @Test
    public void testThatMultipleCustomersCanBeCreatedAndRecalled() {
        // Given
        CustomerEntity customerEntityA = TestDataUtil.createCustomerEntityA();
        underTest.save(customerEntityA);
        CustomerEntity customerEntityB = TestDataUtil.createCustomerEntityB();
        underTest.save(customerEntityB);
        CustomerEntity customerEntityC = TestDataUtil.createCustomerEntityC();
        underTest.save(customerEntityC);

        // When
        Iterable<CustomerEntity> result = underTest.findAll();

        // Then
        assertThat(result)
                .hasSize(3).
                containsExactly(customerEntityA, customerEntityB, customerEntityC);
    }

    @Test
    public void testThatCustomerCanBeUpdated() {
        // Given
        CustomerEntity customerEntityA = TestDataUtil.createCustomerEntityA();
        underTest.save(customerEntityA);
        customerEntityA.setName("updated");
        underTest.save(customerEntityA);

        // When
        Optional<CustomerEntity> result = underTest.findById(customerEntityA.getId());

        // Then
        assertThat(result).isPresent();
        assertThat(result.get().getName()).isEqualTo("updated");
    }

    @Test
    public void testThatCustomerCanBeDeleted() {
        // Given
        CustomerEntity customerEntityA = TestDataUtil.createCustomerEntityA();
        underTest.save(customerEntityA);

        // When
        underTest.deleteById(customerEntityA.getId());
        Optional<CustomerEntity> result = underTest.findById(customerEntityA.getId());

        // Then
        assertThat(result).isEmpty();
    }
}
