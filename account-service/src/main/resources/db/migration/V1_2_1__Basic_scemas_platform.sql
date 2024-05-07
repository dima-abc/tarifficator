create table account_service.platform
(
    id bigserial primary key,
    platform_name varchar(50) not null unique,
    bank_id boolean default false,
    last_name boolean default false,
    first_name boolean default false,
    middle_name boolean default false,
    birth_date boolean default false,
    passport boolean default false,
    place_birth boolean default false,
    phone boolean default false,
    email boolean default false,
    address_registered boolean default false,
    address_life boolean default false
);