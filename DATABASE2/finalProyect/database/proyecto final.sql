create database toDoList;
use toDoList;

create table categoria(
	categoriaID int,
    nombre varchar(100),
    primary key (categoriaID)
);

create table usuario(
	usuarioID int,
    nombre varchar(100),
    contrasena varchar(100),
    primary key (usuarioID)
);

create table tarea(
	tareaID int,
    nombre varchar(100),
    descripcion text,
    fechaDeInicio date,
    fechaDeFinalizacion date,
    estado boolean,
    categoriaID int,
    usuarioID int,
    primary key (tareaID),
    foreign key (categoriaID) references categoria(categoriaID),
    foreign key (usuarioID) references usuario(usuarioID)
);

