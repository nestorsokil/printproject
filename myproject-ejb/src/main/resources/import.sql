--password=user
insert into app_user(id, password, username, role) values (1, '7hHLsZBS5AsHqsDKBgwj7g==', 'Adam', 'USER');
insert into app_user(id, password, username, role) values (2, '7hHLsZBS5AsHqsDKBgwj7g==', 'Eva', 'USER');
--password=admin
insert into app_user(id, password, username, role) values (3, 'ISMvKXpXpadDiUoOSoAfww==', 'admin', 'ADMIN');

insert into storage(id, path) values ('storage_user', 'C:\\storage\\user');
insert into storage(id, path) values ('storage_temp', 'C:\\storage\\temp');