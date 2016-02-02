package csh.park.check;

import csh.park.car.Car;
import csh.park.data.Employee;

import static csh.park.data.PublicData.*;

import csh.park.warning.Message;

/**
 * CheckIn类实现了前门及前门门禁
 * Created by Alan on 15/12/10.
 */
public class CheckIn extends CheckID {
    /**
     * 对进入前门的员工车辆进行校验,查看车辆的进门数据
     *
     * @param car 当前进入停车场的车辆
     * @return 含有车辆是否通过的Message对象
     */
    @Override
    public Message checkEmployee(Car car) {
        Employee employee = car.getInData();
        //要求员工是本公司的并且该员工并没有车在这个停车场中
        if (employee.inEmployeeList() && !contains(employee)) {
            publicData.getEmployeesInPark().add(employee);
            //在开门这个过程中,线程整体会停2s,相当于车辆等门开的过程
            openDoor();
            return new Message(Message.MessageType.ok, car);
        } else {
            //修改门禁信息
            try {
                doorStatus = DoorStatus.no;
                publicData.getParkFrame().repaint();
                sleep(MID_TIME);
                doorStatus = DoorStatus.nothing;
                publicData.getParkFrame().repaint();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            //向前门错误列表添加信息
            publicData.getInError().add(new Message(Message.MessageType.not_found_in_register, car));
            //返回不再系统中的错误
            return new Message(Message.MessageType.not_found_in_system, car);
        }
    }
}
