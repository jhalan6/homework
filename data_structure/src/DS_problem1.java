public class DS_problem1{
    public static void main(String[] args) {
        Sqlist list=new Sqlist(20); 
        list.show();
        list.insert(-1);
        list.show();
        list.insert(1);
        list.show();
        list.insert(2);
        list.show();
        list.insert(7);
        list.show();
        list.insert(40);
        list.show();

    }
}
class Sqlist{
    private int[] ints;
    private int size,now;
    public Sqlist(int i){
        ints=new int[i];
        for(int j=0;j<i/2;++j)
            ints[j]=3*j;
        size=i;
        now=i/2;
    }
    public void insert(int in){
      //  if(now==size)
        //    newlist();
        int pointer=now;
        while(in<ints[pointer-1]){
            ints[pointer]=ints[pointer-1];
            pointer--;
            if(pointer==0){
                break;
           }
        }
        ints[pointer]=in;
        ++now;
    }
    private void newlist(){
        int[] temp=new int[size*2];
        for(int i=0;i<size;++i){
            temp[i]=ints[i];
        }
        size*=2;
    }
    public void show(){
        for(int i=0;i<now;++i){
            System.out.println(ints[i]);
        }
    }
}
