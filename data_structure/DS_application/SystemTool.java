package DS_application;

import DS_source.Stack;
/**
 * addNewCar tool to do system cast use Stack
 * test in dataStructurePractice.pdf
 * test2_1:
 * 写一个程序,利用栈将一个十进制数转换为二进制数的形式输出,其中,栈的基本运算应自行实 现,不能使用开发工具中提供的栈类型。
 */
public class SystemTool {
    public enum system{
        Hexadecimal,Binary,Decimal,Octonary; 
        public static int toInt(system to){
            int fun=10;
            switch(to){
                case Hexadecimal:
                    fun=16;
                    break;
                case Binary:
                    fun=2;
                    break;
                case Octonary:
                    fun=8;
                    break;
                case Decimal:
                    fun=10;
                    break;
            }
            return fun;
        }
    }
    /**
     * addNewCar castFromInt receive a number and cast it into a specific system
     * @param castTo a system enum defined in the beginning of this file
     * @param intNumber
     * @return Result in String
     */
    public static String castFromInt(system castTo, int intNumber){
        Stack<String> stack=new Stack<String>(100);
        int temp=system.toInt(castTo);
        while(intNumber!=0){
            stack.push(getNumber(intNumber%temp));
            intNumber/=temp;
        }
        StringBuffer s=new StringBuffer();
        while(!stack.isEmpty()){
            s.append(stack.pop());
        }
        return s.toString();   
    }
    private static String getNumber(int i){
        if(i>=0&&i<=9)
            return ""+i;
        else 
            return ""+(char)((int)'A'+i-10);
    }
}
