FROM maven:3.6-jdk-8-slim

ADD pom.xml /tmp
RUN mkdir /tmp/annotador-core && mkdir /tmp/annotador-rest
ADD annotador-core/pom.xml /tmp/annotador-core
ADD annotador-rest/pom.xml /tmp/annotador-rest/pom.xml

RUN mkdir /var/maven/ && chmod -R 777 /var/maven  && umask 0777 /var/maven
ENV MAVEN_CONFIG /var/maven/
ENV MAVEN_CONFIG /var/maven/.m2
RUN mkdir /app
ADD pom.xml /app
RUN cd /app && mkdir ./annotador-core && chmod -R 777 ./annotador-core
RUN cd /app && mkdir ./annotador-rest && chmod -R 777 ./annotador-rest
RUN cd /app && mkdir ./annotador-web && chmod -R 777 ./annotador-web
ADD annotador-core /app/annotador-core
ADD annotador-rest /app/annotador-rest
ADD annotador-web /app/annotador-web

WORKDIR /app

RUN mvn -Duser.home=/var/maven -Dmaven.repo.local=/var/maven/.m2 -f pom.xml install -DskipTests

EXPOSE 5000

WORKDIR /app/annotador-rest/

RUN apt-get update && apt-get install -y python3-pip
RUN pip3 install Flask==2.0.3
RUN pip3 install requests==2.25.1
ENV FLASK_ENV development
ENV FLASK_APP server
COPY start.sh start.sh
COPY server.py server.py

CMD ["bash", "./start.sh"]
