public class Parking{
    public static void main(String[] args){
        Park park=new Park(2);    
        park.function(Park.func.A,"1",5);
        park.function(Park.func.A,"2",10);
        park.function(Park.func.D,"1",15);
        park.function(Park.func.A,"3",20);
        park.function(Park.func.A,"4",25);
        park.function(Park.func.A,"5",30);
        park.function(Park.func.D,"2",35);
        park.function(Park.func.D,"4",40);
        park.function(Park.func.E,"4",40);
    }
}
class Message{
    private int time;
    private String num;
    public Message(int time,String num){
        this.time=time;
        this.num=num;
    }
    public int getTime(){
        return time;
    }
    public String getNum(){
        return num;
    }
}
class Park{
    private int n;
    private Stack<Message> park;
    private Stack<String> temp;
    private Stack<String> result;
    public static enum func{
        A,D,E
    }
    public Park(int n){
        this.n=n;    
        park=new Stack<Message>(n);
        temp=new Stack<String>(100);
        result=new Stack<String>(100);
        result.push("结果显示完毕");
    }
    public int getMax(){
        return n;
    }
    public void function(func f,String num,int time){
       switch(f){
           case A:
                if(!park.isFull()){
                    park.push(new Message(time,num));
                }else{
                    temp.push(num);
                }
               break;
           case D:
               Stack<Message> tpark=new Stack<Message>(n);
               boolean sign=false;
               while(!park.isEmpty()){
                    tpark.push(park.pop());
               }
               while(!tpark.isEmpty()){
                    if(tpark.checkTop().getNum().equals(num)){
                        String s=num+"车离开，停车费用："+(time-tpark.checkTop().getTime()); 
                        tpark.pop();
                        System.out.println(s);
                        result.push(s);
                        sign=true;
                    }else
                        park.push(tpark.pop());
               }
               if(sign==false){
                   Stack<String> tTemp=new Stack<String>(100);
                   while(!temp.isEmpty()){
                       tTemp.push(temp.pop());
                   }
                   while(!tTemp.isEmpty()){
                       if(tTemp.checkTop().equals(num)){
                           String s=num+"车离开，停车费用："+0;
                           System.out.println(s);
                           result.push(s);
                           tTemp.pop();
                        }
                       else
                           temp.push(tTemp.pop());
               }
       } 
               else if(temp.isEmpty())
                   return;
               else{
                   Stack<String> tTemp=new Stack<String>(100);
                   while(!temp.isEmpty()){
                       tTemp.push(temp.pop());
                   }
                   System.out.println("时间："+time+"车辆："+tTemp.checkTop()+"进入停车场");
                   park.push(new Message(time,tTemp.pop()));
                   while(!tTemp.isEmpty()){
                       temp.push(tTemp.pop());
                   }
               }
               break;
           case E:
                while(!park.isEmpty()){
                    function(func.D,park.checkTop().getNum(),time);
                }
                result.push("全程收费结果如下：");
                while(!result.isEmpty()){
                    System.out.println(result.pop());
                }
                return; 
    }
}
}
