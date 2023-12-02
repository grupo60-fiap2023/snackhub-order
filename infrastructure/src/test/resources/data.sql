INSERT INTO orders (id, customer_id, order_identifier, observation, status, created_at)
VALUES (1, 1, 'ORDER123', 'First order', 1, '2023-01-01 12:00:00.000');

INSERT INTO order_items (order_id, name, price, quantity, category)
VALUES (1, 'Product A', 15.99, 2, 'Lanche');

INSERT INTO orders (id, customer_id, order_identifier, observation, status, created_at)
VALUES (2, 2, 'ORDER345', 'Second order', 2, '2023-01-01 12:00:00.000');

INSERT INTO order_items (order_id, name, price, quantity, category)
VALUES (2, 'Product B', 15.99, 2, 'Lanche');