package DS_application;

/**
*上机实验手册实验一题目一／题目二
*/
public class Test1_1{
    /**
     * test this answer whether it's right
     * @param args
     */
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

class tDataCell {
    int data;
    tDataCell next;
    tDataCell(int data, tDataCell next){
        this.data=data;
        this.next=next;
    }
}
class List{
    tDataCell head;

    /**
     * constructor to initial a list with no element in it
     */
    public List(){
        head=new tDataCell(0,null);
    }
    public void insert(int place,int obj){
        tDataCell tempPrior,tempRear;
        tempPrior=head;
        tempRear=head.next;
        while(place>1&&tempRear!=null){
            tempPrior=tempPrior.next;
            tempRear=tempRear.next;
            --place;
        }
        tempPrior.next=new tDataCell(obj,tempRear);
    }

    /**
     * delete a data at place
     * @param place location of data to delete
     * @return return whether the data is deleted return true.
     */
    public boolean deleteData(int place){
        tDataCell tempPrior,tempCurrent;
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

    /**
     * addNewCar method to check the list
     * Used to test or show the result
     * @return what is show in console use to do unit test
     */
    public String show(){
        tDataCell temp=head;
        StringBuilder stringBuilder=new StringBuilder();
        if(temp.next==null){
            System.out.println("empty list!");
            stringBuilder.append("empty list!");
        }
        while(temp.next!=null){
            temp=temp.next;
            stringBuilder.append(temp.data);
            System.out.println(temp.data);
        }
        return stringBuilder.toString();
    }

    /**
     * sort elements in list
     */
    public void sort(){
        tDataCell temp=head;
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

    /**
     * exchange two elements after the argument
     * @param beforeTwo the element before two number
     */
    public void exchange(tDataCell beforeTwo){
        tDataCell first,second;
        first=beforeTwo.next;
        second=beforeTwo.next.next;
        beforeTwo.next=second;
        first.next=second.next;
        second.next=first;
    }
}
