package foo.bar;

public class StandardOutMessageRenderer implements MessageRenderer {
    private MessageProvider provider;

    @Override
    public void render() {
        if (provider == null) {
            throw new RuntimeException("Message provider isn't specified.");
        }
        System.out.println(provider.getMessage());
    }

    @Override
    public MessageProvider getMessageProvider() {
        return provider;
    }

    @Override
    public void setMessageProvider(MessageProvider provider) {
        this.provider = provider;
    }
}
