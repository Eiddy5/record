package org.newcaffeina.message;

public class Message {
    private Action action;
    private String key;
    private String value;
    private final Long version = System.currentTimeMillis();

    private Message(Builder builder){
        this.action = builder.action;
        this.key = builder.key;
        this.value = builder.value;
    }

    public static Builder builder(){
        return new Builder();
    }

    public static class Builder{
        private Action action;
        private String key;
        private String value;

        public Builder action(Action action){
            this.action = action;
            return this;
        }
        public Builder key(String key){
            this.key = key;
            return this;
        }

        public Builder value(String value){
            this.value = value;
            return this;
        }
        public Message build(){
            if (action == null){
                throw new IllegalArgumentException("action is null");
            }
            if (key == null){
                throw new IllegalArgumentException("key is null");
            }
            if (value == null){
                throw new IllegalArgumentException("value is null");
            }
            return new Message(this);
        }
    }

    public Action getAction() {
        return action;
    }

    public String getKey() {
        return key;
    }

    public String getValue() {
        return value;
    }

    public Long getVersion() {
        return version;
    }

    public boolean isBefore(Message message){
        return this.version < message.getVersion();
    }
}
