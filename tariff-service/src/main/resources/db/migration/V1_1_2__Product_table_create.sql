create table tariff_service.product
(
    id        uuid primary key,
    tariff_id uuid not null references tariff_service.tariff (id),
    version   bigint
);