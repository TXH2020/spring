FROM eclipse-temurin:17
RUN apt update
RUN apt install -y mysql-server
RUN /etc/init.d/mysql start && mysql -u root -e "create database myspringdb;use mysql;ALTER USER 'root'@'localhost' IDENTIFIED WITH mysql_native_password BY ''"
# copy the packaged jar file into the docker image
COPY demo1-0.0.1-SNAPSHOT.jar /demo.jar

# set the startup command to execute the jar
ENTRYPOINT ["sh", "-c","/etc/init.d/mysql start && java -jar /demo.jar"]
