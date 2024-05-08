-- Schema
CREATE SCHEMA IF NOT EXISTS mcshop;

-- Tables
CREATE TABLE IF NOT EXISTS `mcshop`.`customer` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `email` varchar(100) NOT NULL,
  `phone` varchar(100) NULL DEFAULT NULL,
  `nickname` varchar(100) NULL DEFAULT NULL,
  `password` varchar(100) NULL DEFAULT NULL,
  `first_name` varchar(100) NULL DEFAULT NULL,
  `last_name` varchar(100) NULL DEFAULT NULL,

  `created_at` datetime NOT NULL,
  `modified_at` datetime NULL DEFAULT NULL,
  `deleted_at` datetime NULL DEFAULT NULL,
  CONSTRAINT PK_Customer PRIMARY KEY (`id`) USING BTREE,
  CONSTRAINT UQ_Customer_Email UNIQUE (`email`)
) ENGINE=InnoDB;

CREATE TABLE IF NOT EXISTS `mcshop`.`customer_address` (
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
  CONSTRAINT PK_CustomerAddress PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB;

CREATE TABLE IF NOT EXISTS `mcshop`.`customer_payment` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `type` varchar(64) NOT NULL COMMENT 'PAYPAL; CREDIT_CARD',
  `provider` varchar(64) NOT NULL,
  `account_no` varchar(64) NOT NULL,
  `expiry` datetime NULL DEFAULT NULL,

  `customer_id` bigint NULL DEFAULT NULL,

  `created_at` datetime NOT NULL,
  `modified_at` datetime NULL DEFAULT NULL,
  `deleted_at` datetime NULL DEFAULT NULL,
  CONSTRAINT PK_CustomerPayment PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB;

CREATE TABLE IF NOT EXISTS `mcshop`.`product_category` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `name` varchar(100) NOT NULL,
  `description` text NULL DEFAULT NULL,
  `image_url` varchar(100) NULL DEFAULT NULL,

  `created_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `modified_at` datetime  NULL DEFAULT NULL,
  `deleted_at` datetime  NULL DEFAULT NULL,
  CONSTRAINT PK_ProductCategory PRIMARY KEY (`id`) USING BTREE,
  CONSTRAINT UQ_ProductCategory_Name UNIQUE (`name`)
) ENGINE=InnoDB;

CREATE TABLE IF NOT EXISTS `mcshop`.`product_discount` (
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
) ENGINE=InnoDB;

CREATE TABLE IF NOT EXISTS `mcshop`.`product` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `name` varchar(100) NOT NULL,
  `price` decimal NOT NULL,
  `description` text NULL DEFAULT NULL,
  `image_url` varchar(100) NULL DEFAULT NULL,

  `category_id` bigint NULL DEFAULT NULL,

  `created_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `modified_at` datetime  NULL DEFAULT NULL,
  `deleted_at` datetime  NULL DEFAULT NULL,
  CONSTRAINT PK_Product PRIMARY KEY (`id`) USING BTREE,
  CONSTRAINT UQ_Product_Name UNIQUE (`name`)
) ENGINE=InnoDB;

CREATE TABLE IF NOT EXISTS `mcshop`.`product_discount_link` (
  `product_id` bigint NOT NULL,
  `discount_id` bigint NOT NULL,

  `created_at` datetime NOT NULL,
  CONSTRAINT PK_ProductDiscountLink PRIMARY KEY (`product_id`, `discount_id`) USING BTREE
) ENGINE=InnoDB;

CREATE TABLE IF NOT EXISTS `mcshop`.`product_inventory` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `quantity` int NOT NULL,
  `name` varchar(255) NOT NULL,

  `product_id` bigint NULL DEFAULT NULL,

  `created_at` datetime NOT NULL,
  `modified_at` datetime  NULL DEFAULT NULL,
  `deleted_at` datetime NULL DEFAULT NULL,
  CONSTRAINT PK_ProductInventory PRIMARY KEY (`id`) USING BTREE,
  CONSTRAINT UQ_ProductInventory_Name UNIQUE (`name`),
  CONSTRAINT UQ_ProductInventory_ProductId UNIQUE (`product_id`)
) ENGINE=InnoDB;

CREATE TABLE IF NOT EXISTS `mcshop`.`orders` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `status` varchar(100) NOT NULL COMMENT 'ORDERED, PROCESSED, DELIVERED, REFUNDED, CANCELED',
  `total` decimal NOT NULL,

  `customer_id` bigint NULL DEFAULT NULL,
  `customer_address_id` bigint NULL DEFAULT NULL,

  `created_at` datetime NOT NULL,
  `modified_at` datetime  NULL DEFAULT NULL,
  `deleted_at` datetime NULL DEFAULT NULL,
  CONSTRAINT PK_Orders PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB;

CREATE TABLE IF NOT EXISTS `mcshop`.`order_transaction` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `amount` decimal NOT NULL,
  `provider` varchar(64) NOT NULL,
  `account_no` varchar(64) NOT NULL,
  `status` varchar(64) NOT NULL COMMENT 'PENDING, COMPLETED, FAILED',

  `order_id` bigint NULL DEFAULT NULL,

  `created_at` datetime NOT NULL,
  `modified_at` datetime  NULL DEFAULT NULL,
  `deleted_at` datetime NULL DEFAULT NULL,
  CONSTRAINT PK_Transaction PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB;

CREATE TABLE IF NOT EXISTS `mcshop`.`order_item` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `quantity` int NOT NULL,

  `order_id` bigint NULL DEFAULT NULL,
  `product_id` bigint NULL DEFAULT NULL,

  `created_at` datetime NOT NULL,
  `modified_at` datetime  NULL DEFAULT NULL,
  `deleted_at` datetime NULL DEFAULT NULL,
  CONSTRAINT PK_OrderItem PRIMARY KEY (`id`) USING BTREE,
  CONSTRAINT UQ_OrderItem_OrderId_ProductId UNIQUE (`order_id`, `product_id`)
) ENGINE=InnoDB;

CREATE TABLE IF NOT EXISTS `mcshop`.`shopping_cart` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `total` decimal NOT NULL,

  `customer_id` bigint NULL DEFAULT NULL,

  `created_at` datetime NOT NULL,
  `modified_at` datetime NULL DEFAULT NULL,
  `deleted_at` datetime NULL DEFAULT NULL,
  CONSTRAINT PK_ShoppingCart PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB;

CREATE TABLE IF NOT EXISTS `mcshop`.`shopping_cart_item` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `quantity` bigint NOT NULL,

  `product_id` bigint NULL DEFAULT NULL,
  `shopping_cart_id` bigint NULL DEFAULT NULL,

  `created_at` datetime NOT NULL,
  `modified_at` datetime NULL DEFAULT NULL,
  `deleted_at` datetime NULL DEFAULT NULL,
  CONSTRAINT PK_ShoppingCartItem PRIMARY KEY (`id`) USING BTREE,
  CONSTRAINT UQ_ShoppingCartItem_ProductId_ShoppingCartId UNIQUE (`product_id`, `shopping_cart_id`)
) ENGINE=InnoDB;

-- Data
START TRANSACTION;

INSERT IGNORE INTO mcshop.product_category (id, name, description, image_url, created_at) VALUES (1, "category1", "A Description for category1", "/images/category/category1.jpg", default);
INSERT IGNORE INTO mcshop.product_category (id, name, description, image_url, created_at) VALUES (2, "category2", "A Description for category2", "/images/category/category2.jpg", default);
INSERT IGNORE INTO mcshop.product_category (id, name, description, image_url, created_at) VALUES (3, "category3", "A Description for category3", "/images/category/category3.jpg", default);

INSERT IGNORE INTO mcshop.product (id, name, price, description, image_url, category_id, created_at) VALUES (1, "product1", 10.5, "A good product1", "/images/products/product1.jpg", 1, default);
INSERT IGNORE INTO mcshop.product (id, name, price, description, image_url, category_id, created_at) VALUES (2, "product2", 29.3, "A good product2", "/images/products/product2.jpg", 2, default);
INSERT IGNORE INTO mcshop.product (id, name, price, description, image_url, category_id, created_at) VALUES (3, "product3", 22.5, "A good product3", "/images/products/product3.jpg", 2, default);
INSERT IGNORE INTO mcshop.product (id, name, price, description, image_url, category_id, created_at) VALUES (4, "product4", 25.5, "A good product4", "/images/products/product4.jpg", 2, default);
INSERT IGNORE INTO mcshop.product (id, name, price, description, image_url, category_id, created_at) VALUES (5, "product5", 201, "A good product5", "/images/products/product5.jpg", 3, default);

COMMIT;