START TRANSACTION;

-- customer

-- product_category
INSERT IGNORE INTO shop.product_category (id, name, description, image_url, created_at) VALUES (1, "category1", "A Description for category1", "/images/category/category1.jpg", default);
INSERT IGNORE INTO shop.product_category (id, name, description, image_url, created_at) VALUES (2, "category2", "A Description for category2", "/images/category/category2.jpg", default);
INSERT IGNORE INTO shop.product_category (id, name, description, image_url, created_at) VALUES (3, "category3", "A Description for category3", "/images/category/category3.jpg", default);

-- product
INSERT IGNORE INTO shop.product (id, name, price, description, image_url, category_id, created_at) VALUES (1, "product1", 10.5, "A good product1", "/images/products/product1.jpg", 1, default);
INSERT IGNORE INTO shop.product (id, name, price, description, image_url, category_id, created_at) VALUES (2, "product2", 29.3, "A good product2", "/images/products/product2.jpg", 2, default);
INSERT IGNORE INTO shop.product (id, name, price, description, image_url, category_id, created_at) VALUES (3, "product3", 22.5, "A good product3", "/images/products/product3.jpg", 2, default);
INSERT IGNORE INTO shop.product (id, name, price, description, image_url, category_id, created_at) VALUES (4, "product4", 25.5, "A good product4", "/images/products/product4.jpg", 2, default);
INSERT IGNORE INTO shop.product (id, name, price, description, image_url, category_id, created_at) VALUES (5, "product5", 201, "A good product5", "/images/products/product5.jpg", 3, default);

COMMIT;