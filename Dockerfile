# Base image with WildFly
FROM jboss/wildfly:30.0.1.Final

# Add WAR file to WildFly deployments directory
COPY target/api-set-tracker.war /opt/jboss/wildfly/standalone/deployments/

# Expose WildFly port
EXPOSE 4040

# Start WildFly in standalone mode
CMD ["/opt/jboss/wildfly/bin/standalone.sh"]
