use stockapp;

alter table user drop index password;

alter table user add unique(username);
