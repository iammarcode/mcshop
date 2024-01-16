package com.marcoecommerce.shop.repository;


import com.marcoecommerce.shop.model.order.OrderEntity;
import com.marcoecommerce.shop.model.shoppingCart.ShoppingCartEntity;
import com.marcoecommerce.shop.model.user.UserEntity;
import com.marcoecommerce.shop.model.userAddress.UserAddressEntity;
import com.marcoecommerce.shop.model.userPayment.UserPaymentEntity;
import com.marcoecommerce.shop.utils.TestDataUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.Optional;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class UserRepositoryIntegrationTest {

    @Autowired
    private UserRepository underTest;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private UserPaymentRepository userPaymentRepository;

    @Autowired
    private UserAddressRepository userAddressRepository;
    @Autowired
    private ShoppingCartRepository shoppingCartRepository;

    private UserEntity userA;
    private UserEntity userB;
    private UserAddressEntity addressA;
    private UserAddressEntity addressB;
    private UserPaymentEntity paymentA;
    private UserPaymentEntity paymentB;
    private OrderEntity orderA;
    private OrderEntity orderB;
    private ShoppingCartEntity shoppingCartA;

    @BeforeEach
    public void setUp() {
        this.userA = TestDataUtil.createUserEntityA();
        this.userB = TestDataUtil.createUserEntityB();
        addressA = TestDataUtil.createUserAddressEntityA();
        addressB = TestDataUtil.createUserAddressEntityB();
        paymentA = TestDataUtil.createUserPaymentEntityA();
        paymentB = TestDataUtil.createUserPaymentEntityB();
        orderA = TestDataUtil.createOrderEntityA();
        orderB = TestDataUtil.createOrderEntityB();
        shoppingCartA = TestDataUtil.createShoppingCartEntityA();
    }

    @Test
    public void givenUser_whenFindById_thenReturnUser() {
        // given
        UserEntity userSaved = underTest.save(userA);

        // when
        Optional<UserEntity> result = underTest.findById(userSaved.getId());

        // then
        assertThat(result).isPresent();
        assertThat(result.get()).isEqualTo(userSaved);
    }

    @Test
    public void givenMultipleUsers_whenFindAll_thenReturnAll() {
        // given
        underTest.saveAll(List.of(userA, userB));

        // when
        Iterable<UserEntity> result = underTest.findAll();

        // then
        assertThat(result)
                .hasSize(2).
                containsExactly(userA, userB);
    }

    @Test
    public void givenUser_whenUpdateUser_thenReturnUpdatedUser() {
        // given
        UserEntity userSaved = underTest.save(userA);

        // when
        userSaved.setEmail("updated@gmail.com");
        UserEntity result = underTest.save(userSaved);

        // then
        assertThat(result.getEmail()).isEqualTo("updated@gmail.com");
    }

    @Test
    public void givenUser_whenDeleteUserById_thenUserAndAssociationDeleted() {
        // given
        
        userA.addAddress(addressA);
        userA.addAddress(addressB);
        userA.addPayment(paymentA);
        userA.addPayment(paymentB);
        userA.addOrder(orderA);
        userA.addOrder(orderB);
        userA.addShoppingCart(shoppingCartA);
        UserEntity userSaved = underTest.save(userA);

        assertThat(underTest.count()).isEqualTo(1);
        assertThat(orderRepository.count()).isEqualTo(2);
        assertThat(userPaymentRepository.count()).isEqualTo(2);
        assertThat(userAddressRepository.count()).isEqualTo(2);
        assertThat(shoppingCartRepository.count()).isEqualTo(1);

        // when
        underTest.deleteById(userSaved.getId());

        // then
        assertThat(underTest.count()).isEqualTo(0);
        assertThat(orderRepository.count()).isEqualTo(0);
        assertThat(userPaymentRepository.count()).isEqualTo(0);
        assertThat(userAddressRepository.count()).isEqualTo(0);
        assertThat(shoppingCartRepository.count()).isEqualTo(0);
    }
}
