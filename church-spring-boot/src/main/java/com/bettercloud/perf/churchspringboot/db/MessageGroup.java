package com.bettercloud.perf.churchspringboot.db;

public class MessageGroup {
    private String message;
    private long count;

    public MessageGroup() {
    }

    public MessageGroup(String message, long count) {
        this.message = message;
        this.count = count;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setCount(long count) {
        this.count = count;
    }

    public String getMessage() {
        return message;
    }

    public long getCount() {
        return count;
    }

    @Override
    public String toString() {
        return "MessageGroup{" +
                "message='" + message + '\'' +
                ", count=" + count +
                '}';
    }
}
