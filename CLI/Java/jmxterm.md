# jmxterm

Download: `wget -O jmxterm.jar https://github.com/jiaqi/jmxterm/releases/download/v1.0.2/jmxterm-1.0.2-uber.jar`
Run console: `java -jar jmxterm.jar`

## Console commands
Help: `help`
Help about command: `help get`
List all local JMX processes: `jvms` (requires `sudo`)
Connect by PID: `open 2252087`
List all MBeans: `beans`
List beans in a domain: `beans -d com.example`
Set current bean: `bean com.example:type=Hello`
List attributes and operations of current bean: `info`
Get an attribute value: `get CacheSize`
Set an attribute value: `set CacheSize 777`
Invoke an operation: `run sayHello`
