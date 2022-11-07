use stockapp;

create table rule
(
    id            int not null auto_increment,
    rule_no       varchar(50),
    profitability boolean,
    activity      boolean,
    liquidity     boolean,
    debt          boolean,
    market        boolean,
    classified    varchar(50),
    primary key (id)

);

create table stock
(
    id                int not null auto_increment,
    stock_symbol      varchar(50),
    net_profit        float,
    total_assets      float,
    revenue           float,
    current_assets    float,
    current_debt      float,
    total_liabilities float,
    stock_price       float,
    eps               float,
    created_date      timestamp,
    last_updated      timestamp,
    primary key (id)
);

create table user
(
    id           int not null auto_increment,
    cccd         varchar(50),
    first_name   varchar(50),
    last_name    varchar(50),
    email        varchar(50),
    username     varchar(50),
    password     varchar(50),
    created_date timestamp,
    last_updated timestamp,
    status       boolean,
    primary key (id)
);

create table user_stock
(
    id int not null auto_increment,
    user_id int,
    stock_id int,
    registered_at timestamp,
    primary key (id)
);

create table bought_user_stock
(
    id int not null auto_increment,
    user_id int,
    stock_id int,
    price float,
    bought_at timestamp,
    primary key (id)
);

insert into rule(rule_no, profitability, activity, liquidity, debt, market, classified) values('R1', FALSE, TRUE, TRUE, FALSE, FALSE, 'ATTRACTIVE');
insert into rule(rule_no, profitability, activity, liquidity, debt, market, classified) values('R2', TRUE, TRUE, FALSE, FALSE, FALSE, 'ATTRACTIVE');
insert into rule(rule_no, profitability, activity, liquidity, debt, market, classified) values('R3', TRUE, TRUE, TRUE, FALSE, FALSE, 'ATTRACTIVE');
insert into rule(rule_no, profitability, activity, liquidity, debt, market, classified) values('R4', TRUE, TRUE, TRUE, FALSE, TRUE, 'ATTRACTIVE');
insert into rule(rule_no, profitability, activity, liquidity, debt, market, classified) values('R5', TRUE, TRUE, TRUE, TRUE, TRUE, 'NON_ATTRACTIVE');
insert into rule(rule_no, profitability, activity, liquidity, debt, market, classified) values('R6', TRUE, TRUE, TRUE, TRUE, FALSE, 'NON_ATTRACTIVE');
insert into rule(rule_no, profitability, activity, liquidity, debt, market, classified) values('R7', TRUE, TRUE, FALSE, TRUE, TRUE, 'NON_ATTRACTIVE');
insert into rule(rule_no, profitability, activity, liquidity, debt, market, classified) values('R8', TRUE, TRUE, FALSE, TRUE, FALSE, 'NON_ATTRACTIVE');
insert into rule(rule_no, profitability, activity, liquidity, debt, market, classified) values('R9', TRUE, TRUE, FALSE, FALSE, TRUE, 'ATTRACTIVE');
insert into rule(rule_no, profitability, activity, liquidity, debt, market, classified) values('R10', TRUE, FALSE, TRUE, TRUE, TRUE, 'NON_ATTRACTIVE');
insert into rule(rule_no, profitability, activity, liquidity, debt, market, classified) values('R11', TRUE, FALSE, TRUE, TRUE, FALSE, 'NON_ATTRACTIVE');
insert into rule(rule_no, profitability, activity, liquidity, debt, market, classified) values('R12', TRUE, FALSE, TRUE, FALSE, TRUE, 'NON_ATTRACTIVE');
insert into rule(rule_no, profitability, activity, liquidity, debt, market, classified) values('R13', TRUE, FALSE, TRUE, FALSE, FALSE, 'NON_ATTRACTIVE');
insert into rule(rule_no, profitability, activity, liquidity, debt, market, classified) values('R14', TRUE, FALSE, FALSE, TRUE, TRUE, 'NON_ATTRACTIVE');
insert into rule(rule_no, profitability, activity, liquidity, debt, market, classified) values('R15', TRUE, FALSE, FALSE, TRUE, FALSE, 'NON_ATTRACTIVE');
insert into rule(rule_no, profitability, activity, liquidity, debt, market, classified) values('R16', TRUE, FALSE, FALSE, FALSE, FALSE, 'NON_ATTRACTIVE');
insert into rule(rule_no, profitability, activity, liquidity, debt, market, classified) values('R17', FALSE, TRUE, TRUE, TRUE, TRUE, 'NON_ATTRACTIVE');
insert into rule(rule_no, profitability, activity, liquidity, debt, market, classified) values('R18', FALSE, TRUE, TRUE, TRUE, FALSE, 'NON_ATTRACTIVE');
insert into rule(rule_no, profitability, activity, liquidity, debt, market, classified) values('R19', FALSE, TRUE, TRUE, FALSE, TRUE, 'ATTRACTIVE');
insert into rule(rule_no, profitability, activity, liquidity, debt, market, classified) values('R20', FALSE, TRUE, TRUE, FALSE, FALSE, 'NON_ATTRACTIVE');
insert into rule(rule_no, profitability, activity, liquidity, debt, market, classified) values('R21', FALSE, TRUE, TRUE, FALSE, TRUE, 'NON_ATTRACTIVE');
insert into rule(rule_no, profitability, activity, liquidity, debt, market, classified) values('R22', FALSE, TRUE, FALSE, TRUE, FALSE, 'NON_ATTRACTIVE');
insert into rule(rule_no, profitability, activity, liquidity, debt, market, classified) values('R23', FALSE, TRUE, FALSE, FALSE, FALSE, 'NON_ATTRACTIVE');
insert into rule(rule_no, profitability, activity, liquidity, debt, market, classified) values('R24', FALSE, FALSE, TRUE, TRUE, TRUE, 'NON_ATTRACTIVE');
insert into rule(rule_no, profitability, activity, liquidity, debt, market, classified) values('R25', FALSE, FALSE, TRUE, TRUE, FALSE, 'NON_ATTRACTIVE');
insert into rule(rule_no, profitability, activity, liquidity, debt, market, classified) values('R26', FALSE, FALSE, TRUE, FALSE, TRUE, 'NON_ATTRACTIVE');
insert into rule(rule_no, profitability, activity, liquidity, debt, market, classified) values('R27', FALSE, FALSE, TRUE, FALSE, FALSE, 'NON_ATTRACTIVE');
insert into rule(rule_no, profitability, activity, liquidity, debt, market, classified) values('R28', FALSE, FALSE, FALSE, TRUE, TRUE, 'NON_ATTRACTIVE');
insert into rule(rule_no, profitability, activity, liquidity, debt, market, classified) values('R29', FALSE, FALSE, FALSE, TRUE, FALSE, 'NON_ATTRACTIVE');
insert into rule(rule_no, profitability, activity, liquidity, debt, market, classified) values('R30', FALSE, FALSE, FALSE, FALSE, FALSE, 'NON_ATTRACTIVE');
insert into rule(rule_no, profitability, activity, liquidity, debt, market, classified) values('R31', FALSE, TRUE, FALSE, FALSE, TRUE, 'NON_ATTRACTIVE');
insert into rule(rule_no, profitability, activity, liquidity, debt, market, classified) values('R32', TRUE, FALSE, FALSE, FALSE, TRUE, 'NON_ATTRACTIVE');

insert into stock(stock_symbol, net_profit, total_assets, revenue, current_assets, current_debt, total_liabilities, stock_price, eps, created_date, last_updated) values('GIA', 319173, 3119253, 3940636, 2506223, 1677252, 1679852, 49670, 10870, '2022-11-06 00:00:01', '2022-11-06 00:00:01');
insert into stock(stock_symbol, net_profit, total_assets, revenue, current_assets, current_debt, total_liabilities, stock_price, eps, created_date, last_updated) values('GIB', 10657, 136218, 165913, 108792, 42157, 53204, 14500, 1570, '2022-11-06 00:00:01', '2022-11-06 00:00:01');
insert into stock(stock_symbol, net_profit, total_assets, revenue, current_assets, current_debt, total_liabilities, stock_price, eps, created_date, last_updated) values('GIC', 1797473, 30377146, 5922956, 1987947, 965115, 14988012, 57700, 5990, '2022-11-06 00:00:01', '2022-11-06 00:00:01');
insert into stock(stock_symbol, net_profit, total_assets, revenue, current_assets, current_debt, total_liabilities, stock_price, eps, created_date, last_updated) values('GID', 1795673, 35377146, 5922913, 2457947, 679890, 14988012, 15989, 22000, '2022-11-06 00:00:01', '2022-11-06 00:00:01');
insert into stock(stock_symbol, net_profit, total_assets, revenue, current_assets, current_debt, total_liabilities, stock_price, eps, created_date, last_updated) values('GIE', 3298988, 23868696, 8868777, 6587998, 234566, 18657565, 34454, 89797, '2022-11-06 00:00:01', '2022-11-06 00:00:01');