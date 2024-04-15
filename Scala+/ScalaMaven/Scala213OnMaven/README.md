# ScalaOnMaven

## Configure IDEA project
1. Java
   1. Module Settings - Project - SDK - Zulu 21
2. Scala
   1. Module Settings - Global Libraries - New Global Library - Choose latest Scala 2.13.x distribution
   2. Module Settings - Module - `scala213-on-maven` - Add Scala 2.13.x library

## Maven
Build: `mvn clean package`