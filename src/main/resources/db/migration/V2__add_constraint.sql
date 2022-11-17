use stockapp;

alter table user modify column password varchar(250);

ALTER TABLE user ADD UNIQUE (password);
