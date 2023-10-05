# jre environment
FROM openjdk:17-alpine

MAINTAINER Zhihui Wan

# env variable
ENV jAVA_OPTS ""

# add jar package to container
ADD *.jar /home/app.jar

# socket for connect
EXPOSE 8080

# log and exec command
CMD ["sh", "-c", "echo \"****** exec command: java -jar ${jAVA_OPTS} /home/app.jar\" & java -jar /home/app.jar"]