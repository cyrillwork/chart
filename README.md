# chart
Simple chart (JAVA 1.8, Spring-boot2, PostgreSQL, maven)
Internalization english and russian

# usage
For work you need PostgreSQL. 
Create database db_chart (for integration tests used db_chart) and user develop with password 123456 
(or change application-property). 
There are sql-commands in file init.sql, which do it.
At first start, DB has to automatically deploys to create user 'admin' with password '12345'.  

# build
mvn -X clean package

# run
java -jar target/chart-1.0-SNAPSHOT.jar

