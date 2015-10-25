/**多项式加减法（练习1——题目4）
*/
public class Polynomial{
    List list;
    public static void main(String[] args){
        double[] ints1=new double[]{2,1,5,8,-3.1,11};
        double[] ints2=new double[]{7,0,-5,8,11,9};
        PolynomialData list1=new PolynomialData(ints1);
        PolynomialData list2=new PolynomialData(ints2);
        list1.show(); 
        list2.show(); 
        PolynomialData.polyAdd(list1,list2).show();
        PolynomialData.polySub(list1,list2).show();
    }
}
class PolynomialData{
    DataCell listHead;
    public PolynomialData(double[] ints){
        listHead=new DataCell(0,0,null); 
        for(int i=0;i<ints.length/2;++i){
            listHead.next=new DataCell(ints[2*i],ints[2*i+1],listHead.next);
        }
        this.reverse();
    }
    public static PolynomialData polyAdd(PolynomialData list1,PolynomialData list2){
        PolynomialData result=new PolynomialData(new double[0]);
        DataCell l1,l2;
        l1=list1.listHead.next;
        l2=list2.listHead.next;
        while(l1!=null&&l2!=null){
            if(l1.you==l2.you){
                result.listHead.next=new DataCell(l2.zuo+l1.zuo,l1.you,result.listHead.next);
                l1=l1.next;
                l2=l2.next;
            }
            else if(l1.you>l2.you){
                result.listHead.next=new DataCell(l2.zuo,l2.you,result.listHead.next);    
                l2=l2.next;
            }else{
                result.listHead.next=new DataCell(l1.zuo,l1.you,result.listHead.next);    
                l1=l1.next;
            }
        }
        l1=l1==null?l2:l1;
        while(l1!=null){
            result.listHead.next=new DataCell(l1.zuo,l1.you,result.listHead.next);
            l1=l1.next;
        }
        result.reverse();
        return result;
    }
    public static PolynomialData polySub(PolynomialData list1,PolynomialData list2){
        PolynomialData result=new PolynomialData(new double[0]);
        DataCell l1,l2;
        l1=list1.listHead.next;
        l2=list2.listHead.next;
        while(l1!=null&&l2!=null){
            if(l1.you==l2.you){
                result.listHead.next=new DataCell(l1.zuo-l2.zuo,l1.you,result.listHead.next);
                l1=l1.next;
                l2=l2.next;
            }
            else if(l1.you>l2.you){
                result.listHead.next=new DataCell(-1*l2.zuo,l2.you,result.listHead.next);    
                l2=l2.next;
            }else{
                result.listHead.next=new DataCell(l1.zuo,l1.you,result.listHead.next);    
                l1=l1.next;
            }
        }
        l1=l1==null?l2:l1;
        while(l1!=null){
            result.listHead.next=new DataCell(l1.zuo,l1.you,result.listHead.next);
            l1=l1.next;
        }
        result.reverse();
        return result;
        }
    public void reverse(){
        DataCell temp2,temp=new DataCell(0,0,null);
        while(listHead.next!=null){
            temp2=listHead.next;
            listHead.next=temp2.next;
            temp2.next=temp.next;
            temp.next=temp2;
        }
        listHead=temp;
    }
    public void show(){
        DataCell temp=listHead;
        while(temp!=null){
            System.out.print(temp);
            if((temp.next!=null)&&(temp.next.zuo>0)&&(!temp.toString().isEmpty())){
                System.out.print("+");
            }
            temp=temp.next;
        }
        System.out.println();
    }
}
class DataCell{
    double zuo,you;
    DataCell next;
    public DataCell(double zuo,double you,DataCell next){
        this.zuo=zuo;
        this.you=you;
        this.next=next;
    }
    public static void printRight(DataCell begin){
        while(begin!=null){
            System.out.println("zuo:"+begin.zuo+"........you:"+begin.you);
            begin=begin.next;
        } 
        
    }
    public String toString(){
        if(zuo==0){
            return "";
        } else if(zuo==1){
            if(you==1){
                return "X";
            }
            return "X^"+(int)you;
        }
        else if(you==0)
            return ""+zuo;
        else
            return""+zuo+"X^"+(int)you;
    }
}
