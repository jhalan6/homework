public class List{
    private boolean isDouble;
    private Node current,head,rear,temp;
    class Node{
        Node prior,next;
        Object data;
        Node(Object data,Node prior,Node next){
            isDouble=true;
            this.prior=prior;
            this.next=next;
            this.data=data; 
        }
        Node(Object data,Node next){
            isDouble=false;
            this.data=data; 
            this.next=next;
        }
    }
    public List(Object o,boolean isDouble){
        this.isDouble=isDouble;
            head=new Node(null,null);
        if(isDouble)
            head.next=new Node(o,head,null);
        else
            head.next=new Node(o,null);
            current=head.next;
        reset();
    }
    public boolean deleteObject(Object o){
        if(searchObject(o)){
            if(isDouble){
                temp.next=current.next;
                current.next.prior=temp;
            }
            else{
                temp.next=current.next;
            }
        }
        return false;
    }
    
    public boolean insertAfterObject(Object o,Object toInsert){
        if(searchObject(o)) {
               if(isDouble()) {
                       if(current.next!=null) {
                           current.next.prior=new Node(toInsert,current,current.next);
                           current.next=current.next.prior;
                       }else 
                           current.next=new Node(toInsert,current,current.next);
               }
               else
                   current.next=new Node(toInsert,current.next); 
               return true;
        }
        else
            return false;
    }
    public boolean insertbeforObject(Object o,Object toInsert){
        if(searchObject(o)) {
               if(isDouble()) {
                    current.prior=new Node(toInsert,current.prior,current);
                    current.prior.prior=current.prior;
               }
               else
                   current.next=new Node(toInsert,current.next); 
               return true;
        }
        else
            return false;
    }
    public boolean searchObject(Object o){
        reset();
        while(current.data!=o&&current!=null){
            temp=current;
            current=current.next;
        } 
        return current!=null;
    }
    public void reset(){
        temp=head;
        current=head.next;
    }
    public Object toPrior(){
        if(isDouble()){
            if(current!=null&&current!=head.next){
                current=current.prior;
                return current;
            }
        }
        if(current==head.next)
            return current;
        else if(current!=null)
            searchObject(current.data);
        else
            searchObject(null);
        current=temp;
        return temp;
    }
    public Object getCurrent(){
        if(current==null)
            return null;
        return current.data;
    }
    public Object toNext(){
        if(current!=null)    
            current=current.next;
        return getCurrent(); 
    }
    public Object getNext(){
        if(current==null||current.next==null)
            return null;
        return current.next.data; 
    }
    public boolean isDouble(){
        return isDouble;
    }
    public void print(){
        reset();
        while(current!=null) {
            System.out.println(""+current.data);
            toNext();
        }
    }
    public static void main(String[] args){
        List list= new List(2,false);
        list.insertAfterObject(2,3);
        list.insertAfterObject(2,13);
        list.insertAfterObject(2,33);
        list.insertAfterObject(2,36);
        list.print();
        list.reset();
        System.out.println(""+list.getCurrent());
        System.out.println(""+list.toNext());
        System.out.println(""+list.toNext());
        System.out.println(""+list.toNext());
        System.out.println(""+list.toNext());
        List list1= new List(2,true);
        list1.insertAfterObject(2,3);
        list1.insertAfterObject(2,13);
        list1.insertAfterObject(2,33);
        list1.insertAfterObject(2,36);
        list1.print();
        list1.reset();
        System.out.println(""+list1.getCurrent());
        System.out.println(""+list1.toNext());
        System.out.println(""+list1.toNext());
        System.out.println(""+list1.toNext());
         }
}
