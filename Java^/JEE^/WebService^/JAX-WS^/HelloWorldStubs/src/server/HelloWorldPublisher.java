package server;

import javax.xml.ws.Endpoint;

//Endpoint publisher
public class HelloWorldPublisher {

    public static void main(String[] args) {
        Endpoint.publish("http://localhost:9999/ws/hello", new HelloWorldImpl());
    }

}
