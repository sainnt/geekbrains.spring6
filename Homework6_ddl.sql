CREATE TABLE users
(
    id   BIGINT PRIMARY KEY,
    name VARCHAR NOT NULL
);

CREATE TABLE products
(
    id   BIGINT PRIMARY KEY,
    name VARCHAR NOT NULL,
    price BIGINT
);
CREATE TABLE orders(
    id BIGINT PRIMARY KEY,
    user_id BIGINT NOT NULL references users(id),
    date TIMESTAMP NOT NULL
);
CREATE TABLE order_positions
(
    id            BIGINT PRIMARY KEY,
    product_id    BIGINT NOT NULL references products(id),
    amount        INTEGER NOT NULL,
    product_price BIGINT NOT NULL,
    order_id      BIGINT NOT NULL references orders(id)
);
