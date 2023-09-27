insert into service_user(id, username, email, password) values
(1, 'shop1admin', 'shop1.admin@example.com', '$2a$10$//9qerXAU3UcdIZcVG5NNeKd3ZEHU/O0tuTJzcrxcX6T2uszo7i5.'),
(2, 'shop2admin', 'shop2.admin@example.com', '$2a$10$qI6eri1zdp6xKTgZhvBFZeVGjUtt4wTfryOSU5xz1Yq6WTa10zHyW'),
(3, 'shop3admin', 'shop3.admin@example.com', '$2a$10$mVFZIoa8OE5.T5DYW5NFROTG8gSAuPkzFstoz5f2voQlaKcRcPuai'),
(4, 'cust1ali', 'customer1@example.com', '$2a$10$mVFZIoa8OE5.T5DYW5NFROTG8gSAuPkzFstoz5f2voQlaKcRcPuai'),
(5, 'cust2bob', 'customer2@example.com', '$2a$10$mVFZIoa8OE5.T5DYW5NFROTG8gSAuPkzFstoz5f2voQlaKcRcPuai'),
(6, 'cust3cole', 'customer3@example.com', '$2a$10$mVFZIoa8OE5.T5DYW5NFROTG8gSAuPkzFstoz5f2voQlaKcRcPuai'),
(7, 'cust4dan', 'customer4@example.com', '$2a$10$mVFZIoa8OE5.T5DYW5NFROTG8gSAuPkzFstoz5f2voQlaKcRcPuai'),
(8, 'cust5emmy', 'customer5@example.com', '$2a$10$mVFZIoa8OE5.T5DYW5NFROTG8gSAuPkzFstoz5f2voQlaKcRcPuai')
;

insert into service_user_role(user_id, role_id) values
(1, 2), (2, 2), (3, 2),
(4, 3), (5, 3), (6, 3), (7, 3), (8, 3)
;

insert into shop(id, branch, address, opening_time, closing_time, contract) values
(1, 'First Branch', 'No.1 Abraham street', '08:00', '22:00', ''),
(2, 'Second Branch', 'No.2 Desmond street', '08:30', '22:30', ''),
(3, 'Third Branch', 'No.3 Luciano street', '07:00', '21:45', '')
;

insert into shop_admin(user_id, shop_id) values (1, 1), (2, 2), (3, 2);

insert into customer(id, name, phone, address, user_id) values
(1, 'Ali Baba', '+1223334444', 'address 1', 4),
(2, 'Bob Paisley', '+2003334444', 'address 2', 5),
(3, 'Cole Palmer', '+3551112222', 'address 3', 6),
(4, 'Dan Burn', '+4002228888', 'address 4', 7),
(5, 'Emmy Watts', '+1009996666', 'address 5', 8)
;

insert into dish(id, name, price, currency) values
(1, 'Flat White', 4, 'USD'),
(2, 'Americano', 3, 'USD'),
(3, 'Capuccino', 3, 'USD'),
(4, 'Latte', 2.5, 'USD'),
(5, 'Mocca', 5, 'USD')
;

insert into shop_dish(ref_shop_id, ref_dish_id) values
(1, 1), (1, 2), (1, 3), (1, 4), (1, 5),
(2, 1), (2, 2), (2, 4), (2, 5),
(3, 2), (1, 3), (1, 4), (1, 5)
;
insert into shop_dish(ref_shop_id, ref_dish_id, name, price, currency) values
(2, 3, 'Capuccino Hazelnut', 3.4, 'USD'),
(2, null, 'Macciato', 5, 'USD'),
(2, null, 'Matcha Tea', 4.8, 'USD'),
(3, 1, 'Flat White Light', 3.8, 'USD'),
(3, null, 'Vienna Coffee', 5, 'USD'),
(3, null, 'Irish Coffee', 4, 'USD'),
(3, null, 'Glaze', 4, 'USD')
;
insert into shop_queue(shop_id, name, max_quantity, current_index) values
(1, 'Shop1 Anne', 8, 0),
(2, 'Shop2 Bryan', 5, 0), (2, 'Shop2 Bukka', 5, 0),
(3, 'Shop3 Cyan', 4, 0), (3, 'Shop3 Christie', 4, 0), (3, 'Shop3 Callar', 4, 0)
;