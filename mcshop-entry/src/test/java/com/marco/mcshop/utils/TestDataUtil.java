package com.marco.mcshop.utils;

import com.marco.mcshop.model.constant.CustomerPaymentType;
import com.marco.mcshop.model.constant.OrderStatus;
import com.marco.mcshop.model.constant.OrderTransactionStatus;
import com.marco.mcshop.model.dto.CustomerDto;
import com.marco.mcshop.model.dto.OrderDto;
import com.marco.mcshop.model.entity.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class TestDataUtil {

    private TestDataUtil() {
    }

    // Customer
    public static Customer createCustomerEntityA() {
        return Customer.builder()
                .email("aaa@example.com")
                .phone("11111111")
                .nickname("nicknameA")
                .password("passwordA")
                .firstName("firstnameA")
                .lastName("lastnameA")
                .addressList(new ArrayList<>())
                .paymentList(new ArrayList<>())
                .orderList(new ArrayList<>())
                .build();
    }

    public static Customer createCustomerEntityB() {
        return Customer.builder()
                .email("bbb@example.com")
                .phone("22222222")
                .nickname("nicknameB")
                .password("passwordB")
                .firstName("firstnameB")
                .lastName("lastnameB")
                .addressList(new ArrayList<>())
                .paymentList(new ArrayList<>())
                .orderList(new ArrayList<>())
                .build();
    }

    public static CustomerDto createCustomerDtoA() {
        return CustomerDto.builder()
                .email("aaa@example.com")
                .phone("11111111")
                .nickname("nicknameA")
                .firstName("firstnameA")
                .lastName("lastnameA")
                .addressList(new ArrayList<>())
                .paymentList(new ArrayList<>())
                .orderList(new ArrayList<>())
                .build();
    }

    public static CustomerDto createCustomerDtoB() {
        return CustomerDto.builder()
                .email("bbb@example.com")
                .phone("222222")
                .nickname("nicknameB")
                .firstName("firstnameB")
                .lastName("lastnameB")
                .addressList(new ArrayList<>())
                .paymentList(new ArrayList<>())
                .orderList(new ArrayList<>())
                .build();
    }

    // CustomerAddress
    public static CustomerAddress createCustomerAddressEntityA() {
        return CustomerAddress.builder()
                .phone("11111111")
                .city("city A")
                .country("country A")
                .postalCode("1111")
                .addressLine2("address line2")
                .addressLine1("address line1")
                .build();
    }

    public static CustomerAddress createCustomerAddressEntityB() {
        return CustomerAddress.builder()
                .phone("2222222")
                .city("city B")
                .country("country B")
                .postalCode("2222")
                .addressLine2("address line2")
                .addressLine1("address line1")
                .build();
    }

    // CustomerPayment
    public static CustomerPayment createCustomerPaymentEntityA() {
        return CustomerPayment.builder()
                .accountNo("663377889")
                .expiry(LocalDate.of(2046, 9, 9))
                .provider("HSBC")
                .type(CustomerPaymentType.CREDIT_CARD)
                .build();
    }

    public static CustomerPayment createCustomerPaymentEntityB() {
        return CustomerPayment.builder()
                .accountNo("663377777")
                .expiry(LocalDate.of(2041, 8, 1))
                .provider("Citi")
                .type(CustomerPaymentType.CREDIT_CARD)
                .build();
    }

    // Product
    public static Product createProductEntityA() {
        return Product.builder()
                .name("product A")
                .description("product description A")
                .price(BigDecimal.valueOf(100.1))
                .imageUrl("https://image/product/a")
                .discountLinkList(new ArrayList<>())
                .orderItemList(new ArrayList<>())
                .shoppingCartItemList(new ArrayList<>())
                .build();
    }

    public static Product createProductEntityB() {
        return Product.builder()
                .name("product B")
                .description("product description B")
                .price(BigDecimal.valueOf(10.1))
                .imageUrl("https://image/product/b")
                .discountLinkList(new ArrayList<>())
                .orderItemList(new ArrayList<>())
                .shoppingCartItemList(new ArrayList<>())
                .build();
    }

    // Product Category
    public static ProductCategory createProductCategoryEntityA() {
        return ProductCategory.builder()
                .name("category A")
                .description("category description A")
                .imageUrl("https://image/product/category/a")
                .productList(new ArrayList<>())
                .build();
    }

    public static ProductCategory createProductCategoryEntityB() {
        return ProductCategory.builder()
                .name("category B")
                .description("category description B")
                .imageUrl("https://image/product/category/b")
                .productList(new ArrayList<>())
                .build();
    }

    // Product Discount
    public static ProductDiscount createProductDiscountEntityA() {
        return ProductDiscount.builder()
                .name("discount A")
                .description("discount description A")
                .type("flat")
                .value(BigDecimal.valueOf(10.5))
                .active(false)
                .expiry(LocalDateTime.of(2024, 1, 15, 15, 30))
                .productLinkList(new ArrayList<>())
                .build();
    }

    public static ProductDiscount createProductDiscountEntityB() {
        return ProductDiscount.builder()
                .name("discount B")
                .description("discount description B")
                .type("flat")
                .value(BigDecimal.valueOf(12.5))
                .active(false)
                .expiry(LocalDateTime.of(2024, 2, 15, 15, 30))
                .productLinkList(new ArrayList<>())
                .build();
    }

    // Product Inventory
    public static ProductInventory createProductInventoryEntityA() {
        return ProductInventory.builder()
                .name("inventory A")
                .quantity(100)
                .build();
    }

    public static ProductInventory createProductInventoryEntityB() {
        return ProductInventory.builder()
                .name("inventory B")
                .quantity(100)
                .build();
    }


    // Order
    public static Order createOrderEntityA() {
        return Order.builder()
                .status(OrderStatus.ORDERED)
                .total(BigDecimal.valueOf(100.1))
                .orderItemList(new ArrayList<>())
                .build();
    }

    public static Order createOrderEntityB() {
        return Order.builder()
                .status(OrderStatus.DELIVERED)
                .total(BigDecimal.valueOf(10.1))
                .orderItemList(new ArrayList<>())
                .build();
    }

    public static OrderDto createOrderDtoA() {
        return OrderDto.builder()
                .status(OrderStatus.ORDERED)
                .total(BigDecimal.valueOf(100.1))
                .orderItemList(new ArrayList<>())
                .build();
    }

    public static OrderDto createOrderDtoB() {
        return OrderDto.builder()
                .status(OrderStatus.DELIVERED)
                .total(BigDecimal.valueOf(10.1))
                .orderItemList(new ArrayList<>())
                .build();
    }

    public static OrderTransaction createOrderTransactionEntityA() {
        return OrderTransaction.builder()
                .status(OrderTransactionStatus.PENDING)
                .amount(BigDecimal.valueOf(101.1))
                .accountNo("2222")
                .provider("HSBC")
                .build();
    }

    public static OrderTransaction createOrderTransactionEntityB() {
        return OrderTransaction.builder()
                .status(OrderTransactionStatus.PENDING)
                .amount(BigDecimal.valueOf(100.1))
                .accountNo("1111")
                .provider("Citi")
                .build();
    }

    public static OrderItem createOrderItemEntityA() {
        return OrderItem.builder()
                .quantity(10)
                .build();
    }

    public static OrderItem createOrderItemEntityB() {
        return OrderItem.builder()
                .quantity(20)
                .build();
    }

    public static ShoppingCart createShoppingCartEntityA() {
        return ShoppingCart.builder()
                .total(BigDecimal.valueOf(1000))
                .shoppingCartItemList(new ArrayList<>())
                .build();
    }

    public static ShoppingCart createShoppingCartEntityB() {
        return ShoppingCart.builder()
                .total(BigDecimal.valueOf(2000.1))
                .shoppingCartItemList(new ArrayList<>())
                .build();
    }

    public static ShoppingCartItem createShoppingCartItemEntityA() {
        return ShoppingCartItem.builder()
                .quantity(10)
                .build();
    }

    public static ShoppingCartItem createShoppingCartItemEntityB() {
        return ShoppingCartItem.builder()
                .quantity(20)
                .build();
    }
}
