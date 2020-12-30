FROM java:8-jdk-alpine

COPY DESEncryption.java /usr/app/
COPY DES.java /usr/app/

WORKDIR /usr/app

RUN javac DESEncryption.java

CMD ["java", "DESEncryption"]