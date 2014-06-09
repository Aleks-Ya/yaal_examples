package client;

import client.stubs.ObjectFactory;

/**
 * Реализация клиента с применением стабов.
 */
public class StubClient {
    public static void main(String[] args) {
        ObjectFactory factory = new ObjectFactory();
        factory.createGetHelloWorldAsString();
    }
}
