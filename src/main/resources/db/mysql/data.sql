Start transaction;

insert into shop.product_category (name, description, image_url, created_at) values ("category1", "A Description for category1", "/images/category/category1.jpg", default);
insert into shop.product_category (name, description, image_url, created_at) values ("category2", "A Description for category2", "/images/category/category2.jpg", default);
insert into shop.product_category (name, description, image_url, created_at) values ("category3", "A Description for category3", "/images/category/category3.jpg", default);

INSERT INTO shop.product (name, price, description, image_url, category_id, created_at) VALUES ("product1", 10.5, "A good product1", "/images/products/product1.jpg", 1, default);
INSERT INTO shop.product (name, price, description, image_url, category_id, created_at) VALUES ("product2", 29.3, "A good product2", "/images/products/product2.jpg", 2, default);
INSERT INTO shop.product (name, price, description, image_url, category_id, created_at) VALUES ("product3", 22.5, "A good product3", "/images/products/product3.jpg", 2, default);
INSERT INTO shop.product (name, price, description, image_url, category_id, created_at) VALUES ("product4", 25.5, "A good product4", "/images/products/product4.jpg", 2, default);
INSERT INTO shop.product (name, price, description, image_url, category_id, created_at) VALUES ("product5", 33, "A good product5", "/images/products/product5.jpg", 3, default);

commit;