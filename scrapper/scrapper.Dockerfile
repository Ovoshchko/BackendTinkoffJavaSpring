FROM openjdk:21 as builder
COPY target/*.jar scrapper-application.jar
RUN java -Djarmode=layertools -jar scrapper-application.jar extract

FROM openjdk:21
COPY --from=builder dependencies/ ./
COPY --from=builder snapshot-dependencies/ ./
COPY --from=builder spring-boot-loader/ ./
COPY --from=builder application/ ./
ENTRYPOINT ["java", "org.springframework.boot.loader.launch.JarLauncher"]
