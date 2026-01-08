package dev.shaaf.tswing.event;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * EventQueue is responsible for queuing and dispatching events.
 * Similar to java.awt.EventQueue but for terminal-based events.
 */
public class EventQueue {
    private final BlockingQueue<TEvent> eventQueue;
    private static final EventQueue instance = new EventQueue();

    private EventQueue() {
        this.eventQueue = new LinkedBlockingQueue<>();
    }

    /**
     * Get the singleton instance of the EventQueue.
     */
    public static EventQueue getInstance() {
        return instance;
    }

    /**
     * Post an event to the queue.
     */
    public void postEvent(TEvent event) {
        try {
            eventQueue.put(event);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    /**
     * Get the next event from the queue, blocking if necessary.
     */
    public TEvent getNextEvent() throws InterruptedException {
        return eventQueue.take();
    }

    /**
     * Poll for an event without blocking.
     * Returns null if no event is available.
     */
    public TEvent pollEvent() {
        return eventQueue.poll();
    }

    /**
     * Check if there are any pending events.
     */
    public boolean hasPendingEvents() {
        return !eventQueue.isEmpty();
    }

    /**
     * Clear all pending events.
     */
    public void clear() {
        eventQueue.clear();
    }
}