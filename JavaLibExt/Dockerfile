FROM openjdk:8-jdk-alpine

COPY Main.java Main.java

ENV JDK_DIR=/usr/lib/jvm/java-1.8-openjdk
ENV EXT_DIR=${JDK_DIR}/jre/lib/ext

# Compile Jar and put to lib/ext
RUN ${JDK_DIR}/bin/javac -d . Main.java \
	&& mkdir -p ${EXT_DIR} \
	&& ${JDK_DIR}/bin/jar -cf ${EXT_DIR}/hello.jar hello \
	&& rm -r hello Main.java

# List file structure
RUN ls -l . \
	&& ls -l ${EXT_DIR} \
	&& ${JDK_DIR}/bin/jar -tf ${EXT_DIR}/hello.jar

# Run app from Jar located in jre/lib/ext
CMD ${JDK_DIR}/bin/java hello.Main 
