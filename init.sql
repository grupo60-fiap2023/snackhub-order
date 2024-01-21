CREATE TABLE orders (
	id INT AUTO_INCREMENT,
	customer_id INT,
	order_identifier VARCHAR(30),
	observation VARCHAR(255),
	status INT not null,
	created_at DATETIME(3),
	primary key (id)
);

CREATE TABLE order_items (
  id INT AUTO_INCREMENT,
  order_id INT,
  name varchar(255) not null,
  price decimal(19,2) not null,
  quantity INT,
  category varchar(255) not null,
  FOREIGN KEY (order_id) REFERENCES orders(id),
  primary key (id)
);