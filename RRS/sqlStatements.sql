drop database rrs;
create database rrs;
use rrs;

create table passenger(
    pid int auto_increment,
    email varchar(50) not null,
    pwd varchar(20) not null,
    name varchar(30) not null,
    gender varchar(1) not null,
    city varchar(20) not null,
    contact varchar(10) not null,
    unique(email),
    primary key(pid)
);

create table train(
    tid int,
    name varchar(20) not null,
    source varchar(30) not null,
    destination varchar(30) not null,
    max_seats int not null,
    duration int not null,
    primary key(tid)
);

create table ticket(
    ticno int auto_increment,
    pid int not null,
    tid int not null,
    dot varchar(15) not null,
    ano int not null,
    cno int not null,
    ticstatus varchar(15),
    primary key(ticno),
    foreign key(pid) references passenger(pid) ON DELETE CASCADE,
    foreign key(tid) references train(tid) ON DELETE CASCADE
);

insert into passenger(email, pwd, name, gender, city, contact) values("nithi@gmail.com", "1234", "nithish", "m", "kpm", 1234567890);
insert into passenger(email, pwd, name, gender, city, contact) values("admin@gmail.com", "admin", "Admin", "f", "che", 7894561230);

insert into train(tid, name, source, destination, max_seats, duration) values(1, "train1", "Chennai", "Madurai", 78, 1);
insert into train(tid, name, source, destination, max_seats, duration) values(2, "train2", "Chennai", "Madurai", 89, 1);

insert into ticket(pid, tid, dot, ano, cno, ticstatus) values(1, 1, "2022-05-26", 2, 1, "Confirmed");
insert into ticket(pid, tid, dot, ano, cno, ticstatus) values(1, 2, "2022-05-31", 3, 2, "Waiting");