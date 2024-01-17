-- Schema
CREATE SCHEMA IF NOT EXISTS shop;

-- Tables
CREATE TABLE IF NOT EXISTS `shop`.`customer` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `email` varchar(100) NOT NULL,
  `phone` varchar(100) NOT NULL,
  `username` varchar(100) NOT NULL,
  `password` varchar(100) NULL DEFAULT NULL,
  `first_name` varchar(100) NULL DEFAULT NULL,
  `last_name` varchar(100) NULL DEFAULT NULL,

  `created_at` datetime NOT NULL,
  `modified_at` datetime NULL DEFAULT NULL,
  `deleted_at` datetime NULL DEFAULT NULL,
  CONSTRAINT PK_Customer PRIMARY KEY (`id`) USING BTREE,
  CONSTRAINT UQ_Customer_Email UNIQUE (`email`),
  CONSTRAINT UQ_Customer_Phone UNIQUE (`phone`),
  CONSTRAINT UQ_Customer_Username UNIQUE (`username`)
);

CREATE TABLE IF NOT EXISTS `shop`.`customer_address` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `address_line1` text NULL DEFAULT NULL,
  `address_line2` text NULL DEFAULT NULL,
  `postal_code` varchar(100) NOT NULL,
  `city` varchar(100) NOT NULL,
  `country` varchar(100) NOT NULL,
  `phone` varchar(64) NOT NULL,

  `customer_id` bigint NULL DEFAULT NULL,

  `created_at` datetime NOT NULL,
  `modified_at` datetime NULL DEFAULT NULL,
  `deleted_at` datetime NULL DEFAULT NULL,
  CONSTRAINT PK_CustomerAddress PRIMARY KEY (`id`) USING BTREE,
  CONSTRAINT FK_CustomerAddress_Customer FOREIGN KEY (`customer_id`) REFERENCES `customer` (`id`)
);

CREATE TABLE IF NOT EXISTS `shop`.`customer_payment` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `type` varchar(64) NOT NULL COMMENT 'PAYPAL; CREDIT_CARD',
  `provider` varchar(64) NOT NULL,
  `account_no` varchar(64) NOT NULL,
  `expiry` datetime NULL DEFAULT NULL,

  `customer_id` bigint NULL DEFAULT NULL,

  `created_at` datetime NOT NULL,
  `modified_at` datetime NULL DEFAULT NULL,
  `deleted_at` datetime NULL DEFAULT NULL,
  CONSTRAINT PK_CustomerPayment PRIMARY KEY (`id`) USING BTREE,
  CONSTRAINT FK_CustomerPayment_Customer FOREIGN KEY (`customer_id`) REFERENCES `customer` (`id`),
  CONSTRAINT FK_CustomerPayment_AccountNo UNIQUE (`account_no`)
);

CREATE TABLE IF NOT EXISTS `shop`.`product_category` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `name` varchar(100) NOT NULL,
  `description` text NULL DEFAULT NULL,
  `image_url` varchar(100) NULL DEFAULT NULL,

  `created_at` datetime NOT NULL,
  `modified_at` datetime  NULL DEFAULT NULL,
  `deleted_at` datetime  NULL DEFAULT NULL,
  CONSTRAINT PK_ProductCategory PRIMARY KEY (`id`) USING BTREE,
  CONSTRAINT UQ_ProductCategory_Name UNIQUE (`name`)
);

CREATE TABLE IF NOT EXISTS `shop`.`product_discount` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL,
  `description` text NULL DEFAULT NULL,
  `type` varchar(100) NOT NULL COMMENT 'percent; flat',
  `value` decimal NOT NULL,
  `active` boolean NOT NULL,
  `expiry` datetime NULL DEFAULT NULL,

  `created_at` datetime NOT NULL,
  `modified_at` datetime NULL DEFAULT NULL,
  `deleted_at` datetime NULL DEFAULT NULL,
  CONSTRAINT PK_ProductDiscount PRIMARY KEY (`id`) USING BTREE,
  CONSTRAINT UQ_ProductDiscount_Name UNIQUE (`name`)
);

CREATE TABLE IF NOT EXISTS `shop`.`product` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `name` varchar(100) NOT NULL,
  `sku` varchar(100) NOT NULL,
  `price` decimal NOT NULL,
  `description` text NULL DEFAULT NULL,
  `image_url` varchar(100) NULL DEFAULT NULL,

  `category_id` bigint NULL DEFAULT NULL,

  `created_at` datetime NOT NULL,
  `modified_at` datetime  NULL DEFAULT NULL,
  `deleted_at` datetime  NULL DEFAULT NULL,
  CONSTRAINT PK_Product PRIMARY KEY (`id`) USING BTREE,
  CONSTRAINT FK_Product_ProductCategory FOREIGN KEY (`category_id`) REFERENCES `product_category` (`id`),
  CONSTRAINT UQ_Product_Name UNIQUE (`name`),
  CONSTRAINT UQ_Product_Sku UNIQUE (`sku`)
);

CREATE TABLE IF NOT EXISTS `shop`.`product_discount_link` (
  `product_id` bigint NOT NULL,
  `discount_id` bigint NOT NULL,

  `created_at` datetime NOT NULL,
  CONSTRAINT PK_ProductDiscountLink PRIMARY KEY (`product_id`, `discount_id`) USING BTREE,
  CONSTRAINT FK_ProductDiscountLink_Product FOREIGN KEY (`product_id`) REFERENCES `product` (`id`),
  CONSTRAINT FK_ProductDiscountLink_Discount FOREIGN KEY (`discount_id`) REFERENCES `product_discount` (`id`)
);

CREATE TABLE IF NOT EXISTS `shop`.`product_inventory` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `quantity` int NOT NULL,
  `name` varchar(255) NOT NULL,

  `product_id` bigint NULL DEFAULT NULL,

  `created_at` datetime NOT NULL,
  `modified_at` datetime  NULL DEFAULT NULL,
  `deleted_at` datetime NULL DEFAULT NULL,
  CONSTRAINT PK_ProductInventory PRIMARY KEY (`id`) USING BTREE,
  CONSTRAINT FK_ProductInventory_Product FOREIGN KEY (`product_id`) REFERENCES `product` (`id`),
  CONSTRAINT UQ_ProductInventory_Name UNIQUE (`name`),
  CONSTRAINT UQ_ProductInventory_ProductId UNIQUE (`product_id`)
);

CREATE TABLE IF NOT EXISTS `shop`.`orders` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `status` varchar(100) NOT NULL COMMENT 'ORDERED, PROCESSED, DELIVERED, REFUNDED, CANCELED',
  `total` decimal NOT NULL,

  `customer_id` bigint NULL DEFAULT NULL,
  `customer_address_id` bigint NULL DEFAULT NULL,

  `created_at` datetime NOT NULL,
  `modified_at` datetime  NULL DEFAULT NULL,
  `deleted_at` datetime NULL DEFAULT NULL,
  CONSTRAINT PK_Orders PRIMARY KEY (`id`) USING BTREE,
  CONSTRAINT FK_Orders_Customer FOREIGN KEY (`customer_id`) REFERENCES `customer` (`id`),
  CONSTRAINT FK_Orders_CustomerAddress FOREIGN KEY (`customer_address_id`) REFERENCES `customer_address` (`id`)
);

CREATE TABLE IF NOT EXISTS `shop`.`order_transaction` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `amount` decimal NOT NULL,
  `provider` varchar(64) NOT NULL,
  `account_no` varchar(64) NOT NULL,
  `status` varchar(64) NOT NULL COMMENT 'PENDING, COMPLETED, FAILED',

  `order_id` bigint NULL DEFAULT NULL,

  `created_at` datetime NOT NULL,
  `modified_at` datetime  NULL DEFAULT NULL,
  `deleted_at` datetime NULL DEFAULT NULL,
  CONSTRAINT PK_Transaction PRIMARY KEY (`id`) USING BTREE,
  CONSTRAINT FK_OrderTransaction_Orders FOREIGN KEY (`order_id`) REFERENCES `orders` (`id`)
);

CREATE TABLE IF NOT EXISTS `shop`.`order_item` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `quantity` int NOT NULL,

  `order_id` bigint NULL DEFAULT NULL,
  `product_id` bigint NULL DEFAULT NULL,

  `created_at` datetime NOT NULL,
  `modified_at` datetime  NULL DEFAULT NULL,
  `deleted_at` datetime NULL DEFAULT NULL,
  CONSTRAINT PK_OrderItem PRIMARY KEY (`id`) USING BTREE,
  CONSTRAINT FK_OrderItem_Orders FOREIGN KEY (`order_id`) REFERENCES `orders` (`id`),
  CONSTRAINT FK_OrderItem_Product FOREIGN KEY (`product_id`) REFERENCES `product` (`id`),
  CONSTRAINT UQ_OrderItem_OrderId_ProductId UNIQUE (`order_id`, `product_id`)
);

CREATE TABLE IF NOT EXISTS `shop`.`shopping_cart` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `total` decimal NOT NULL,

  `customer_id` bigint NULL DEFAULT NULL,

  `created_at` datetime NOT NULL,
  `modified_at` datetime NULL DEFAULT NULL,
  `deleted_at` datetime NULL DEFAULT NULL,
  CONSTRAINT PK_ShoppingCart PRIMARY KEY (`id`) USING BTREE,
  CONSTRAINT FK_ShoppingCart_Customer FOREIGN KEY (`customer_id`) REFERENCES `customer` (`id`)
);

CREATE TABLE IF NOT EXISTS `shop`.`shopping_cart_item` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `quantity` bigint NOT NULL,

  `product_id` bigint NULL DEFAULT NULL,
  `shopping_cart_id` bigint NULL DEFAULT NULL,

  `created_at` datetime NOT NULL,
  `modified_at` datetime NULL DEFAULT NULL,
  `deleted_at` datetime NULL DEFAULT NULL,
  CONSTRAINT PK_ShoppingCartItem PRIMARY KEY (`id`) USING BTREE,
  CONSTRAINT FK_ShoppingCartItem_Product FOREIGN KEY (`product_id`) REFERENCES `product` (`id`),
  CONSTRAINT FK_ShoppingCartItem_ShoppingCart FOREIGN KEY (`shopping_cart_id`) REFERENCES `shopping_cart` (`id`),
  CONSTRAINT UQ_ShoppingCartItem_ProductId_ShoppingCartId UNIQUE (`product_id`, `shopping_cart_id`)
);