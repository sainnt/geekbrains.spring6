INSERT INTO users
VALUES (1, 'Ivan'),
       (2, 'Dmitriy'),
       (3, 'Mikhail');

INSERT INTO products
VALUES (1, 'Baikal', 60),
       (2, 'Kvass', 70),
       (3, 'Tarragon', 100);

INSERT INTO orders
VALUES (1, 1, now()),
       (2, 3, now());

INSERT INTO order_positions(id, product_id, amount, product_price, order_id)
VALUES (1, 3, 2, 50, 1),
       (3,3,5,100,1),
       (2,1,10,60,2);
