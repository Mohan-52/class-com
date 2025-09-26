FROM openjdk:17-jdk
ADD target/class-com-v1.jar class-com-v1.jar
ENTRYPOINT ["java", "-jar", "/class-com-v1.jar"]