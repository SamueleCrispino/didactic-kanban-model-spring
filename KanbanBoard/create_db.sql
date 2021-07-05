create table colonna (id integer not null auto_increment, titolo varchar(255), primary key (id)) engine=InnoDB
create table login (id integer not null auto_increment, password varchar(255), username varchar(255), primary key (id)) engine=InnoDB
create table tile (id integer not null auto_increment, autore tinyblob, contenuto_multimediale longblob, contenuto_testuale varchar(255), titolo varchar(255), colonna_id integer, primary key (id)) engine=InnoDB
create table user (id integer not null auto_increment, email varchar(255), timestamp_creazione datetime, timestamp_login datetime, username varchar(255), primary key (id)) engine=InnoDB
alter table tile add constraint FKq53nr6xu16lf3uwjrurvm4gmw foreign key (colonna_id) references colonna (id)
create table colonna (id integer not null auto_increment, titolo varchar(255), primary key (id)) engine=InnoDB
create table login (id integer not null auto_increment, password varchar(255), username varchar(255), primary key (id)) engine=InnoDB
create table tile (id integer not null auto_increment, autore tinyblob, contenuto_multimediale longblob, contenuto_testuale varchar(255), titolo varchar(255), colonna_id integer, primary key (id)) engine=InnoDB
create table user (id integer not null auto_increment, email varchar(255), timestamp_creazione datetime, timestamp_login datetime, username varchar(255), primary key (id)) engine=InnoDB
alter table tile add constraint FKq53nr6xu16lf3uwjrurvm4gmw foreign key (colonna_id) references colonna (id)
create table colonna (id integer not null auto_increment, titolo varchar(255), primary key (id)) engine=InnoDB
create table login (id integer not null auto_increment, password varchar(255), username varchar(255), primary key (id)) engine=InnoDB
create table tile (id integer not null auto_increment, autore tinyblob, contenuto_multimediale longblob, contenuto_testuale varchar(255), titolo varchar(255), colonna_id integer, primary key (id)) engine=InnoDB
create table user (id integer not null auto_increment, email varchar(255), timestamp_creazione datetime, timestamp_login datetime, username varchar(255), primary key (id)) engine=InnoDB
alter table tile add constraint FKq53nr6xu16lf3uwjrurvm4gmw foreign key (colonna_id) references colonna (id)
