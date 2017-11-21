package com.marvelcomics.brito.presentation;

public class ResourceModel<T> {

    private State state;
    private T data;
    private String message;

    public ResourceModel(State state, T data, String message) {
        this.state = state;
        this.data = data;
        this. message = message;
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

    public enum State {
        LOADING, SUCCESS, ERROR
    }
}
