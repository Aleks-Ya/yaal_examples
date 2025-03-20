# SystemDependency

A JAR file is distributed inside a ZIP file. 
Maven needs to download ZIP dependency, unpack JAR from it, and use the JAR file as a dependency.

Build: 
1. Unpack ZIP (in not invoked by `compile`): `mvn clean initialize`
2. Use unpacked JAR: `mvn compile`
