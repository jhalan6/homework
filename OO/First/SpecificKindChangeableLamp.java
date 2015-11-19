///**根据ADT的概念,定义一个抽象数据类型,来表示亮度可调的台灯。
// *表示的手法及语言不限,但须体现类型的主要特征:
// *值集(值的数据结构)、操作集(各操作的语法和用自然语言描述的语义),
// *并规定对外的接口。
// */
////一个抽象类，包含了亮度可调台灯的接口
//abstract class Changeable_lamp{
//    //台灯的组件（值集）：灯泡、外壳、开关、电源线、旋钮
//    protected Lamp lamp;
//    protected Outer outer;
//    protected Button button;
//    protected Knob knob;
//    protected Power power;
//    //台灯对外提供的接口:亮、暗、开、关、接电、不接电
//    abstract public boolean brighter();
//    abstract public boolean darker();
//    abstract public void buttonOn();
//    abstract public void buttonOff();
//    abstract public void powerOn();
//    abstract public void powerOff();
//    //台灯内部实现的改变操作，有些时候状态的改变会带来台灯状态的改变
//    abstract protected void modify();
//    //台灯的属性初始化
//    public Changeable_lamp(Lamp lamp,Outer outer,Button button,Knob knob,Power power){
//        this.lamp=lamp;
//        this.outer=outer;
//        this.button=button;
//        this.knob=knob;
//        this.power=power;
//    }
//}
//class SpecificKindChangeableLamp extends Changeable_lamp{
//    public SpecificKindChangeableLamp(Lamp lamp,Outer outer,Button button,Knob knob,Power power){
//        super(lamp,outer,button,knob,power);
//    }
//    //实现亮度可调节台灯的插头插入拔出，电源类具有插拔方法，
//    //可以返回是否成功
//    public void powerOn(){
//        power.on();
//        modify();
//    }
//    public void powerOff(){
//        power.off();
//        modify();
//    }
//    //实现亮度可调节台灯的开关功能
//    public void buttonOn(){
//        button.on();
//        modify();
//    }
//    public void buttonOff(){
//        button.off();
//        modify();
//    }
//    //实现亮度可调节台灯的调节部分
//    //旋钮应该具有刻度变大，刻度变小的功能，同时还具有相应的上限
//    public boolean brighter(){
//        knob.higer();
//        modify();
//    }
//    public boolean darker(){
//        knob.lower();
//        modify();
//    }
//    //这里的knob.getScale()方法应该能将当前的刻度转换成百分比数据提供给灯泡，用来控制亮度
//    protected void modify(){
//        if(power.isOn()&&button.isOn()&&knob!=0){
//            lamp.atBright(knob.getScale());
//        }else{
//            lamp.off();
//            System.out.println("台灯没亮");
//        }
//    }
//}
