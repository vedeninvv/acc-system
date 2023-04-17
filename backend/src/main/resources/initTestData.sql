insert into appuser (id, date_user_expired, middle_name, name, password, role, surname, username)
values (2, '2023-06-05', 'Витальевич', 'Вадим', '$2a$10$iq2rLNn9uZCKQ0xyqZHXHOmL5n9o1wFelKdJF4fLfIAk3f7x1halW', 'USER',
        'Веденин', 'vvedenin');

insert into contract (id, contract_type, fact_end_date, fact_start_date, plan_end_date, plan_start_date, sum, title,
                      assigned_user_id)
values (1, 'SUPPLY', '2023-06-12', '2023-06-10', '2023-06-01', '2023-05-28', 1000.10, 'Первый контракт', 1),
       (2, 'PURCHASE', '2023-06-10', '2023-06-08', '2023-06-03', '2023-05-30', 1000, 'Второй контракт', 1),
       (3, 'WORKS', '2023-06-24', '2023-06-20', '2023-06-10', '2023-06-08', 9999999.99, 'Очень дорогой контракт', 1),
       (4, 'SUPPLY', '2023-06-17', '2023-06-15', '2023-06-6', '2023-06-01', 1000.10, 'Контракт обычного пользователя',
        2),
       (5, 'SUPPLY', null, null, null, null, 1000.10, 'Контракт без дат', 1);

insert into contract_stage (id, fact_end_date, fact_start_date, fact_total_expenses, plan_end_date, plan_start_date,
                            plan_total_expenses, sum, title, contract_id)
values (1, '2023-06-12', '2023-06-10', 500.50, '2023-06-01', '2023-05-28', 400.44, 350.50,
        'Первый этап первого контракта', 1),
       (2, '2023-06-13', '2023-06-11', 540.55, '2023-06-02', '2023-05-29', 220.20, 500.10,
        'Второй этап первого контракта', 1),
       (3, '2023-06-14', '2023-06-12', 120.10, '2023-06-03', '2023-06-01', 1000, 1000.10,
        'Первый этап третьего контракта', 3),
       (4, '2023-06-15', '2023-06-13', 343.50, '2023-06-04', '2023-06-02', 1200, 999.99,
        'Второй этап третьего контракта', 3),
       (5, '2023-06-16', '2023-06-14', 500, '2023-06-05', '2023-06-03', 9000, 250, 'Третий этап третьего контракта', 3),
       (6, '2023-06-17', '2023-06-15', 428, '2023-06-06', '2023-06-04', 1111.11, 5000,
        'Первый этап четвертого контракта', 4),
       (7, '2023-06-18', '2023-06-16', 54879, '2023-06-07', '2023-06-05', 400.10, 12000,
        'Второй этап четвертого контракта', 4),
       (8, '2023-06-19', '2023-06-17', 90000.99, '2023-06-08', '2023-06-06', 255.55, 13000,
        'Первый этап пятого контракта', 5),
       (9, '2023-06-20', '2023-06-18', 0.1, '2023-06-09', '2023-06-07', 400, 450.05, 'Второй этап пятого контракта', 5),
       (10, '2023-06-21', '2023-06-19', 100.10, '2023-06-10', '2023-06-08', 600.12, 0.2,
        'Четвертый этап третьего контракта', 3),
       (11, '2023-06-22', '2023-06-20', 200.20, '2023-06-11', '2023-06-09', 555.55, 10000,
        'Третий этап первого контракта', 1),
       (12, '2023-06-23', '2023-06-21', 300.33, '2023-06-12', '2023-06-10', 100000, 252,
        'Четвертый этап третьего контракта', 3);

insert into expense (id, fact_amount, plan_amount, title, contract_stage_id)
values (1, 100, 100, 'Пункт расхода №1', 1),
       (2, 400.50, 300.44, 'Пункт расхода №2', 1),
       (3, 540.55, 220.20, 'Пункт расхода №1', 2),
       (4, 120.10, 1000, 'Расход 1', 3),
       (5, 320.50, 1000, 'Пункт расхода №1', 4),
       (6, 23, 200, 'Пункт расхода №2', 4),
       (7, 500, 9000, 'Зарплата', 5),
       (8, 428, 1111.11, 'Зарплата', 6),
       (9, 50000, 400, 'Большой расход', 7),
       (10, 4879, 0.10, 'Маленький расход', 7),
       (11, 30000.33, 200, 'Пункт расхода №1', 8),
       (12, 30000.33, 20, 'Пункт расхода №2', 8),
       (13, 30000.33, 30.55, 'Пункт расхода №3', 8),
       (14, 0.1, 400, 'Один расход', 9),
       (15, 100.10, 600.12, 'Один расход', 10),
       (16, 200.20, 555.55, 'Один расход', 11),
       (17, 300.33, 100000, 'Один выгодный расход', 12);

insert into counterparty (id, inn, address, title)
values (1, '123456789', 'г. Санкт-Петербург, Комендантский проспект, 100', 'ПАО "Газпром"'),
       (2, '432198765', 'г. Санкт-Петербург, ул. Ленина, 5', 'ЗАО "Рога и копыта"'),
       (3, '987654321', 'г. Санкт-Петербург, Невский проспект, 10', 'ОАО "РЖД"');

insert into counterparty_contract (id, contract_type, fact_end_date, fact_start_date, plan_end_date, plan_start_date,
                                   sum, title, contract_id, counterparty_id)
values (1, 'SUPPLY', '2023-06-10', '2023-06-01', '2023-06-15', '2023-06-03', 100.50, 'Первый Контракт с контрагентом 1',
        1, 1),
       (2, 'SUPPLY', '2023-06-11', '2023-06-02', '2023-06-16', '2023-06-04', 100.50, 'Первый Контракт с контрагентом 2',
        1, 2),
       (3, 'SUPPLY', '2023-06-12', '2023-06-03', '2023-06-17', '2023-06-05', 100.50, 'Второй Контракт с контрагентом 1',
        1, 1),
       (4, 'SUPPLY', '2023-06-13', '2023-06-04', '2023-06-18', '2023-06-06', 100.50, 'Первый Контракт с контрагентом 3',
        2, 3),
       (5, 'SUPPLY', '2023-06-14', '2023-06-05', '2023-06-19', '2023-06-07', 100.50, 'Второй Контракт с контрагентом 3',
        2, 3),
       (6, 'SUPPLY', '2023-06-15', '2023-06-06', '2023-06-20', '2023-06-08', 100.50, 'Первый Контракт с контрагентом 1',
        3, 1),
       (7, 'SUPPLY', '2023-06-16', '2023-06-07', '2023-06-21', '2023-06-09', 100.50, 'Первый Контракт с контрагентом 2',
        4, 2),
       (8, 'SUPPLY', '2023-06-17', '2023-06-08', '2023-06-22', '2023-06-10', 100.50, 'Первый Контракт с контрагентом 3',
        4, 3),
       (9, 'SUPPLY', '2023-06-18', '2023-06-09', '2023-06-23', '2023-06-11', 100.50, 'Первый Контракт с контрагентом 1',
        5, 1),
       (10, 'SUPPLY', '2023-06-19', '2023-06-10', '2023-06-24', '2023-06-12', 100.50,
        'Первый Контракт с контрагентом 2', 5, 2),
       (11, 'SUPPLY', '2023-06-20', '2023-06-11', '2023-06-25', '2023-06-13', 100.50,
        'Первый Контракт с контрагентом 3', 5, 3),
       (12, 'SUPPLY', '2023-06-21', '2023-06-12', '2023-06-26', '2023-06-14', 100.50,
        'Второй Контракт с контрагентом 1', 5, 1);