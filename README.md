grails-api-toolkit-test

In config, move test.properties to your home directory (or home directory of running app server - ie Tomcat) and create .test diectory to put into (see line 15 of Config.groovy).

Then open file in text editor and uncomment and change login and passwords for database and system.

In MySQL/MariaDB DB, you will have to create two database instances: a testdb instance and a testuser instance. Make sure to 'GRANT' the correct permissions that you set in the properties file.

After this. You should be able to run the project. Please file any bugs or issues you have.

Note: You can change your RDBMS but you will have to manage the datasource and changes yourself.



