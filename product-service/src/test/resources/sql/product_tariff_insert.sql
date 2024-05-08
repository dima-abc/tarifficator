insert into product_service.tariff(id, version)
values ('ab008c85-e2e4-4bf7-894f-c60b99926ae2', 1),
       ('13e1895d-2f1d-4766-a2a4-9af3adadfb38', 1),
       ('0aab4e94-5564-4ce4-8581-e6d956016844', 1);
insert into product_service.product(id, name, type_product, start_date, end_date, description, tariff_id, author_id,
                                    version)
values ('eef26128-a527-4ae9-b08b-ef2207b08261', 'Product name1', 1, '2022-12-01', '2024-11-01', 'Product1 detail',
        'ab008c85-e2e4-4bf7-894f-c60b99926ae2', '52f54cee-e2fd-4e49-8b75-f00cb690032c', 0),
       ('b5711e0a-5aed-4df8-bbd2-5625e05a9799', 'Product name2', 2, '2020-10-11', '2025-11-01', 'Product2 detail',
        '13e1895d-2f1d-4766-a2a4-9af3adadfb38', '52f54cee-e2fd-4e49-8b75-f00cb690032c', 1),
       ('761d98d6-ff53-4642-8727-15637347af37', 'Product name3', 1, '2021-02-21', '2026-11-01', 'Product3 detail',
        '0aab4e94-5564-4ce4-8581-e6d956016844', '52f54cee-e2fd-4e49-8b75-f00cb690032c', 2);