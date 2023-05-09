insert into springproject.users (id, first_name, last_name, user_name, password, email)
    values (1, 'Igor', 'Popovich', 'IGPO', '123qwerty', 'igor123@gmail.com');
insert into springproject.users (id, first_name, last_name, user_name, password, email)
    values (2, 'Andrey', 'Andreevich', 'Andrew', 'asdf123', 'andrew569@gmail.com');
insert into springproject.users (id, first_name, last_name, user_name, password, email)
    values (3, 'Oleg', 'Olegovich', 'Oleg777', 'asdfghj', 'oleg777@gmail.com');
insert into springproject.users (id, first_name, last_name, user_name, password, email)
    values (4, 'Petya', 'Petrov', 'Petya123', 'zxcvbn', 'petya123@gmail.com');

insert into springproject.files (id, name, file_path)
    values (1, 'FileOne', 'FilePathOne');
insert into springproject.files (id, name, file_path)
    values (2, 'FileTwo', 'FilePathTwo');
insert into springproject.files (id, name, file_path)
    values (3, 'FileThree', 'FilePathThree');
insert into springproject.files (id, name, file_path)
    values (4, 'FileFour', 'FilePathFour');

insert into springproject.events (id, event_status, user_id, file_id)
    values (1, 'CREATED', 1, 1);
insert into springproject.events (id, event_status, user_id, file_id)
    values (2, 'CREATED', 2, 2);
insert into springproject.events (id, event_status, user_id, file_id)
    values (3, 'CREATED', 3, 3);
insert into springproject.events (id, event_status, user_id, file_id)
    values (4, 'CREATED', 4, 4);

insert into springproject.user_roles (user_id, role_id)
    values (1, 1);
insert into springproject.user_roles (user_id, role_id)
    values (2, 1);
insert into springproject.user_roles (user_id, role_id)
    values (3, 2);
insert into springproject.user_roles (user_id, role_id)
    values (4, 3);