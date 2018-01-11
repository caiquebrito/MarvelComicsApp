package com.marvelcomics.brito.presentation;

public class ResourceModel<T> {

    private State state;
    private T data;
    private String message;
    private Throwable throwable;

    public ResourceModel(State state, T data, String message) {
        this.state = state;
        this.data = data;
        this.message = message;
    }

    public ResourceModel(State state, T data, String message, Throwable throwable) {
        this.state = state;
        this.data = data;
        this.message = message;
        this.throwable = throwable;
    }

    public State getState() {
        return state;
    }

    public T getData() {
        return data;
    }

    public String getMessage() {
        return message;
    }

    public Throwable getThrowable() {
        return throwable;
    }

    public enum State {
        LOADING, SUCCESS, ERROR
    }
}
