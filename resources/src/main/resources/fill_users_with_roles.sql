USE registrator_db;

INSERT INTO roles (`description`,`type`) VALUES("have admin access","ADMIN" );
INSERT INTO roles (`description`,`type`) VALUES("have registrator access","REGISTRATOR" );
INSERT INTO roles (`description`,`type`) VALUES("have user access","USER" );

INSERT INTO users (`email`,`first_name`,`last_name`,`login`,`middle_name`,`password`,`status`,`role_id`) 
  VALUES("admin@gmail.com","Huan","Pedro","admin","Oliveira","admin","unblock",1);
INSERT INTO users (`email`,`first_name`,`last_name`,`login`,`middle_name`,`password`,`status`,`role_id`) 
  VALUES("registrator@gmail.com","Ivan","Shpulka","registrator","Petrovuch","registrator","unblock",2);
INSERT INTO users (`email`,`first_name`,`last_name`,`login`,`middle_name`,`password`,`status`,`role_id`) 
  VALUES("user@gmail.com","Hugo","Boss","user","Ivanovuch","user","unblock",3);
  
INSERT INTO address (`building`,`city`,`district`,`flat`, `postcode`,`region`,`street`,`user_id`) 
  VALUES ("one","Lviv","Lvivslka","21","78901","west","Bandera",1);
INSERT INTO address (`building`,`city`,`district`,`flat`, `postcode`,`region`,`street`,`user_id`) 
  VALUES ("two","Drohobuch","Lvivslka","44","90123","east","Ringo",2);
INSERT INTO address (`building`,`city`,`district`,`flat`, `postcode`,`region`,`street`,`user_id`) 
  VALUES ("three","Lviv","Lvivslka","88","77901","center","Bumer",3);

INSERT INTO passport_data(`number`,`published_by_data`,`seria`,`user_id`) VALUES(123456,"Lviv","KB",1);
INSERT INTO passport_data(`number`,`published_by_data`,`seria`,`user_id`) VALUES(654321,"Lviv","KC",2);
INSERT INTO passport_data(`number`,`published_by_data`,`seria`,`user_id`) VALUES(45612,"Lviv","KH",3);