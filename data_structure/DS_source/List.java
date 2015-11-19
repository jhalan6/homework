package DS_source;

/**
 * addNewCar data structure could be initial as single linked list or a double linked list
 * This list is implements by use force cast instead of use pattern
 * It's a way to implement but may not the best way
 */
public class List{
    private boolean isDouble;
    private Node current,head,rear,temp;

    /**
     * Node is the data cell in this data structure
     */
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

    /**
     * initial list with first element.
     * All kind of class is ok.
     * @param element
     * @param isDouble true to initial a double linked list or false to initial a single linked list
     */
    public List(Object element,boolean isDouble){
        this.isDouble=isDouble;
            head=new Node(null,null);
        if(isDouble)
            head.next=new Node(element,head,null);
        else
            head.next=new Node(element,null);
            current=head.next;
        reset();
    }

    /**
     * delete a element in this list with the object
     * @param toDelete
     * @return true to show the delete is complete,false to show there is no this element to delete
     */
    public boolean deleteObject(Object toDelete){
        if(searchObject(toDelete)){
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

    /**
     * Insert an element by the element before it
     * @param beforeInsert
     * @param toInsert
     * @return
     */
    public boolean insertAfterObject(Object beforeInsert,Object toInsert){
        if(searchObject(beforeInsert)) {
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

    /**
     *
     * @param afterInsert
     * @param toInsert
     * @return
     */
    public boolean insertBeforObject(Object afterInsert, Object toInsert){
        if(searchObject(afterInsert)) {
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

    /**
     * search whether the element is in this list
     * @param toSearch
     * @return
     */
    public boolean searchObject(Object toSearch){
        reset();
        while(current.data!=toSearch&&current!=null){
            temp=current;
            current=current.next;
        } 
        return current!=null;
    }

    /**
     * reset inside iterator
     */
    public void reset(){
        temp=head;
        current=head.next;
    }

    /**
     * Move inside iterator to the object before current one
     * If now is at the first element,return itself
     * @return prior element
     */
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

    /**
     *
     * @return element which inside iterator point at
     */
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

    /**
     * test whether this class is right
     * @param args
     */
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
