/**实验一题目三

*/public class Joseph{
    public static void main(String[] args){
        JosephCircle j=new JosephCircle(new int[]{20,3,1,7,2,4,8,4});
    }
}
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
    private void check(int[] is){
        for(int i:is){
            System.out.print(""+i+",");
        }
    }
}
