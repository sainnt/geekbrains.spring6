BEGIN TRANSACTION ;
INSERT INTO users(name)
VALUES ( 'Ivan'),
       ('Dmitriy'),
       ('Mikhail');

INSERT INTO products(name, price)
VALUES ('Baikal', 60),
       ( 'Kvass', 70),
       ( 'Tarragon', 100);

INSERT INTO orders(user_id,date)
VALUES (1, now()),
       (3, now());

INSERT INTO order_positions( product_id, amount, product_price, order_id)
VALUES (3, 2, 50, 1),
       (3,5,100,1),
       (1,10,60,2);
COMMIT TRANSACTION ;
