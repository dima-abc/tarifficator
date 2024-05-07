insert into account_service.platform(platform_name, bank_id, last_name, first_name,
                                     middle_name, birth_date, passport, place_birth,
                                     phone, email, address_registered, address_life)
values ('mail', false, false, true, false, false, false, false, false, true, false, false),
       ('mobile', false, false, false, false, false, false, false, true, false, false, false),
       ('bank', true, true, true, true, true, true, false, false, false, false, false),
       ('gosuslugi', true, true, true, true, true, true, true, true, false, true, false);