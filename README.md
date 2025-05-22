JBOSS INSTALLATION 

1.Copy Jboss EAP 8.0 to /opt

2.Extract Jboss Installation Manager 8.0.7 
  1.change dir jboss-installation-manager-8.0.7/bin/ 
  2.run jboss-installation-manager.sh update prepare\
        --candidate-dir=/tmp/jboss-candidate \
        --dir=/opt/jboss-eap-8.0
  3.run jboss-installation-manager.sh update apply \
        --candidate-dir=/tmp/jboss-candidate \
        --dir=/opt/jboss-eap-8.0
 //Now jboss updated to 8.0.7

3.Place mysql connector and module.xml in /opt/jboss-eap-8.0/modules/com/mysql/main

4.run domain.sh then go to localhost:9990
  1.go Configuration>Profile>DataSources & Drivers 
  2.add driver and Datasource properly

CONFIGURE JENKINS

1.Manage Jenkins>System--->Set server instance using bitbucket authentication token

2.Manage Jenkins>System--->Setup Global shared Pipelines (maybe it can configure seperatly for multibranch pipeline config)

3.Manage Jenkins>System--->Setup SSH to server 

4.Manage Jenkins>Tools--->Set Maven and Java PATH and VERSION

5.PipelineConfig---> add needed project and repo name with credentials

REQUIRED PLUGINS

1.SSH TO SERVER
2.BITBUCKET SERVER INTEGRATION
