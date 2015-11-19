package DS_source;

/**
 * 实现了一个接收范型的栈数据结构,同时定义了一系列栈操作
 *
 * @param <T> 接受该堆栈所能操作的数据类型
 */
public class Stack<T> {
    private Object[] items;
    private int top;
    private int n;

    /**
     * @param size provide a way to initial a stack with the size of stack
     * @param <T>  T shows the class type in stack
     */
    public <T> Stack(int size) {
        items = new Object[size];
        top = -1;
        this.n = size;
    }

    public boolean isEmpty() {
        return top == -1;
    }

    public boolean isFull() {
        return top == n - 1;
    }

    /**
     * @return return the top number of the stack without pop
     */
    public int getTop() {
        return top;
    }

    /**
     * push a T into the stack
     *
     * @return return true if t has been pushed in the stack return false if not
     */
    public boolean push(T t) {
        if (isFull())
            return false;
        ++top;
        items[top] = t;
        return true;
    }

    /**
     * get the element on the top of stack with pop it
     *
     * @return the top element on the stack when there has,and return null when the stack is empty
     */
    @SuppressWarnings("unchecked")
    public T pop() {
        if (!isEmpty()) {
            return (T) items[top--];
        }
        return null;
    }

    /**
     * get the element on the top of the stack without pop it
     *
     * @return the top element on the stack when there has,and return null when the stack is empty
     * @SuppressWarnings("unchecked")
     */
    public T checkTop() {
        if (!isEmpty()) {
            return (T) items[top];
        }
        return null;
    }

}
