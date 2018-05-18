package com.bettercloud.perf.churchspringboot;

public class Entry {
    private long id;
    private String message;

    public Entry() {
    }

    public Entry(long id, String message) {
        this.id = id;
        this.message = message;
    }

    public long getId() {
        return id;
    }

    public String getMessage() {
        return message;
    }

    @Override
    public String toString() {
        return "Entry{" +
                "id=" + id +
                ", message='" + message + '\'' +
                '}';
    }
}
