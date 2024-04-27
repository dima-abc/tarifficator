create schema if not exists product_service;

create table if not exists product_service.tariff
(
    id      uuid primary key,
    version bigint default 0
);

create table if not exists product_service.product
(
    id           uuid primary key,
    name         varchar(255) not null,
    type_product int CHECK (type_product > 0),
    start_date   date not null,
    end_date     date not null,
    description  varchar not null,
    tariff_id    uuid not null references product_service.tariff (id),
    author_id    uuid not null,
    version      bigint default 0
);