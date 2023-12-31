create table if not exists Post(
        id int not null primary key AUTO_INCREMENT,
        title VARCHAR(255),
        body VARCHAR(1000),
        state varchar(20)
);

create table if not exists Comment(
        id int not null primary key AUTO_INCREMENT,
        post_id int,
        name varchar(500),
        email varchar(500),
        body varchar(1000),
        foreign key (post_id) references post(id)
);

create table if not exists History(
        id int not null primary key auto_increment,
        post_id int,
        state varchar(20),
        timestamp TIMESTAMP,
        foreign key (post_id) references post(id)
);