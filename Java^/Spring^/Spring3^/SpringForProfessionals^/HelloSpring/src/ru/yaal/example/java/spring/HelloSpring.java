package ru.yaal.example.java.spring;

public class HelloSpring {
    public static void main(String[] args) {
        MessageProvider provider = MessageSupportFactory.getInstance().getMessageProvider();
        MessageRenderer renderer = MessageSupportFactory.getInstance().getMessageRenderer();
        renderer.setMessageProvider(provider);
        renderer.render();
    }
}
