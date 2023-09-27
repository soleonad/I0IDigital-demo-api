package com.I0Idigital.demo.handler.error;

public class NoAvailableQueueError extends GenericRuntimeException {
    public NoAvailableQueueError() {
        super("No available queue");
    }

    public static NoAvailableQueueError create() {
        return new NoAvailableQueueError();
    }
}
