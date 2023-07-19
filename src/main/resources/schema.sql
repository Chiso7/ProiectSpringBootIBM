create table shelter
(
    id      integer auto_increment,
    name    varchar(50),
    address varchar(100),
    primary key (id)
);

create table animal
(
    id         integer auto_increment,
    name       varchar(50),
    animal     varchar(50),
    breed      varchar(50),
    age        integer,
    id_shelter integer,
    primary key (id),
    foreign key(id_shelter) references shelter(id)
);