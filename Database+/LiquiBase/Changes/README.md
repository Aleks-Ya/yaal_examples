# Using LiguiBase commands

# migrate
```
liquibase --driver=org.postgresql.Driver \
     --changeLogFile=db.changelog-master.xml \
     --url="jdbc:postgresql://172.17.0.2:5432/commands" \
     --username=pguser \
     --password=pgpass \
     migrate
 ```


## Update
```
liquibase --driver=org.postgresql.Driver \
     --changeLogFile=db.changelog-master.xml \
     --url="jdbc:postgresql://172.17.0.2:5432/commands" \
     --username=pguser \
     --password=pgpass \
     update
```

## Generate ChangeLog
```
liquibase --driver=org.postgresql.Driver \
    --changeLogFile=db.changelog-master2.xml \
    --url="jdbc:postgresql://172.17.0.2:5432/commands" \
    --username=pguser \
    --password=pgpass \
    generateChangeLog
```

## Generate SQL script
```
liquibase --driver=org.postgresql.Driver \
     --changeLogFile=db.changelog-master.xml \
     --url="jdbc:postgresql://172.17.0.2:5432/commands" \
     --username=pguser \
     --password=pgpass \
     updateSQL
```


## Status (show number of not executed Change Sets)
```
liquibase --driver=org.postgresql.Driver \
     --changeLogFile=db.changelog-master.xml \
     --url="jdbc:postgresql://172.17.0.2:5432/commands" \
     --username=pguser \
     --password=pgpass \
     status
```
