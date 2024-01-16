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
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;
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

    @Autowired
    private TestEntityManager entityManager;

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
    public void givenUser_whenFindUser_thenReturnSavedUser() {
        // given
        UserEntity userSaved = underTest.save(userA);

        // when
        UserEntity result = entityManager.find(UserEntity.class, userSaved.getId());

        // then
        assertThat(result).isEqualTo(userSaved);
    }

    @Test
    public void givenUser_whenUpdateUser_thenReturnUpdatedUser() {
        // given
        UserEntity userSaved = entityManager.persistAndFlush(userA);

        // when
        userSaved.setUsername("Updated Username");
        UserEntity result = underTest.save(userSaved);

        // then
        assertThat(result.getUsername()).isEqualTo("Updated Username");
    }

    @Test
    public void givenUser_whenFindById_thenReturnUser() {
        // given
        UserEntity UserEntity = entityManager.persistAndFlush(userA);

        // when
        Optional<UserEntity> result = underTest.findById(UserEntity.getId());

        // then
        assertThat(result).isPresent();
        assertThat(result.get()).isEqualTo(UserEntity);
    }

    @Test
    public void givenUser_whenExistById_thenReturnTrue() {
        // given
        UserEntity userSaved = entityManager.persistAndFlush(userA);

        // when
        boolean result = underTest.existsById(userSaved.getId());

        // then
        assertThat(result).isTrue();
    }


    @Test
    public void givenMultipleUsers_whenFindAll_thenReturnAll() {
        // given
        entityManager.persistAndFlush(userA);
        entityManager.persistAndFlush(userB);

        // when
        Iterable<UserEntity> result = underTest.findAll();

        // then
        assertThat(result)
                .hasSize(2).
                containsExactly(userA, userB);
    }

    @Test
    public void givenMultipleUsers_whenFindAllByIds_thenReturnAll() {
        // given
        entityManager.persistAndFlush(userA);
        entityManager.persistAndFlush(userB);

        // when
        Iterable<UserEntity> result = underTest.findAllById(List.of(userA.getId(), userB.getId()));

        // then
        assertThat(result)
                .hasSize(2).
                containsExactly(userA, userB);
    }

    @Test
    public void givenMultipleUsers_whenCount_thenReturnCountNumber() {
        // given
        entityManager.persistAndFlush(userA);
        entityManager.persistAndFlush(userB);

        // when
        long count = underTest.count();

        // then
        assertThat(count).isEqualTo(2);
    }

    @Test
    public void givenMultipleUsers_whenDeleteAllByIds_thenReturnAll() {
        // given
        entityManager.persistAndFlush(userA);
        entityManager.persistAndFlush(userB);

        // when
        underTest.deleteAllById(List.of(userA.getId(), userB.getId()));
        long result = underTest.count();

        // then
        assertThat(result).isEqualTo(0);
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
