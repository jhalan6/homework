package csh.park.car;

import csh.park.data.Employee;
import csh.park.data.PublicData;

import static csh.park.data.PublicData.*;

import java.util.Random;

/**
 * CarFactory类用来定时生成Car线程,根据配置页面的结果随机生成数据
 * Created by Alan on 15/12/9.
 */
public class CarFactory extends Thread {
    /**
     * 一个随机数对象,用来生成随机的员工信息等
     */
    private Random random;
    /**
     * 公共数据的内部引用
     */
    private PublicData publicData;
    /**
     * 一个临时的员工信息
     */
    private Employee temp;

    public CarFactory() {
        this.publicData = PublicData.getPublicData();
        random = new Random();
    }

    /**
     * 每10个单位时间进行一次检查,看看能否产生新的车辆,如果可以则新建一个Car线程,并启动之
     */
    @Override
    public void run() {
        while (true) {
            try {
                sleep(10 * MID_TIME);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            //如果左上角的两个位置有车辆的话,说明上一次制造的汽车还未进入停车场,所以先不生产下一辆车
            if (publicData.getPark().getStatus(0, 0) && publicData.getPark().getStatus(1, 0))
                new Car(nextIn(), nextOut(), random.nextInt(300 * MID_TIME)).start();
        }
    }

    /**
     * 随机生成一个员工的入场信息
     *
     * @return 一个员工信息的引用, 这个员工信息是在 1~(1+非员工概率)*最大员工数 之间的一个随机数
     */
    private Employee nextIn() {
        temp = new Employee(random.nextInt((int) (publicData.getConfig().getMaxEmployee() * (1 + publicData.getConfig().getRateOfNotEmployee()))));
        return temp;
    }

    /**
     * 随机生成一个员工的出场信息
     *
     * @return 一个员工信息的引用, 根据概率计算, 若这个车的前后不一致, 那么将这辆车的出门信息设置为入门信息+1
     */
    private Employee nextOut() {
        int forRandom = random.nextInt((int) ((publicData.getConfig().getRateOfError() + 1) * 100));
        if (forRandom > 100) {
            return new Employee(temp.getId() + 1);
        } else
            return temp;
    }
}
