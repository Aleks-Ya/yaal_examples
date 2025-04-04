package jni;

public class HelloWorld {
    static {
        System.loadLibrary("hello"); // Load native library at runtime
    }

    // Declare a native method
    public native void printHello();

    public static void main(String[] args) {
        new HelloWorld().printHello(); // Call the native method
    }
}
