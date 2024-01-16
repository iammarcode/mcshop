package com.marcoecommerce.shop.utils;

import com.marcoecommerce.shop.model.orderItem.OrderItemEntity;
import com.marcoecommerce.shop.model.orderTransaction.OrderTransactionEntity;
import com.marcoecommerce.shop.model.orderTransaction.OrderTransactionStatus;
import com.marcoecommerce.shop.model.product.ProductEntity;
import com.marcoecommerce.shop.model.productCategory.ProductCategoryEntity;
import com.marcoecommerce.shop.model.productDiscount.ProductDiscountEntity;
import com.marcoecommerce.shop.model.productInventory.ProductInventoryEntity;
import com.marcoecommerce.shop.model.shoppingCart.ShoppingCartEntity;
import com.marcoecommerce.shop.model.shoppingCartItem.ShoppingCartItemEntity;
import com.marcoecommerce.shop.model.user.UserDto;
import com.marcoecommerce.shop.model.order.OrderDto;
import com.marcoecommerce.shop.model.user.UserEntity;
import com.marcoecommerce.shop.model.order.OrderEntity;
import com.marcoecommerce.shop.model.order.OrderStatus;
import com.marcoecommerce.shop.model.userAddress.UserAddressEntity;
import com.marcoecommerce.shop.model.userPayment.UserPaymentEntity;
import com.marcoecommerce.shop.model.userPayment.UserPaymentType;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class TestDataUtil {

    private TestDataUtil() {
    }

    // User
    public static UserEntity createUserEntityA() {
        return UserEntity.builder()
                .email("aaa@example.com")
                .phone("11111111")
                .username("usernameA")
                .password("passwordA")
                .firstName("firstnameA")
                .lastName("lastnameA")
                .addressList(new ArrayList<>())
                .paymentList(new ArrayList<>())
                .orderList(new ArrayList<>())
                .build();
    }

    public static UserEntity createUserEntityB() {
        return UserEntity.builder()
                .email("bbb@example.com")
                .phone("22222222")
                .username("usernameB")
                .password("passwordB")
                .firstName("firstnameB")
                .lastName("lastnameB")
                .addressList(new ArrayList<>())
                .paymentList(new ArrayList<>())
                .orderList(new ArrayList<>())
                .build();
    }

    public static UserDto createUserDtoA() {
        return UserDto.builder()
                .email("aaa@example.com")
                .phone("11111111")
                .username("usernameA")
                .password("passwordA")
                .firstName("firstnameA")
                .lastName("lastnameA")
                .addressList(new ArrayList<>())
                .paymentList(new ArrayList<>())
                .orderList(new ArrayList<>())
                .build();
    }

    public static UserDto createUserDtoB() {
        return UserDto.builder()
                .email("bbb@example.com")
                .phone("222222")
                .username("usernameB")
                .password("passwordB")
                .firstName("firstnameB")
                .lastName("lastnameB")
                .addressList(new ArrayList<>())
                .paymentList(new ArrayList<>())
                .orderList(new ArrayList<>())
                .build();
    }

    // UserAddress
    public static UserAddressEntity createUserAddressEntityA() {
        return UserAddressEntity.builder()
                .phone("11111111")
                .city("city A")
                .country("country A")
                .postalCode("1111")
                .addressLine2("address line2")
                .addressLine1("address line1")
                .build();
    }

    public static UserAddressEntity createUserAddressEntityB() {
        return UserAddressEntity.builder()
                .phone("2222222")
                .city("city B")
                .country("country B")
                .postalCode("2222")
                .addressLine2("address line2")
                .addressLine1("address line1")
                .build();
    }

    // UserPayment
    public static UserPaymentEntity createUserPaymentEntityA() {
        return UserPaymentEntity.builder()
                .accountNo("663377889")
                .expiry(LocalDate.of(2046, 9, 9))
                .provider("HSBC")
                .type(UserPaymentType.CREDIT_CARD)
                .build();
    }

    public static UserPaymentEntity createUserPaymentEntityB() {
        return UserPaymentEntity.builder()
                .accountNo("663377777")
                .expiry(LocalDate.of(2041, 8, 1))
                .provider("Citi")
                .type(UserPaymentType.CREDIT_CARD)
                .build();
    }

    // Product
    public static ProductEntity createProductEntityA() {
        return ProductEntity.builder()
                .name("product A")
                .description("product description A")
                .price(BigDecimal.valueOf(100.1))
                .imageUrl("https://image/product/a")
                .sku("sku_a")
                .discountLinkList(new ArrayList<>())
                .orderItemList(new ArrayList<>())
                .shoppingCartItemList(new ArrayList<>())
                .build();
    }

    public static ProductEntity createProductEntityB() {
        return ProductEntity.builder()
                .name("product B")
                .description("product description B")
                .price(BigDecimal.valueOf(10.1))
                .imageUrl("https://image/product/b")
                .sku("sku_b")
                .discountLinkList(new ArrayList<>())
                .orderItemList(new ArrayList<>())
                .shoppingCartItemList(new ArrayList<>())
                .build();
    }

    // Product Category
    public static ProductCategoryEntity createProductCategoryEntityA() {
        return ProductCategoryEntity.builder()
                .name("category A")
                .description("category description A")
                .imageUrl("https://image/product/category/a")
                .productList(new ArrayList<>())
                .build();
    }

    public static ProductCategoryEntity createProductCategoryEntityB() {
        return ProductCategoryEntity.builder()
                .name("category B")
                .description("category description B")
                .imageUrl("https://image/product/category/b")
                .productList(new ArrayList<>())
                .build();
    }

    // Product Discount
    public static ProductDiscountEntity createProductDiscountEntityA() {
        return ProductDiscountEntity.builder()
                .name("discount A")
                .description("discount description A")
                .type("flat")
                .value(BigDecimal.valueOf(10.5))
                .active(false)
                .expiry(LocalDateTime.of(2024, 1, 15, 15, 30))
                .productLinkList(new ArrayList<>())
                .build();
    }

    public static ProductDiscountEntity createProductDiscountEntityB() {
        return ProductDiscountEntity.builder()
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
    public static ProductInventoryEntity createProductInventoryEntityA() {
        return ProductInventoryEntity.builder()
                .name("inventory A")
                .quantity(100)
                .build();
    }

    public static ProductInventoryEntity createProductInventoryEntityB() {
        return ProductInventoryEntity.builder()
                .name("inventory B")
                .quantity(100)
                .build();
    }


    // Order
    public static OrderEntity createOrderEntityA() {
        return OrderEntity.builder()
                .status(OrderStatus.ORDERED)
                .total(BigDecimal.valueOf(100.1))
                .orderItemList(new ArrayList<>())
                .build();
    }

    public static OrderEntity createOrderEntityB() {
        return OrderEntity.builder()
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

    public static OrderTransactionEntity createOrderTransactionEntityA() {
        return OrderTransactionEntity.builder()
                .status(OrderTransactionStatus.PENDING)
                .amount(BigDecimal.valueOf(101.1))
                .accountNo("2222")
                .provider("HSBC")
                .build();
    }

    public static OrderTransactionEntity createOrderTransactionEntityB() {
        return OrderTransactionEntity.builder()
                .status(OrderTransactionStatus.PENDING)
                .amount(BigDecimal.valueOf(100.1))
                .accountNo("1111")
                .provider("Citi")
                .build();
    }

    public static OrderItemEntity createOrderItemEntityA() {
        return OrderItemEntity.builder()
                .quantity(10)
                .build();
    }

    public static OrderItemEntity createOrderItemEntityB() {
        return OrderItemEntity.builder()
                .quantity(20)
                .build();
    }

    public static ShoppingCartEntity createShoppingCartEntityA() {
        return ShoppingCartEntity.builder()
                .total(BigDecimal.valueOf(1000))
                .shoppingCartItemList(new ArrayList<>())
                .build();
    }

    public static ShoppingCartEntity createShoppingCartEntityB() {
        return ShoppingCartEntity.builder()
                .total(BigDecimal.valueOf(2000.1))
                .shoppingCartItemList(new ArrayList<>())
                .build();
    }

    public static ShoppingCartItemEntity createShoppingCartItemEntityA() {
        return ShoppingCartItemEntity.builder()
                .quantity(10)
                .build();
    }

    public static ShoppingCartItemEntity createShoppingCartItemEntityB() {
        return ShoppingCartItemEntity.builder()
                .quantity(20)
                .build();
    }
}
