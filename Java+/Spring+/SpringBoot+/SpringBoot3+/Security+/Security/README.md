# Configure logging of a Spring Boot application

## Run

1. Main class: `logging.LoggingApp`
2. See messages in the log.

## Set log level

### For all loggers

Add `--trace`, `--debug`, etc. parameter to Java app.

### For specific loggers

For all: `-Dlogging.level.root=TRACE`  
For Spring: `-Dlogging.level.org.springframework=TRACE`  
For app: `-Dlogging.level.logging.LoggerA=TRACE`
