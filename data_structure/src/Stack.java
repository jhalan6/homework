public class Stack<T>{
    Object[] items;
    private int top;
    private int n;
    public <T>Stack(int n){
        items=new Object[n];
        top=-1;
        this.n=n;
    }
    public boolean isEmpty(){
       return top==-1; 
    }
    public boolean isFull(){
       return top==n-1; 
    }
    public int getTop(){
        return top;
    }
    public boolean push(T t){
        if(isFull())
            return false;
        ++top;
        items[top]=t;
        return true;
    }
    @SuppressWarnings("unchecked")
    public T pop(){
        if(!isEmpty()){
                return (T)items[top--];
        }
        return null;
    }
    @SuppressWarnings("unchecked")
    public T checkTop(){
        if(!isEmpty()){
                return (T)items[top];
        }
        return null;
    }
    public static void main(String[] args){
        Stack<String> stack=new Stack<String>(100);
        stack.getTop();
        stack.isEmpty();
        stack.push("hello world");
        System.out.println(stack.pop());
        System.out.println(stack.pop());
    }
}
