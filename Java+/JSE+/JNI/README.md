# JNI

1. Clean: `rm -rf build`
2. Generate C Header File: `javac -d build -h build/headers src/main/java/jni/HelloWorld.java`
3. Compile C file:
   `gcc -shared -fpic -o build/libhello.so -I${JAVA_HOME}/include -I${JAVA_HOME}/include/linux -Ibuild/headers src/main/c/hello.c`
4. Run Java app: `java -cp build -Djava.library.path=build jni.HelloWorld`
