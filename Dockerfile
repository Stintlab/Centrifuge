FROM gcr.io/distroless/java21-debian12:nonroot
ARG VERSION
ADD build/libs/centrifuge-${VERSION}.jar /usr/share/java/centrifuge.jar
CMD["/usr/share/java/centrifuge.jar"]
