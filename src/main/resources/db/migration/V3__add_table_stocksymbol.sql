use stockapp;

create table stock_ticket (
    id int primary key auto_increment,
    stock_name varchar(10),
    created_date timestamp,
    last_updated timestamp,
    unique (stock_name)
);

alter table user add column role varchar(20) AFTER password;

alter table stock add column stock_ticket_id int AFTER id;

alter table stock add foreign key (stock_ticket_id) references stock_ticket(id);

insert into stock_ticket(id, stock_name, created_date, last_updated)
values (1, 'GIA', '2022-11-06 00:00:01', '2022-11-06 00:00:01');

insert into stock_ticket(id, stock_name, created_date, last_updated)
values (2, 'GIB', '2022-11-06 00:00:01', '2022-11-06 00:00:01');

insert into stock_ticket(id, stock_name, created_date, last_updated)
values (3, 'GIC', '2022-11-06 00:00:01', '2022-11-06 00:00:01');

insert into stock_ticket(id, stock_name, created_date, last_updated)
values (4, 'GID', '2022-11-06 00:00:01', '2022-11-06 00:00:01');

insert into stock_ticket(id, stock_name, created_date, last_updated)
values (5, 'GIE', '2022-11-06 00:00:01', '2022-11-06 00:00:01');

update stock set stock_ticket_id = 1 where id = 1;
update stock set stock_ticket_id = 2 where id = 2;
update stock set stock_ticket_id = 3 where id = 3;
update stock set stock_ticket_id = 4 where id = 4;
update stock set stock_ticket_id = 5 where id = 5;

insert into user(id, cccd, first_name, last_name, email, username, password, role, created_date, last_updated, status)
values (99, '07264727887', 'admin', 'stock', 'admin@gmail.com', 'admin', '12345678', 'ROLE_ADMIN',  '2022-11-06 00:00:01', '2022-11-06 00:00:01', TRUE);