INSERT INTO categories (name, description) VALUES ('Books', 'Books and literature');
INSERT INTO categories (name, description) VALUES ('Electronics', 'Electronic devices');
INSERT INTO categories (name, description) VALUES ('Computers', 'Laptops and Desktops');
INSERT INTO categories (name, description) VALUES ('Fashion', 'Clothing and Accessories');
INSERT INTO categories (name, description) VALUES ('Home & Garden', 'Furniture and Decoration');

INSERT INTO products (name, description, price, image_url) VALUES ('The Lord of the Rings', 'A high fantasy novel written by English author and scholar J. R. R. Tolkien.', 90.5, '');
INSERT INTO products (name, description, price, image_url) VALUES ('Smart TV', '4K UHD Smart TV with HDR and Alexa built-in.', 2190.0, '');
INSERT INTO products (name, description, price, image_url) VALUES ('Macbook Pro', 'Apple MacBook Pro with M2 chip, 13-inch Retina Display.', 1250.0, '');
INSERT INTO products (name, description, price, image_url) VALUES ('PC Gamer', 'High performance Gaming PC with RTX 3060 and Ryzen 5.', 1200.0, '');
INSERT INTO products (name, description, price, image_url) VALUES ('Rails for Dummies', 'Learn Ruby on Rails the easy way.', 100.99, '');
INSERT INTO products (name, description, price, image_url) VALUES ('Harry Potter and the Sorcerers Stone', 'Fantasy novel.', 45.0, '');
INSERT INTO products (name, description, price, image_url) VALUES ('Clean Code', 'A Handbook of Agile Software Craftsmanship.', 150.0, '');
INSERT INTO products (name, description, price, image_url) VALUES ('Domain-Driven Design', 'Tackling Complexity in the Heart of Software.', 200.0, '');
INSERT INTO products (name, description, price, image_url) VALUES ('IPhone 13', 'Apple smartphone.', 5000.0, '');
INSERT INTO products (name, description, price, image_url) VALUES ('Samsung Galaxy S22', 'Samsung smartphone.', 4500.0, '');
INSERT INTO products (name, description, price, image_url) VALUES ('PlayStation 5', 'Next-gen console.', 4500.0, '');
INSERT INTO products (name, description, price, image_url) VALUES ('Xbox Series X', 'Microsoft console.', 4000.0, '');
INSERT INTO products (name, description, price, image_url) VALUES ('Nintendo Switch', 'Hybrid console.', 2000.0, '');
INSERT INTO products (name, description, price, image_url) VALUES ('Dell XPS 13', 'Ultrabook.', 8000.0, '');
INSERT INTO products (name, description, price, image_url) VALUES ('Mechanical Keyboard', 'RGB keyboard.', 300.0, '');
INSERT INTO products (name, description, price, image_url) VALUES ('Gaming Mouse', 'High precision mouse.', 200.0, '');
INSERT INTO products (name, description, price, image_url) VALUES ('Monitor 4K', '27 inch 4K monitor.', 2500.0, '');
INSERT INTO products (name, description, price, image_url) VALUES ('Headset Noise Cancelling', 'Wireless headset.', 800.0, '');
INSERT INTO products (name, description, price, image_url) VALUES ('T-Shirt Black', 'Cotton t-shirt.', 50.0, '');
INSERT INTO products (name, description, price, image_url) VALUES ('Jeans Blue', 'Classic denim jeans.', 120.0, '');
INSERT INTO products (name, description, price, image_url) VALUES ('Sneakers White', 'Comfortable sneakers.', 250.0, '');
INSERT INTO products (name, description, price, image_url) VALUES ('Leather Jacket', 'Black leather jacket.', 600.0, '');
INSERT INTO products (name, description, price, image_url) VALUES ('Summer Dress', 'Floral dress.', 150.0, '');
INSERT INTO products (name, description, price, image_url) VALUES ('Office Chair', 'Ergonomic chair.', 800.0, '');
INSERT INTO products (name, description, price, image_url) VALUES ('Desk Lamp', 'LED desk lamp.', 100.0, '');
INSERT INTO products (name, description, price, image_url) VALUES ('Coffee Table', 'Wooden coffee table.', 300.0, '');
INSERT INTO products (name, description, price, image_url) VALUES ('Sofa Grey', '3-seat sofa.', 2000.0, '');
INSERT INTO products (name, description, price, image_url) VALUES ('Bookshelf', 'Wooden bookshelf.', 400.0, '');
INSERT INTO products (name, description, price, image_url) VALUES ('Plant Pot', 'Ceramic pot.', 50.0, '');
INSERT INTO products (name, description, price, image_url) VALUES ('Smart Watch', 'Fitness tracker.', 800.0, '');

INSERT INTO product_category (product_id, category_id) VALUES (1, 1);
INSERT INTO product_category (product_id, category_id) VALUES (2, 2);
INSERT INTO product_category (product_id, category_id) VALUES (2, 3);
INSERT INTO product_category (product_id, category_id) VALUES (3, 3);
INSERT INTO product_category (product_id, category_id) VALUES (4, 3);
INSERT INTO product_category (product_id, category_id) VALUES (5, 2);
INSERT INTO product_category (product_id, category_id) VALUES (6, 1);
INSERT INTO product_category (product_id, category_id) VALUES (7, 1);
INSERT INTO product_category (product_id, category_id) VALUES (7, 3);
INSERT INTO product_category (product_id, category_id) VALUES (8, 1);
INSERT INTO product_category (product_id, category_id) VALUES (8, 3);
INSERT INTO product_category (product_id, category_id) VALUES (9, 2);
INSERT INTO product_category (product_id, category_id) VALUES (9, 3);
INSERT INTO product_category (product_id, category_id) VALUES (10, 2);
INSERT INTO product_category (product_id, category_id) VALUES (11, 2);
INSERT INTO product_category (product_id, category_id) VALUES (12, 2);
INSERT INTO product_category (product_id, category_id) VALUES (13, 2);
INSERT INTO product_category (product_id, category_id) VALUES (14, 3);
INSERT INTO product_category (product_id, category_id) VALUES (15, 3);
INSERT INTO product_category (product_id, category_id) VALUES (16, 3);
INSERT INTO product_category (product_id, category_id) VALUES (17, 3);
INSERT INTO product_category (product_id, category_id) VALUES (17, 2);
INSERT INTO product_category (product_id, category_id) VALUES (18, 2);
INSERT INTO product_category (product_id, category_id) VALUES (19, 4);
INSERT INTO product_category (product_id, category_id) VALUES (20, 4);
INSERT INTO product_category (product_id, category_id) VALUES (21, 4);
INSERT INTO product_category (product_id, category_id) VALUES (22, 4);
INSERT INTO product_category (product_id, category_id) VALUES (23, 4);
INSERT INTO product_category (product_id, category_id) VALUES (24, 5);
INSERT INTO product_category (product_id, category_id) VALUES (25, 5);
INSERT INTO product_category (product_id, category_id) VALUES (26, 5);
INSERT INTO product_category (product_id, category_id) VALUES (27, 5);
INSERT INTO product_category (product_id, category_id) VALUES (28, 5);
INSERT INTO product_category (product_id, category_id) VALUES (29, 5);
INSERT INTO product_category (product_id, category_id) VALUES (30, 2);

INSERT INTO users (username, email, password) VALUES ('maria', 'maria@gmail.com', '123456');
INSERT INTO users (username, email, password) VALUES ('alex', 'alex@gmail.com', '123456');

INSERT INTO customers (user_id, cpf, phone_number, birth_date) VALUES (1, '12345678901', '988888888', '1998-07-23');
INSERT INTO customers (user_id, cpf, phone_number, birth_date) VALUES (2, '98765432109', '977777777', '1995-10-15');

INSERT INTO customer_addresses (customer_id, state, city, street, number, neighborhood, complement, zip_code) VALUES (1, 'SP', 'Sao Paulo', 'Rua Flores', '300', 'Jardim', 'Apto 203', '38400000');
INSERT INTO customer_addresses (customer_id, state, city, street, number, neighborhood, complement, zip_code) VALUES (1, 'MG', 'Uberlandia', 'Avenida Matos', '105', 'Centro', 'Sala 800', '38400123');

INSERT INTO wallets (balance, customer_id) VALUES (5000.0, 1);
INSERT INTO wallets (balance, customer_id) VALUES (3000.0, 2);

INSERT INTO orders (moment, status, customer_id) VALUES ('2022-06-20T19:53:07Z', 'PAID', 1);
INSERT INTO orders (moment, status, customer_id) VALUES ('2022-07-21T03:42:10Z', 'WAITING_PAYMENT', 2);
INSERT INTO orders (moment, status, customer_id) VALUES ('2022-07-22T15:21:20Z', 'PAID', 1);

INSERT INTO order_items (order_id, product_id, quantity, price) VALUES (1, 1, 2, 90.5);
INSERT INTO order_items (order_id, product_id, quantity, price) VALUES (1, 3, 1, 1250.0);
INSERT INTO order_items (order_id, product_id, quantity, price) VALUES (2, 3, 2, 1250.0);
INSERT INTO order_items (order_id, product_id, quantity, price) VALUES (3, 5, 2, 100.99);

INSERT INTO payments (order_id, moment) VALUES (1, '2022-06-20T21:53:07Z');
INSERT INTO payments (order_id, moment) VALUES (3, '2022-07-22T16:21:20Z');
