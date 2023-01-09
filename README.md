Control de Stock
===
Abstract: Aplicación usando swing, mysql y jdbc, mostrando un formulario simple realizando un crud en la db mysql, proyecto realizado en el curso de Alura Latam ->
Java y JDBC: Trabajando con una Base de Datos 

## Papar Information
- Title:  `Control de Stock`
- Authors:  `jocnn`

## Install & Dependence
- java
- mysql
- jdbc
- c3p0
- maven

## Directory Hierarchy
```
|—— .gitignore
|—— pom.xml
|—— src
|    |—— main
|        |—— java
|            |—— com
|                |—— jocnn
|                    |—— jdbc
|                        |—— controller
|                            |—— CategoriaController.java
|                            |—— ProductoController.java
|                        |—— dao
|                            |—— CategoriaDAO.java
|                            |—— ProductoDAO.java
|                        |—— main
|                            |—— ControlDeStockMain.java
|                        |—— modelo
|                            |—— Categoria.java
|                            |—— Producto.java
|                        |—— test
|                            |—— PruebaConexion.java
|                            |—— PruebaDelete.java
|                            |—— PruebaPoolConexiones.java
|                        |—— view
|                            |—— ControlDeStockFrame.java
|                            |—— ReporteFrame.java
|            |—— con
|                |—— jocnn
|                    |—— jdbc
|                        |—— factory
|                            |—— ConnectionFactory.java
|        |—— resources
|            |—— .gitkeep
|    |—— test
|        |—— java
|            |—— .gitkeep
|        |—— resources
|            |—— .gitkeep
|—— target
|    |—— classes
|        |—— .gitkeep
|        |—— com
|            |—— jocnn
|                |—— jdbc
|                    |—— controller
|                        |—— CategoriaController.class
|                        |—— ProductoController.class
|                    |—— dao
|                        |—— CategoriaDAO.class
|                        |—— ProductoDAO.class
|                    |—— main
|                        |—— ControlDeStockMain.class
|                    |—— modelo
|                        |—— Categoria.class
|                        |—— Producto.class
|                    |—— test
|                        |—— PruebaConexion.class
|                        |—— PruebaDelete.class
|                        |—— PruebaPoolConexiones.class
|                    |—— view
|                        |—— ControlDeStockFrame$1.class
|                        |—— ControlDeStockFrame$2.class
|                        |—— ControlDeStockFrame$3.class
|                        |—— ControlDeStockFrame$4.class
|                        |—— ControlDeStockFrame$5.class
|                        |—— ControlDeStockFrame.class
|                        |—— ReporteFrame.class
|        |—— con
|            |—— jocnn
|                |—— jdbc
|                    |—— factory
|                        |—— ConnectionFactory.class
|    |—— test-classes
|        |—— .gitkeep
```
## Code Details
### Tested Platform
- software
  ```
  OS: Archlinux x64
  Java: 11
  Mysql: 5.7.40
  ```
- hardware
  ```
  CPU: Intel i3 o superior
  GPU: integrada o independiente
  ```
## SQL QUERYS
```
create database control_de_stock;

use control_de_stock;

create table producto(id int not null auto_increment, 
nombre varchar(50) not null, 
descripcion varchar(255) default null, 
cantidad int(11) not null default 0, 
categoria_id int(11) default null, 
primary key(id))Engine=InnoDB;

create table Categoria(id int auto_increment, 
nombre varchar(50), 
primary key(id))Engine=InnoDB;

insert into categoria(nombre) values('muebles'),('tecnologia'),('cocina'),('calzado');

alter table producto add column categoria_id int;
alter table producto add foreign key (categoria_id) references categoria(id);

select * from producto;
select * from categoria;
```
