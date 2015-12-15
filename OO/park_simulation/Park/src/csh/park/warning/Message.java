package csh.park.warning;

import csh.park.data.PublicData;

/**
 * Created by Alan on 15/12/9.
 */
public class Message {
    protected MessageType messageType;
    public enum MessageType {
        not_found_in_system,not_found_in_register,ok
    }
    public Message(MessageType messageType) {
        this.messageType = messageType;
        PublicData.print.println(messageType);
    }
    public MessageType getMessageType() {
        return messageType;
    }
}
