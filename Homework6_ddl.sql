BEGIN TRANSACTION ;
CREATE TABLE users
(
    id   BIGSERIAL PRIMARY KEY ,
    name VARCHAR NOT NULL
);

CREATE TABLE products
(
    id   BIGSERIAL PRIMARY KEY ,
    name VARCHAR NOT NULL,
    price BIGINT
);
CREATE TABLE orders(
    id BIGSERIAL PRIMARY KEY ,
    user_id BIGSERIAL NOT NULL references users(id),
    date TIMESTAMP NOT NULL
);
CREATE TABLE order_positions
(
    id            BIGSERIAL PRIMARY KEY ,
    product_id    BIGSERIAL NOT NULL references products(id),
    amount        INTEGER NOT NULL,
    product_price BIGINT NOT NULL,
    order_id      BIGSERIAL references orders(id) ON DELETE CASCADE
);

COMMIT TRANSACTION ;
