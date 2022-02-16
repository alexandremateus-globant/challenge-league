FROM openjdk:11

ARG PROFILE
ARG ADDITIONAL_OPTS

ENV PROFILE=${PROFILE}
ENV ADDITIONAL_OPTS=${ADDITIONAL_OPTS}

WORKDIR /app

COPY /target/challenge*.jar /app/challenge.jar

EXPOSE 	5005
EXPOSE 8080

CMD java ${ADDITIONAL_OPTS} -jar /app/challenge.jar --spring.profiles.active=${PROFILE}