package DS_source;

/**
 * Created by Alan on 15/11/8.
 * addNewCar problem in dataStructurePractice.pdf
 * test1_1:
 * 构造一个非递减数列,然后在该数列中新加入一个数,并保持该数列非递减有序的特性。
 */
public class Sqlist {
    private int[] ints;
    private int size, now;

    /**
     * generate a Sqlist with size element
     *
     * @param size init a size Sqlist
     */

    public Sqlist(int size) {
        ints = new int[size];
        this.size = size - 1;
        now = -1;
    }

    /**
     * insert a int
     *
     * @param insertInt int to insert
     */
    public void insert(int insertInt) {
        if (isFull())
            newList();
        if (isEmpty()) {
            ints[0] = insertInt;
            ++now;
            return;
        }
        ++now;
        int pointer = now - 1;
        while (insertInt < ints[pointer]) {
            ints[pointer + 1] = ints[pointer];
            pointer--;
            if (pointer == 0) {
                break;
            }
        }
        ints[pointer + 1] = insertInt;
    }

    /**
     * method shows all elements in this sqlist
     *
     * @returR
     */
    public String show() {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i <= now; ++i) {
            stringBuilder.append("" + ints[i]);
        }
        return stringBuilder.toString();
    }

    public boolean isEmpty() {
        return now == -1;
    }

    public boolean isFull() {
        return now == size;
    }

    private void newList() {
        int[] temp = new int[size + 21];
        for (int i = 0; i <= size; ++i) {
            temp[i] = ints[i];
        }
        size += 20;
        ints = temp;
    }
}
