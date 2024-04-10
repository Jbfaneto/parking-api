insert into users (id, username, password, role) values (100,'admin@email.com', '$2a$12$CxCmQK2hDUunCvUTFylXJulJ2EpMP9U.o.FTOaG0rpLEN.V.Zqa5u', 'ROLE_ADMIN');
insert into users (id, username, password, role) values (101, 'nome1@email.com', '$2a$12$CxCmQK2hDUunCvUTFylXJulJ2EpMP9U.o.FTOaG0rpLEN.V.Zqa5u', 'ROLE_ADMIN');
insert into users (id, username, password, role) values (102, 'nome2@email.com', '$2a$12$CxCmQK2hDUunCvUTFylXJulJ2EpMP9U.o.FTOaG0rpLEN.V.Zqa5u', 'ROLE_CLIENT');

insert into spots (id, code, status) values (100, 'A-01', 'FREE');
insert into spots (id, code, status) values (101, 'A-02', 'FREE');
insert into spots (id, code, status) values (102, 'A-03', 'OCCUPIED');