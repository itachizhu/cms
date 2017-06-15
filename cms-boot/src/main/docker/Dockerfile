FROM java:8-jre
ADD cms-boot.jar app.jar
ADD wrapper.sh wrapper.sh
RUN sh -c 'mkdir -p /data01/cms'
RUN sh -c 'chmod +x /wrapper.sh'
RUN sh -c 'touch /app.jar'
ENTRYPOINT ["/bin/bash", "/wrapper.sh"]