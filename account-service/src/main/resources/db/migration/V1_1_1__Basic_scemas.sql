create schema if not exists account_service;

create table account_service.accounts
(
    id uuid primary key,
    bank_id uuid,
    last_name varchar,
    first_name varchar,
    middle_name varchar,
    birth_date date,
    passport varchar unique,
    place_birth varchar,
    phone varchar,
    email varchar,
    address_registered varchar,
    address_life varchar
);