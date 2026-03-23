package dev.hollink.partytrails.events;

public interface EventListener<T> {
    void onEvent(T event);
}