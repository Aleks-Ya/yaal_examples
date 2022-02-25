# yarn CLI

[YARN Commands Reference](https://hadoop.apache.org/docs/r2.9.2/hadoop-yarn/hadoop-yarn-site/YarnCommands.html)

Application

Show all applications: 
```
yarn application -list
```
Show application status:
```
yarn application -status application_1631437118956_0001
```
Kill application: 
```
yarn application -kill application_1546650715715_0001
```

Node

Show all nodes: 
```
yarn node -list -all
```

Logs

Show logs for application:  
```
yarn logs -applicationId application_1547894060897_0005
```

Queue

Show queue status:
```
yarn queue -status default
```

RM Admin

Show RM state (active/standby):  
```
yarn rmadmin -getServiceState rm1
```
Switch Active RM to Standby:
```
yarn rmadmin -transitionToStandby rm1
```
