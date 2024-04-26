create sequence revinfo_seq increment by 50;
---
create table tariff_service.revinfo
(
    rev      serial not null primary key,
    revtstmp bigint
);
---revtype 0-ADD, 1-MOD, 2-DEL
create table tariff_service.tariff_aud
(
    id          uuid   not null,
    rev         bigint not null,
    revtype     smallint,
    name        varchar(255),
    start_date  date,
    end_date    date,
    description varchar(255),
    rate        double precision,
    version     bigint,
    primary key (id, rev)
);