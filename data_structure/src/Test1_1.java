/**
*上机实验手册实验一题目一／题目二
*/
public class Test1_1{
    public static void main(String[] args){
        List list=new List();
        list.insert(1,1);
        list.insert(1,2);
        list.insert(1,6);
        list.insert(1,4);
        list.insert(1,5);
        list.show();
        list.sort();
        list.show();
        list.deleteData(1);
        list.show();
        list.deleteData(1);
        list.show();
        list.deleteData(1);
        list.show();
        list.deleteData(1);
        list.show();
        list.deleteData(1);
        list.show();
        list.show();
    }
}
class DataCell{
    int data;
    DataCell next;
    DataCell(int data,DataCell next){
        this.data=data; 
        this.next=next;
    }
}
class List{
    DataCell head;
    public List(){
        head=new DataCell(0,null);
    }
    public void insert(int place,int obj){
        DataCell tempPrior,tempRear;
        tempPrior=head;
        tempRear=head.next;
        while(place>1&&tempRear!=null){
            tempPrior=tempPrior.next;
            tempRear=tempRear.next;
            --place;
        }
        tempPrior.next=new DataCell(obj,tempRear);
    }
    public boolean deleteData(int place){
        DataCell tempPrior,tempCurrent;
        tempPrior=head;
        tempCurrent=head.next;
        while(place>1&&tempCurrent.next!=null){
            tempPrior=tempPrior.next;
            tempCurrent=tempCurrent.next;
            --place;
        }
        if(place!=1){
            return false;
        }
        else{
            tempPrior.next=tempCurrent.next;
            return true;
        }
    }
    public void show(){
        DataCell temp=head;
        if(temp.next==null){
            System.out.println("empty list!");
        }
        while(temp.next!=null){
            temp=temp.next;
            System.out.println(temp.data);
        }
    }
    public void sort(){
        DataCell temp=head;
        boolean isChanged=true;
        while(isChanged){
            isChanged=false;
            while(temp.next!=null&&temp.next.next!=null){
                if(temp.next.next.data<temp.next.data){
                    System.out.println(temp.next.data+"....."+temp.next.next.data);
                    isChanged=true;
                    exchange(temp);
                    System.out.println(temp.next.data+"....."+temp.next.next.data);
                }
                    temp=temp.next;
            }  
            temp=head;
        }  
    }
    public void exchange(DataCell beforeTwo){
        DataCell first,second;
        first=beforeTwo.next;
        second=beforeTwo.next.next;
        beforeTwo.next=second;
        first.next=second.next;
        second.next=first;
    }
}
