--- create revinfo_seq
create sequence revinfo_seq increment by 50;
--- create table revinfo
create table product_service.revinfo
(
    rev      serial primary key,
    revtstmp bigint
);
--- create table product_aud
create table product_service.product_aud
(
    id           uuid    not null,
    rev          integer not null references product_service.revinfo (rev),
    revtype      smallint,
    name         varchar(255),
    type_product integer,
    start_date   date,
    end_date     date,
    description  varchar,
    tariff_id    uuid,
    author_id    uuid,
    version      bigint,
    primary key (rev, id)
);
--- create table tariff_aud
create table product_service.tariff_aud
(
    id      uuid    not null,
    rev     integer not null references product_service.revinfo (rev),
    revtype smallint,
    version bigint,
    primary key (id, rev)
);