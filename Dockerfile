FROM openjdk:22-jdk
ADD target/GrpcClient-0.0.1-SNAPSHOT.jar client.jar
EXPOSE 10000
# run with profile dev
ENTRYPOINT ["java", "-jar", "client.jar", "--spring.profiles.active=dev"]