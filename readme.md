Resource Registration System

Resource registration system is open-source project designed to maintain a centralized register of Natural Resources of Ukraine belonging to the Ukrainian people. The main goal of the project to create a universal system that will make possible to add the information about resources with any parameters and find information about entered resources. This will simplify the exchange of information between representatives of territorial communities from all over Ukraine.

Installation dependencies

The following dependencies are necessary:
  Java 8
  Maven 4
  DataBase(for example MySQL)
  Web server for Java application (for example TomCat 7)
  
Building and starting the server

To build the app locally please choose the maven profile "local".

Before running the app, create a database called registrator_db with charset encoding UTF-8 (more specifically with collation utf8_unicode_ci). Then deploy the application to web server.
During the first start database tables will be created and filled with the start-up data. To get access to the application use the following credentials:

  username: admin
  password: admin

After sign in with admin credentials You can add additional users. 
Strongly recommended to change password for admin.

Team members
  LV-166 Java:
    Expert: Khohlov Oleksandr
    Lector: Yaroslav Garasym 
Team:
    Oleksiy Kirkach
    Andriy Palyga
    Andriy Chypurko
    Bohdan Koroliuk
    Anna Novosiadlo

It is necessary to configure the MySQL server for the correct work with cyrillic characters.
Please check do You server configuration file (my.ini for Windows or my.cnf for Unix-based system) have the following settings:

[client]
default-character-set=utf8

[mysql]
default-character-set=utf8

[mysqld]
collation-server = utf8_unicode_ci
init-connect='SET NAMES utf8'
character-set-server = utf8

All database settings You can change in database.properties file.
