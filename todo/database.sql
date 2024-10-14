use test_java;

drop table users;

create table users (
    id int auto_increment,
    username varchar(100) unique not null,
    password varchar(100) not null,
    token varchar(100),
    primary key (id)
) engine = InnoDB;

select
    *
from
    users;

-- insert into
--     users (username, password, token)
-- values
--     ("Dody", "123123", null),
--     ("John", "123123", null),
--     ("Alice", "123123", null);
select
    *
from
    users;

drop table tasks;

create table tasks (
    id int not null auto_increment,
    title varchar(100) not null,
    description varchar(100) not null,
    userId int not null,
    status boolean not null,
    taskStart datetime not null,
    taskEnd datetime not null,
    primary key (id),
    foreign key (userId) references users (id)
) engine = InnoDB;

desc tasks;

select
    *
from
    tasks;

-- insert into
--     tasks (title, description, userId, taskStart, taskEnd)
-- values
--     (
--         "Buy a book",
--         "going to market and buy the book",
--         1,
--         "2024-10-09 14:30:45.123456",
--         "2024-11-09 14:30:45.123456"
--     ),
--     (
--         "Buy a pen",
--         "going to market and buy the pen",
--         1,
--         "2024-10-09 14:30:45.123456",
--         "2024-11-09 14:30:45.123456"
--     );
