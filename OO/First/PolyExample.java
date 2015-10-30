/**用你熟悉的程序设计语言构造一个范例,使得其中出现了参
 *数多态、包含多态、过载多态和强制多态中的至少三种。
 */
/*堆栈文件展示了参数多态*/
//接下来演示其余几种多态,用子类调用除func1()方法以外，都是包含多态
class Parent{
    public void func1(){
        System.out.println("Parent.func1()");
    }
    //checkType()是过载多态的一种体现
    public void checkType(float x){
        System.out.println("识别了一个浮点型数据");
    }
    public void checkType(char x){
        System.out.println("识别了一个字符型数据");
    }
}
class Child extends Parent{
    public void func1(){
        System.out.println("Child.func1()");
    }
} 
public class PolyExample{
    public static void main(String[] args){
        Parent parent=new Child();
        //这里的func1方法，重写了父类方法，是另外的一种多态
        parent.func1();
        //这里用的int类型数据，在checkType中被系统编译时自动进行了强制多态
        int i=5;
        //另外这两种多态是子类用父类方法的包含多态
        parent.checkType(i);
        parent.checkType((char)i);
    }
}
