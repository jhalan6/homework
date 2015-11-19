package DS_application;

/**实验一题目三
 * 编号为 1,2,...,n 的 n 个人按顺时针方向围坐一圈,每人持有一个密码(正整数)。
 * 现在给定一个随 机数 m>0,从编号为 1 的人开始,
 * 按顺时针方向 1 开始顺序报数,报到 m 时停止。
 * 报 m 的人出圈, 同时留下他的密码作为新的 m 值,
 * 从他在顺时针方向上的下一个人开始,重新从 1 开始报数,
 * 如此下 去,直至所有的人全部出列为止。
*/
public class Joseph{
    public static void main(String[] args){
        new JosephCircle(new int[]{ 20,3,1,7,2,4,8,4, });
    }
}

/**
 * addNewCar joseph init by a Array
 */
class JosephCircle{
    int unOut,key,current;
    public JosephCircle(int[] init){
        unOut=init.length-1;
        run(init);
    }
    private void run(int[] init){
        key=init[0];
        current=1;
        init[0]=-1;
        while(unOut>1){
            key%=unOut;
            while(key>1){
                if(init[current]==-1){
                    current=(current+1)%init.length; 
                }else{
                    current=(current+1)%init.length; 
                    --key; 
                }
            }
            while(init[current]==-1){
                current=(current+1)%init.length;
            }
            key=init[current];
            init[current]=-1;
            --unOut;
            System.out.println(""+current+"out!.........."+unOut+"unOut");
        }
    }
    //a private to show all element in the array
//    private void check(int[] is){
//        for(int i:is){
//            System.out.print(""+i+",");
//        }
//    }
}
