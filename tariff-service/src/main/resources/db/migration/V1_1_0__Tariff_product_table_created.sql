create schema if not exists tariff_service;

create table if not exists tariff_service.tariff
(
    id          uuid primary key,
    name        varchar,
    start_date  date,
    end_date    date,
    description text,
    rate        double precision default 0,
    version     bigint default 0
);