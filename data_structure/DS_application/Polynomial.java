package DS_application;

import DS_source.List;

/**多项式加减法（练习1——题目4）
 * 【问题描述】
 *   设计一个一元稀疏多项式简单计算器。
 * 【基本要求】
 *   一元稀疏多项式简单计算器的基本功能:
 *   (1)输入并建立多项式的单向链表存储;
 *   (2)输出多项式,形式为一个整数序列:n,c ,e ,c ,e , ,c ,e ,其中,n是多项式的项数,c、e 1122nn ii
 *   分别是第 i 项的系数和指数,序列按指数降序排列; 6
 *   (3)多项式 A(x)和 B(x)相加得到多项式 C(x),输出 C(x);
 *   (4)多项式 A(x)和 B(x)相减得到多项式 D(x),输出 D(x)。
*/
public class Polynomial{
    public static void main(String[] args){
        double[] ints1=new double[]{2,1,5,8,-3.1,11};
        double[] ints2=new double[]{7,0,-5,8,11,9};
        PolynomialData list1=new PolynomialData(ints1);
        PolynomialData list2=new PolynomialData(ints2);
        System.out.println(list1);
        System.out.println(list2);
        System.out.println(PolynomialData.polyAdd(list1,list2));
        System.out.println(PolynomialData.polySub(list1,list2));
    }
}

/**
 * PolynomialData contains a whole Polynomial
 */
class PolynomialData{
    DataCell listHead;

    /**
     *
     * @param ints
     */
    public PolynomialData(double[] ints){
        listHead=new DataCell(0,0,null);
        for(int i=0;i<ints.length/2;++i){
            listHead.next=new DataCell(ints[2*i],ints[2*i+1],listHead.next);
        }
        this.reverse();
    }

    /**
     * Add two Polynomial to one
     * @param Polynomial1
     * @param Polynomial2
     * @return
     */
    public static PolynomialData polyAdd(PolynomialData Polynomial1,PolynomialData Polynomial2){
        PolynomialData result=new PolynomialData(new double[0]);
        DataCell l1,l2;
        l1=Polynomial1.listHead.next;
        l2=Polynomial2.listHead.next;
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

    /**
     *
     * @param polynomial1
     * @param polynomial2
     * @return
     */
    public static PolynomialData polySub(PolynomialData polynomial1,PolynomialData polynomial2){
        PolynomialData result=new PolynomialData(new double[0]);
        DataCell l1,l2;
        l1=polynomial1.listHead.next;
        l2=polynomial2.listHead.next;
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
    public String toString(){
        DataCell temp=listHead;
        StringBuilder stringBuilder=new StringBuilder();
        while(temp!=null){
            stringBuilder.append(temp);
            if((temp.next!=null)&&(temp.next.zuo>0)&&(!temp.toString().isEmpty())){
                stringBuilder.append("+");
            }
            temp=temp.next;
        }
        return stringBuilder.toString();
    }
}
//DataCell is basic store unit
class DataCell{
    double zuo,you;//zuo是系数,you是指数
    DataCell next;
    public DataCell(double zuo, double you, DataCell next){
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
