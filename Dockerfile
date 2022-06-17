FROM openjdk:8-jdk-alpine as build
WORKDIR /app

COPY mvnw .
COPY .mvn .mvn
COPY pom.xml .
COPY src src

RUN apk add dos2unix && dos2unix mvnw && ls

EXPOSE 8080

RUN ./mvnw clean install -DskipTests
#RUN mkdir -p target/dependency && (cd target/dependency; jar -xf ../*.jar)

CMD java -jar target/reddit-0.0.1-SNAPSHOT.jar

#COPY build/libs/*.jar app.jar

#FROM openjdk:8-jdk-alpine
#VOLUME /tmp
#ARG DEPENDENCY=/app/build/dependency
#COPY --from=build ${DEPENDENCY}/BOOT-INF/lib /app/lib
#COPY --from=build ${DEPENDENCY}/META-INF /app/META-INF
#COPY --from=build ${DEPENDENCY}/BOOT-INF/classes /app
#ENTRYPOINT ["java","-cp","app:app/lib/*","com.nadeemarif.reddit.RedditApplication"]
