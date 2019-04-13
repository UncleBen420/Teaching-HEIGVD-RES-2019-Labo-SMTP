FROM java:8
MAINTAINER Remy Vuagniaux <remy.vuagniaux@heig-vd.ch>, Erwan Moreira <erwan.moreira@heig-vd.ch>


COPY MockMock.jar /opt/app/mockmock.jar


EXPOSE 25000
EXPOSE 8080

CMD ["java", "-jar", "/opt/app/mockmock.jar", "-p", "25000", "-h", "8080"]
