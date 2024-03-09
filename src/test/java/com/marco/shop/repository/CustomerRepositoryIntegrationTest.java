//package com.marco.shop.repository;
//
//
//import com.marco.shop.model.customerPayment.CustomerPaymentEntity;
//import com.marco.shop.model.shoppingCart.ShoppingCartEntity;
//import com.marco.shop.model.order.OrderEntity;
//import com.marco.shop.model.customer.CustomerEntity;
//import com.marco.shop.model.customerAddress.CustomerAddressEntity;
//import com.marco.shop.utils.TestDataUtil;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
//import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
//import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
//
//import static org.assertj.core.api.Assertions.assertThat;
//
//import java.util.List;
//import java.util.Optional;
//
//@DataJpaTest
//@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
//public class CustomerRepositoryIntegrationTest {
//
//    @Autowired
//    private CustomerRepository underTest;
//
//    @Autowired
//    private OrderRepository orderRepository;
//
//    @Autowired
//    private CustomerPaymentRepository customerPaymentRepository;
//
//    @Autowired
//    private CustomerAddressRepository customerAddressRepository;
//    @Autowired
//    private ShoppingCartRepository shoppingCartRepository;
//
//    @Autowired
//    private TestEntityManager entityManager;
//
//    private CustomerEntity customerA;
//    private CustomerEntity customerB;
//    private CustomerAddressEntity addressA;
//    private CustomerAddressEntity addressB;
//    private CustomerPaymentEntity paymentA;
//    private CustomerPaymentEntity paymentB;
//    private OrderEntity orderA;
//    private OrderEntity orderB;
//    private ShoppingCartEntity shoppingCartA;
//
//    @BeforeEach
//    public void setUp() {
//        this.customerA = TestDataUtil.createCustomerEntityA();
//        this.customerB = TestDataUtil.createCustomerEntityB();
//        addressA = TestDataUtil.createCustomerAddressEntityA();
//        addressB = TestDataUtil.createCustomerAddressEntityB();
//        paymentA = TestDataUtil.createCustomerPaymentEntityA();
//        paymentB = TestDataUtil.createCustomerPaymentEntityB();
//        orderA = TestDataUtil.createOrderEntityA();
//        orderB = TestDataUtil.createOrderEntityB();
//        shoppingCartA = TestDataUtil.createShoppingCartEntityA();
//    }
//
//    @Test
//    public void givenCustomer_whenFindCustomer_thenReturnSavedCustomer() {
//        // given
//        CustomerEntity customerSaved = underTest.save(customerA);
//
//        // when
//        CustomerEntity result = entityManager.find(CustomerEntity.class, customerSaved.getId());
//
//        // then
//        assertThat(result).isEqualTo(customerSaved);
//    }
//
//    @Test
//    public void givenCustomer_whenUpdateCustomer_thenReturnUpdatedCustomer() {
//        // given
//        CustomerEntity customerSaved = entityManager.persistAndFlush(customerA);
//
//        // when
//        customerSaved.setNickname("Updated Username");
//        CustomerEntity result = underTest.save(customerSaved);
//
//        // then
//        assertThat(result.getNickname()).isEqualTo("Updated Username");
//    }
//
//    @Test
//    public void givenCustomer_whenFindById_thenReturnCustomer() {
//        // given
//        CustomerEntity CustomerEntity = entityManager.persistAndFlush(customerA);
//
//        // when
//        Optional<CustomerEntity> result = underTest.findById(CustomerEntity.getId());
//
//        // then
//        assertThat(result).isPresent();
//        assertThat(result.get()).isEqualTo(CustomerEntity);
//    }
//
//    @Test
//    public void givenCustomer_whenExistById_thenReturnTrue() {
//        // given
//        CustomerEntity customerSaved = entityManager.persistAndFlush(customerA);
//
//        // when
//        boolean result = underTest.existsById(customerSaved.getId());
//
//        // then
//        assertThat(result).isTrue();
//    }
//
//
//    @Test
//    public void givenMultipleCustomer_whenFindAll_thenReturnAll() {
//        // given
//        entityManager.persistAndFlush(customerA);
//        entityManager.persistAndFlush(customerB);
//
//        // when
//        Iterable<CustomerEntity> result = underTest.findAll();
//
//        // then
//        assertThat(result)
//                .hasSize(2).
//                containsExactly(customerA, customerB);
//    }
//
//    @Test
//    public void givenMultipleCustomer_whenFindAllByIds_thenReturnAll() {
//        // given
//        entityManager.persistAndFlush(customerA);
//        entityManager.persistAndFlush(customerB);
//
//        // when
//        Iterable<CustomerEntity> result = underTest.findAllById(List.of(customerA.getId(), customerB.getId()));
//
//        // then
//        assertThat(result)
//                .hasSize(2).
//                containsExactly(customerA, customerB);
//    }
//
//    @Test
//    public void givenMultipleCustomer_whenCount_thenReturnCountNumber() {
//        // given
//        entityManager.persistAndFlush(customerA);
//        entityManager.persistAndFlush(customerB);
//
//        // when
//        long count = underTest.count();
//
//        // then
//        assertThat(count).isEqualTo(2);
//    }
//
//    @Test
//    public void givenMultipleCustomer_whenDeleteAllByIds_thenReturnAll() {
//        // given
//        entityManager.persistAndFlush(customerA);
//        entityManager.persistAndFlush(customerB);
//
//        // when
//        underTest.deleteAllById(List.of(customerA.getId(), customerB.getId()));
//        long result = underTest.count();
//
//        // then
//        assertThat(result).isEqualTo(0);
//    }
//
//    @Test
//    public void givenEmailPhoneUsername_whenExistByEmail_thenReturnTrue() {
//        // given
//        entityManager.persistAndFlush(customerA);
//
//        // when
//        boolean isEmailExist = underTest.existsByEmail(customerA.getEmail());
//
//        // then
//        assertThat(isEmailExist).isTrue();
//    }
//
//    @Test
//    public void givenCustomer_whenDeleteCustomerById_thenCustomerAndAssociationDeleted() {
//        // given
//        customerA.addAddress(addressA);
//        customerA.addAddress(addressB);
//        customerA.addPayment(paymentA);
//        customerA.addPayment(paymentB);
//        customerA.addOrder(orderA);
//        customerA.addOrder(orderB);
//        customerA.addShoppingCart(shoppingCartA);
//        CustomerEntity customerSaved = underTest.save(customerA);
//
//        assertThat(underTest.count()).isEqualTo(1);
//        assertThat(orderRepository.count()).isEqualTo(2);
//        assertThat(customerPaymentRepository.count()).isEqualTo(2);
//        assertThat(customerAddressRepository.count()).isEqualTo(2);
//        assertThat(shoppingCartRepository.count()).isEqualTo(1);
//
//        // when
//        underTest.deleteById(customerSaved.getId());
//
//        // then
//        assertThat(underTest.count()).isEqualTo(0);
//        assertThat(orderRepository.count()).isEqualTo(0);
//        assertThat(customerPaymentRepository.count()).isEqualTo(0);
//        assertThat(customerAddressRepository.count()).isEqualTo(0);
//        assertThat(shoppingCartRepository.count()).isEqualTo(0);
//    }
//}
