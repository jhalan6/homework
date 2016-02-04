package csh.park.check;

import csh.park.car.Car;
import csh.park.data.Employee;
import csh.park.data.PublicData;
import csh.park.park.Park;
import csh.park.warning.Message;

/**
 * CheckOut类实现了后门及其门禁
 * Created by Alan on 15/12/10.
 */
public class CheckOut extends CheckID {
    /**
     * 对要离开停车场的员工车辆进行校验,查看车辆的出门数据
     * 这里隐含了一种错误,就是可能车a拿着本公司员工A的员工卡进入,
     * 另一个车持员工B的员工卡进入,a持B卡离开停车场,这种错误没有检测
     *
     * @param car 当前离开停车场的车辆
     * @return 含有离场信息的Message对象
     */
    @Override
    public Message checkEmployee(Car car) {
        Employee employee = car.getOutData();
        /*  如果车辆是员工的,或者车辆被处理过,就放行
         *  这里隐含了一种错误,就是可能车a拿着本公司员工A的员工卡进入,
         *  另一个车持员工B的员工卡进入,a持B卡离开停车场,这种错误没有检测
         */
        if (contains(employee) || car.isHandled()) {
//        if (car.getInData().equals(car.getOutData()) || car.isHandled()) {//这个判定方法可以避免隐含错误
            publicData.getEmployeesInPark().remove(employee);
            openDoor();
            return new Message(Message.MessageType.ok, car);
        } else
            return new Message(Message.MessageType.not_found_in_system, car);
    }
}
