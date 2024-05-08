insert into tariff_service.tariff(id, name, start_date, end_date, description, rate, version)
values ('ecbfd764-b3cf-4207-a86a-04ca643f7c88', 'Tariff name1', '2020-02-20', '2024-04-08', 'Tariff1 detail', 25.0, 1),
       ('df32bd16-82ad-48b4-952f-f561a947593c', 'Tariff name2', '2021-02-20', '2023-03-01', 'Tariff2 detail', 15.0, 2);

insert into tariff_service.product(id, tariff_id, version)
values ('73cf2057-c440-49b2-9a4f-94668d53cda0', 'ecbfd764-b3cf-4207-a86a-04ca643f7c88', 1),
       ('c94abdb8-4490-4e0f-a7e2-3659b78c336f', 'ecbfd764-b3cf-4207-a86a-04ca643f7c88', 2),
       ('26626214-89d9-4080-bf96-148fde927941', 'df32bd16-82ad-48b4-952f-f561a947593c', 3);