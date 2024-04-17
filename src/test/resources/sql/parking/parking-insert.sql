insert into users (id, username, password, role) values (100,'admin@email.com', '$2a$12$CxCmQK2hDUunCvUTFylXJulJ2EpMP9U.o.FTOaG0rpLEN.V.Zqa5u', 'ROLE_ADMIN');
insert into users (id, username, password, role) values (101, 'nome1@email.com', '$2a$12$CxCmQK2hDUunCvUTFylXJulJ2EpMP9U.o.FTOaG0rpLEN.V.Zqa5u', 'ROLE_CLIENT');
insert into users (id, username, password, role) values (102, 'nome2@email.com', '$2a$12$CxCmQK2hDUunCvUTFylXJulJ2EpMP9U.o.FTOaG0rpLEN.V.Zqa5u', 'ROLE_CLIENT');
insert into users (id, username, password, role) values (103, 'toby@email.com', '$2a$12$CxCmQK2hDUunCvUTFylXJulJ2EpMP9U.o.FTOaG0rpLEN.V.Zqa5u', 'ROLE_CLIENT');

insert into clients (id, name, cpf, id_user) values (10, 'Barbara Mayers', '79714506050', 101);
insert into clients (id, name, cpf, id_user) values (11, 'Evelyn Rios', '04084865036', 102);
insert into clients (id, name, cpf, id_user) values (12, 'Toby Faria', '39506397805', 103);

insert into spots (id, code, status) values (100, 'A-01', 'FREE');
insert into spots (id, code, status) values (101, 'A-02', 'FREE');
insert into spots (id, code, status) values (102, 'A-03', 'FREE');

insert into clients_spots (id, receipt_number, plate_number, brand, model, color, entry_time, client_id, spot_id) values (100, '20240416-110201', 'ABC-1234', 'Fiat', 'Uno', 'Red', '2024-04-16 11:02:01', 10, 100);
insert into clients_spots (id, receipt_number, plate_number, brand, model, color, entry_time, exit_time, client_id, spot_id) values (101, '20240416-110202', 'ABC-1234', 'Fiat', 'Palio', 'Black', '2024-04-16 11:02:01', '2024-04-16 11:03:01', 11, 101);
insert into clients_spots (id, receipt_number, plate_number, brand, model, color, entry_time, client_id, spot_id) values (102, '20240417-102302', 'ABC-1234', 'Fiat', 'Uno', 'Red', '2024-04-17 10:45:02', 12, 102);