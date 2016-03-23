INSERT INTO `roles` (`role_id`,`description`,`type`) VALUES (1,'description','ADMIN');
INSERT INTO `roles` (`role_id`,`description`,`type`) VALUES (2,'description','REGISTRATOR');
INSERT INTO `roles` (`role_id`,`description`,`type`) VALUES (3,'description','USER');

INSERT INTO `users` (`user_id`,`email`,`first_name`,`last_name`,`login`,`middle_name`,`password`,`status`,`role_id`) VALUES (1,'oless.@gmail.com','Олександр','Архилюк','oleks','Олександрович','pass1','ACTIVE',1);
INSERT INTO `users` (`user_id`,`email`,`first_name`,`last_name`,`login`,`middle_name`,`password`,`status`,`role_id`) VALUES (2,'petro.@gmail.com','Петро','Петренко','petro','Петрович','pass2','ACTIVE',2);
INSERT INTO `users` (`user_id`,`email`,`first_name`,`last_name`,`login`,`middle_name`,`password`,`status`,`role_id`) VALUES (3,'ivan.@gmail.com','Юрій','Іванов','ivan','Іванович','pass3','ACTIVE',3);
INSERT INTO `users` (`user_id`,`email`,`first_name`,`last_name`,`login`,`middle_name`,`password`,`status`,`role_id`) VALUES (4,'vasyl.@gmail.com','Василь','Василюк','vasyl','Васильович','pass4','ACTIVE',2);
INSERT INTO `users` (`user_id`,`email`,`first_name`,`last_name`,`login`,`middle_name`,`password`,`status`,`role_id`) VALUES (5,'oleh.@gmail.com','Олег','Василюк','oleh','Олеговчич','pass5','INACTIVE',3);

INSERT INTO `address` (`address_id`,`building`,`city`,`district`,`flat`,`postcode`,`region`,`street`,`user_id`) VALUES (1,'35','Львів',NULL,NULL,'79026','Львівська','Пастернака',1);
INSERT INTO `address` (`address_id`,`building`,`city`,`district`,`flat`,`postcode`,`region`,`street`,`user_id`) VALUES (2,'17','Хмельницький',NULL,'17','29000','Хмельницька','Героїв Майдану',2);
INSERT INTO `address` (`address_id`,`building`,`city`,`district`,`flat`,`postcode`,`region`,`street`,`user_id`) VALUES (3,'30','Стрий','Стрийський','0','353567','Львівська','Героїв Майдану',3);
INSERT INTO `address` (`address_id`,`building`,`city`,`district`,`flat`,`postcode`,`region`,`street`,`user_id`) VALUES (4,'45','Львів',NULL,'78','79026','Львівська','Стрийська',4);
INSERT INTO `address` (`address_id`,`building`,`city`,`district`,`flat`,`postcode`,`region`,`street`,`user_id`) VALUES (5,'34','Київ',NULL,'90','4456767','Київська','Бандери',5);

INSERT INTO `passport_data` (`passport_data_id`,`number`,`published_by_data`,`seria`,`user_id`) VALUES (1,2234,'Львівський....','КС',1);
INSERT INTO `passport_data` (`passport_data_id`,`number`,`published_by_data`,`seria`,`user_id`) VALUES (2,123456,'Хмельницьким МВ УМВС України в Хмельницький області 01 січня 1997 року','КК',2);
INSERT INTO `passport_data` (`passport_data_id`,`number`,`published_by_data`,`seria`,`user_id`) VALUES (3,123456,'Стрийський МВ УМВС України в Львівській області 01 січня 1965 року','КК',3);
INSERT INTO `passport_data` (`passport_data_id`,`number`,`published_by_data`,`seria`,`user_id`) VALUES (4,1122456,'Львівський....','КС',4);
INSERT INTO `passport_data` (`passport_data_id`,`number`,`published_by_data`,`seria`,`user_id`) VALUES (5,1126789,'Київський....','КС',5);

INSERT INTO `tomes` (`tome_id`,`identifier`,`registrator_id`) VALUES (1,'12345',2);
INSERT INTO `tomes` (`tome_id`,`identifier`,`registrator_id`) VALUES (2,'6789',4);

INSERT INTO `resource_types` (`resource_type_id`,`type_name`) VALUES (1,'земельний');
INSERT INTO `resource_types` (`resource_type_id`,`type_name`) VALUES (2,'радіочастотний');

INSERT INTO `discrete_parameters` (`discrete_parameter_id`,`description`,`unit_name`,`resource_type_id`) VALUES (1,'периметер','м',1);
INSERT INTO `discrete_parameters` (`discrete_parameter_id`,`description`,`unit_name`,`resource_type_id`) VALUES (2,'площа','га',1);
INSERT INTO `discrete_parameters` (`discrete_parameter_id`,`description`,`unit_name`,`resource_type_id`) VALUES (3,'потужність','мВт',2);
INSERT INTO `discrete_parameters` (`discrete_parameter_id`,`description`,`unit_name`,`resource_type_id`) VALUES (4,'напруженість','мВт',2);
INSERT INTO `discrete_parameters` (`discrete_parameter_id`,`description`,`unit_name`,`resource_type_id`) VALUES (11,'площа','га',2);

INSERT INTO `linear_parameters` (`linear_parameter_id`,`description`,`unit_name`,`resource_type_id`) VALUES (1,'cмуга радіочастот','МГц',2);
INSERT INTO `linear_parameters` (`linear_parameter_id`,`description`,`unit_name`,`resource_type_id`) VALUES (2,'широта діапазону','кГц',2);
INSERT INTO `linear_parameters` (`linear_parameter_id`,`description`,`unit_name`,`resource_type_id`) VALUES (3,'радіус дії','км',2);

INSERT INTO `list_of_resouces` (`resources_id`,`date`,`description`,`identifier`,`reason_inclusion`,`status`,`registrator_id`,`tome_id`,`resource_type_id`) VALUES (1,'2015-12-08 15:33:01','ліс','123567','паспорт...','ACTIVE',2,1,1);
INSERT INTO `list_of_resouces` (`resources_id`,`date`,`description`,`identifier`,`reason_inclusion`,`status`,`registrator_id`,`tome_id`,`resource_type_id`) VALUES (2,'2015-12-08 15:33:01','радіочастоти','123555','Паспорт громадянина україни...','ACTIVE',2,1,2);
INSERT INTO `list_of_resouces` (`resources_id`,`date`,`description`,`identifier`,`reason_inclusion`,`status`,`registrator_id`,`tome_id`,`resource_type_id`) VALUES (3,'2015-12-08 15:33:01','суперрадіочастоти','111111','Посвідчення водія ...','ACTIVE',2,1,2);

INSERT INTO `area` (`area_id`,`latitude`,`longitude`,`order_number`,`resource_id`) VALUES (1,49.86378,24.02591,1,1);
INSERT INTO `area` (`area_id`,`latitude`,`longitude`,`order_number`,`resource_id`) VALUES (2,49.86372,24.02599,2,1);
INSERT INTO `area` (`area_id`,`latitude`,`longitude`,`order_number`,`resource_id`) VALUES (3,49.86362,25.02599,3,1);
INSERT INTO `area` (`area_id`,`latitude`,`longitude`,`order_number`,`resource_id`) VALUES (4,50.86362,24.07699,4,1);
INSERT INTO `area` (`area_id`,`latitude`,`longitude`,`order_number`,`resource_id`) VALUES (5,53.876,30.01,1,2);
INSERT INTO `area` (`area_id`,`latitude`,`longitude`,`order_number`,`resource_id`) VALUES (6,63.55,33.76,2,2);
INSERT INTO `area` (`area_id`,`latitude`,`longitude`,`order_number`,`resource_id`) VALUES (7,49.3552,43.54,3,2);
INSERT INTO `area` (`area_id`,`latitude`,`longitude`,`order_number`,`resource_id`) VALUES (8,50.345,24.07699,4,2);
INSERT INTO `area` (`area_id`,`latitude`,`longitude`,`order_number`,`resource_id`) VALUES (9,53.876,30.01,1,3);
INSERT INTO `area` (`area_id`,`latitude`,`longitude`,`order_number`,`resource_id`) VALUES (10,63.55,33.76,2,3);
INSERT INTO `area` (`area_id`,`latitude`,`longitude`,`order_number`,`resource_id`) VALUES (11,49.3552,43.54,3,3);
INSERT INTO `area` (`area_id`,`latitude`,`longitude`,`order_number`,`resource_id`) VALUES (12,50.345,24.07699,4,3);
INSERT INTO `area` (`area_id`,`latitude`,`longitude`,`order_number`,`resource_id`) VALUES (13,222,333,5,1);

INSERT INTO `resource_linear_values` (`resource_linear_param_id`,`maximal_value`,`minimal_value`,`linear_parameter_id`,`resource_id`) VALUES (1,2483.5,2400,1,2);
INSERT INTO `resource_linear_values` (`resource_linear_param_id`,`maximal_value`,`minimal_value`,`linear_parameter_id`,`resource_id`) VALUES (2,5350,5150,2,2);
INSERT INTO `resource_linear_values` (`resource_linear_param_id`,`maximal_value`,`minimal_value`,`linear_parameter_id`,`resource_id`) VALUES (3,2700,2500,1,2);
INSERT INTO `resource_linear_values` (`resource_linear_param_id`,`maximal_value`,`minimal_value`,`linear_parameter_id`,`resource_id`) VALUES (4,2483.5,1100,3,2);
INSERT INTO `resource_linear_values` (`resource_linear_param_id`,`maximal_value`,`minimal_value`,`linear_parameter_id`,`resource_id`) VALUES (5,5350,1110,2,2);
INSERT INTO `resource_linear_values` (`resource_linear_param_id`,`maximal_value`,`minimal_value`,`linear_parameter_id`,`resource_id`) VALUES (6,2100,9999,1,2);

INSERT INTO `resource_discrete_values` (`resource_discrete_value_id`,`value`,`discrete_parameter_id`,`resource_id`) VALUES (1,0.0868,1,1);
INSERT INTO `resource_discrete_values` (`resource_discrete_value_id`,`value`,`discrete_parameter_id`,`resource_id`) VALUES (2,127.7,2,1);
INSERT INTO `resource_discrete_values` (`resource_discrete_value_id`,`value`,`discrete_parameter_id`,`resource_id`) VALUES (3,100,3,2);
INSERT INTO `resource_discrete_values` (`resource_discrete_value_id`,`value`,`discrete_parameter_id`,`resource_id`) VALUES (4,500.55,3,2);
INSERT INTO `resource_discrete_values` (`resource_discrete_value_id`,`value`,`discrete_parameter_id`,`resource_id`) VALUES (5,23.54,3,2);
INSERT INTO `resource_discrete_values` (`resource_discrete_value_id`,`value`,`discrete_parameter_id`,`resource_id`) VALUES (6,200,4,2);
