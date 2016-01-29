package csh.park.warning;

import csh.park.car.Car;

/**
 * Created by Alan on 15/12/9.
 */
public class Message {
    protected MessageType messageType;
    protected Car car;

    public Car getCar() {
        return car;
    }

    public enum MessageType { not_found_in_system,not_found_in_register,ok}
    public Message(MessageType messageType,Car car) {
        this.messageType = messageType;
        System.err.println(messageType);
        this.car=car;
    }
    public MessageType getMessageType() {
        return messageType;
    }
}
