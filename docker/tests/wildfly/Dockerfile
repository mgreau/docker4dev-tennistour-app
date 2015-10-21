FROM jboss/wildfly:8.2.1.Final

ENV MYSQL_CONNECTOR_VERSION 5.1.34

RUN /opt/jboss/wildfly/bin/add-user.sh -up mgmt-users.properties admin Admin#70365 --silent

# Install mysql module
ADD mysql-module.xml /opt/jboss/wildfly/modules/system/layers/base/com/mysql/jdbc/main/module.xml
RUN  curl -L -o /opt/jboss/wildfly/modules/system/layers/base/com/mysql/jdbc/main/mysql-connector-java.jar https://repo1.maven.org/maven2/mysql/mysql-connector-java/${MYSQL_CONNECTOR_VERSION}/mysql-connector-java-${MYSQL_CONNECTOR_VERSION}.jar

ADD mysql-connector-java-5.1.30-bin.jar /opt/jboss-eap-6.2/modules/system/layers/base/com/mysql/jdbc/main/mysql-connector-java.jar

EXPOSE 8080
EXPOSE 9990

CMD ["/opt/jboss/wildfly/bin/standalone.sh", "-b", "0.0.0.0", "-bmanagement", "0.0.0.0"]

