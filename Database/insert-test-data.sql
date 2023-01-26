USE [CafeSanchez]
GO

DELETE FROM Orderlines;
DELETE FROM Orders;

insert into Orders (Date, Status, CustomerName) values ('2022-12-02 18:43:36', 'Active', 'Ethan');
insert into Orders (Date, Status, CustomerName) values ('2022-12-08 23:26:55', 'Active', 'Euphemia');
insert into Orders (Date, Status, CustomerName) values ('2022-12-02 16:54:20', 'Active', 'Randal');
insert into Orders (Date, Status, CustomerName) values ('2022-12-11 00:47:24', 'Active', 'Hyacinth');
insert into Orders (Date, Status, CustomerName) values ('2022-12-15 14:24:57', 'Active', 'Juanita');
insert into Orders (Date, Status, CustomerName) values ('2022-12-08 10:26:34', 'Active', 'Ursula');
insert into Orders (Date, Status, CustomerName) values ('2022-12-14 21:33:13', 'Active', 'Giselbert');
insert into Orders (Date, Status, CustomerName) values ('2022-12-15 13:59:34', 'Active', 'Shannen');
insert into Orders (Date, Status, CustomerName) values ('2022-12-02 02:08:28', 'Active', 'Brittni');
insert into Orders (Date, Status, CustomerName) values ('2022-12-08 15:55:38', 'Active', 'Dael');


insert into Orderlines (OrderCustomerName, ProductName, Quantity) values ('Ethan', 'Americano', 4);
insert into Orderlines (OrderCustomerName, ProductName, Quantity) values ('Ethan', 'Misto', 4);
insert into Orderlines (OrderCustomerName, ProductName, Quantity) values ('Euphemia', 'Americano', 2);
insert into Orderlines (OrderCustomerName, ProductName, Quantity) values ('Euphemia', 'Dark Roast Coffee', 3);
insert into Orderlines (OrderCustomerName, ProductName, Quantity) values ('Euphemia', 'Misto', 3);
insert into Orderlines (OrderCustomerName, ProductName, Quantity) values ('Randal', 'Cappuccino', 4);
insert into Orderlines (OrderCustomerName, ProductName, Quantity) values ('Randal', 'Espresso', 3);
insert into Orderlines (OrderCustomerName, ProductName, Quantity) values ('Randal', 'Latte', 1);
insert into Orderlines (OrderCustomerName, ProductName, Quantity) values ('Hyacinth', 'Americano', 4);
insert into Orderlines (OrderCustomerName, ProductName, Quantity) values ('Hyacinth', 'Cappuccino', 2);
insert into Orderlines (OrderCustomerName, ProductName, Quantity) values ('Hyacinth', 'Espresso', 1);
insert into Orderlines (OrderCustomerName, ProductName, Quantity) values ('Hyacinth', 'Cinnamon Dolce Latte', 1);
insert into Orderlines (OrderCustomerName, ProductName, Quantity) values ('Juanita', 'Americano', 4);
insert into Orderlines (OrderCustomerName, ProductName, Quantity) values ('Juanita', 'Dark Roast Coffee', 3);
insert into Orderlines (OrderCustomerName, ProductName, Quantity) values ('Juanita', 'Misto', 2);
insert into Orderlines (OrderCustomerName, ProductName, Quantity) values ('Ursula', 'Americano', 2);
insert into Orderlines (OrderCustomerName, ProductName, Quantity) values ('Ursula', 'Misto', 1);
insert into Orderlines (OrderCustomerName, ProductName, Quantity) values ('Ursula', 'Cappuccino', 1);
insert into Orderlines (OrderCustomerName, ProductName, Quantity) values ('Ursula', 'Flat White', 4);
insert into Orderlines (OrderCustomerName, ProductName, Quantity) values ('Giselbert', 'Americano', 3);
insert into Orderlines (OrderCustomerName, ProductName, Quantity) values ('Shannen', 'Americano', 3);
insert into Orderlines (OrderCustomerName, ProductName, Quantity) values ('Shannen', 'Dark Roast Coffee', 2);
insert into Orderlines (OrderCustomerName, ProductName, Quantity) values ('Shannen', 'Caramel Macchiato', 2);
insert into Orderlines (OrderCustomerName, ProductName, Quantity) values ('Brittni', 'Misto', 1);
insert into Orderlines (OrderCustomerName, ProductName, Quantity) values ('Brittni', 'Cappuccino', 4);
insert into Orderlines (OrderCustomerName, ProductName, Quantity) values ('Brittni', 'Flat White', 1);
insert into Orderlines (OrderCustomerName, ProductName, Quantity) values ('Brittni', 'Caramel Macchiato', 2);
insert into Orderlines (OrderCustomerName, ProductName, Quantity) values ('Dael', 'Dark Roast Coffee', 2);
insert into Orderlines (OrderCustomerName, ProductName, Quantity) values ('Dael', 'Cappuccino', 3);
insert into Orderlines (OrderCustomerName, ProductName, Quantity) values ('Dael', 'Espresso', 3);
insert into Orderlines (OrderCustomerName, ProductName, Quantity) values ('Dael', 'Latte', 4);
