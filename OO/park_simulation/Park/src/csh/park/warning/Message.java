package csh.park.warning;

import csh.park.car.Car;

import static csh.park.data.PublicData.print;

/**
 * Message类封装了车辆进出停车场的状态信息,包括通行\不能进入停车场\未记录在停车场信息中,集中信息
 * Created by Alan on 15/12/9.
 */
public class Message {
    /**
     * 信息内容
     */
    protected MessageType messageType;
    /**
     * 信息所属车辆
     */
    protected Car car;

    /**
     * 三类消息,分别是:允许通过\非本系统卡\系统记录未发现
     */
    public enum MessageType {
        /**
         * 非本系统卡
         */
        not_found_in_system,
        /**
         * 系统记录未发现
         */
        not_found_in_register,
        /**
         * 允许通过
         */
        ok}

    public Message(MessageType messageType, Car car) {
        this.messageType = messageType;
//        print.println(messageType);
        this.car = car;
    }

    /**
     * 消息属性
     * @return messageType
     */
    public MessageType getMessageType() {
        return messageType;
    }

    /**
     * 车辆信息
     * @return Car引用
     */
    public Car getCar() {
        return car;
    }
}
