FROM java:8

LABEL author="s.shin@marathoner.kr"

VOLUME /tmp

EXPOSE 8000

ARG JAR_FILE=build/libs/proxy-0.0.1-SNAPSHOT.jar

ADD ${JAR_FILE} springboot-app.jar

# Run the jar file
ENTRYPOINT ["java", "-jar", "/springboot-app.jar", "-Dspring.profiles.active=docker"]
