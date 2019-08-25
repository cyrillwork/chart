# chart
Simple chart (JAVA 1.8, Spring-boot2, PostgreSQL, maven)

# usage
For work you need PostgreSQL. 
Create database db_chart and user develop with password 123456. 
There are sql command in file init.sql, which do it.

#build
mvn -X clean package

# run
java -jar target/chart-1.0-SNAPSHOT.jar