FROM openjdk:11

ADD petclinic.jar  /petclinic.jar
RUN chmod +x /petclinic.jar

CMD ["java", "-jar", "/petclinic.jar"]