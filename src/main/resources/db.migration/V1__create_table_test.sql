use mercadinho;

CREATE TABLE customer(
	id int auto_increment primary key,
    name varchar(255) not null,
    email varchar(255) not null unique
);

CREATE TABLE book(
	id int auto_increment primary key,
    name varchar(255) not null,
    price decimal(10,2) not null unique,
    status varchar(255) not null,
    customer_id int not null,
    foreign key (customer_id) references customer(id)
);

CREATE TABLE purchase(
	id int auto_increment primary key,
    customer_id int not null,
    nfe varchar(255),
    price decimal(10,2) not null,
    created_at DATETIME not null,
    foreign key (customer_id) references customer(id)
);

CREATE TABLE purchase_book(
	purchase_id int not null,
    book_id int not null,
    foreign key (purchase_id) references purchase(id),
    foreign key (book_id) references book(id),
    primary key (purchase_id,book_id)
);

CREATE TABLE customer_role(
	customer_id int auto_increment not null,
    role varchar(50) not null,
    foreign key (customer_id) references customer(id)
);

alter table customer add column password varchar(255);

select * from purchase_book;
select * from purchase;
select * from customer;
select * from customer_roles;

insert into customer_roles(customer_id, role) values (11, 'ADMIN');