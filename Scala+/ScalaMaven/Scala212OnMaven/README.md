# ScalaOnMaven

## Configure IDEA project
1. Java
    1. Module Settings - Project - SDK - Zulu 11
2. Scala
    1. Module Settings - Global Libraries - New Global Library - Choose latest Scala 2.12.x distribution
    2. Module Settings - Module - `scala212-on-maven` - Add Scala 2.12.x library

## Maven
Build: `mvn clean package`