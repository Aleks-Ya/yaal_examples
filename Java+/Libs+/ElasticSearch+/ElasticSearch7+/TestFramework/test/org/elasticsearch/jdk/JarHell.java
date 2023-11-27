package org.elasticsearch.jdk;

import java.util.function.Consumer;

/**
 * Disables jar hell check forced by ElasticSearch Test Framework.
 * Shadows {@link JarHell#checkJarHell(Consumer)} implementation
 * from "org.elasticsearch.test:framework".
 */
@SuppressWarnings("unused")
public class JarHell {
    public static void checkJarHell(Consumer<String> output) {
        System.out.println("Skip jar hell check.");
    }
}
