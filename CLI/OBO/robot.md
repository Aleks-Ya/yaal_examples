# ROBOT OBO Tool

Site: http://robot.obolibrary.org/

Help: `robot help`
Help about a command: `robot verify --help`
Debug log level: add `-vvv`

Execute a Reasoner: `robot reason --input courses_all.obo --reasoner elk --output courses_all_reasoned.obo`
Convert OWL XML to OBO: `robot convert --format OBO --input ontology1.xml --output ontology1.obo`

Limit memory: `export ROBOT_JAVA_ARGS="-Xmx2G"`
Wait for remote debugger: `export ROBOT_JAVA_ARGS="-agentlib:jdwp=transport=dt_socket,server=y,suspend=y,address=*:5005"`

## Errors
### OBO STRUCTURE ERROR
Command: `robot convert --format OBO --input go-plus.owl --output go-plus.obo`
Error message:
```
OBO STRUCTURE ERROR Ontology does not conform to OBO structure rules:
multiple name tags not allowed. in frame:Frame(has_part id( has_part)name( has part)xref( BFO:0000051)namespace( external)name( has_part)def( a core relation that holds between a whole and its part)is_transitive( true)is_a( overlaps))
For details see: http://robot.obolibrary.org/errors#obo-structure-error
Use the -vvv option to show the stack trace.
Use the --help option to see usage information.
```
Fix: add `--check false` option: `robot convert --check false --format OBO --input go-plus.owl --output go-plus.obo`
