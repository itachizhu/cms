FROM java:8
VOLUME /tmp
EXPOSE 9091
ADD cms-boot.jar app.jar
RUN sh -c 'touch /app.jar'
ENTRYPOINT ["java", "-jar", "/app.jar"]