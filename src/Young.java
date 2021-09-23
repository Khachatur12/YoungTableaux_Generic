import java.util.Comparator;

public class Young<T> {
    private Object[][] table;
    private int size;
    private final int max_size;
    private int col;
    private int row;
    private Comparator<T> comparator = null;

    private static final Object MIN_VALUE = new Object();
    private static final Object MAX_VALUE = new Object();


    public Young() {
        this(10);
    }

    public Young(int table_size) {
        this.size = 0;
        this.max_size = table_size * table_size;
        this.table = new Object[table_size][table_size];
        this.col = 0;
        this.row = 0;

        for (int i = 0; i < table_size; i++) {
            for (int j = 0; j < table_size; j++) {
                table[i][j] = MAX_VALUE;
            }
        }
    }

    public Young(int table_size, Comparator<T> comparator){
        this(table_size);
        this.comparator = comparator;
    }


//    -----------------------------------------------------------------------------

    public boolean empty() {
        return size == 0;
    }

    public boolean full() {
        return size == max_size;
    }

    public T top() {
        return (T) table[0][0];
    }

    public void pop() {
        table[0][0] = MAX_VALUE;
        moveRightDown(0, 0);

        if (col - 1 >= 0 && table[table.length - 1][col - 1] == MAX_VALUE) {
            col--;
        }
        if (row - 1 >= 0 && table[row - 1][table.length - 1] == MAX_VALUE) {
            row--;
        }
    }

    public void push(T elem) {
        int i, j;

        if (row > col) {
            i = table.length - 1;
            j = col;
        } else {
            i = row;
            j = table.length - 1;
        }

        table[i][j] = elem;
        size++;

        moveLeftUp(i, j);

        if (table[table.length - 1][col] != MAX_VALUE) {
            col++;
        }
        if (table[row][table.length - 1] != MAX_VALUE) {
            row++;
        }
    }

    //    -------------------------------------------------------------------------

    private int compare(Object e1, Object e2) {
        if (e1 == e2) {
            return 0;
        } else if (e1 == MAX_VALUE || e2 == MIN_VALUE) {
            return 1;
        } else if (e1 == MIN_VALUE || e2 == MAX_VALUE) {
            return -1;
        } else {
            if (comparator != null) {
                return comparator.compare((T) e1, (T) e2);
            }

            return ((Comparable<T>) e1).compareTo((T) e2);
        }
    }

    private void moveLeftUp(int i, int j) {
        Object elem = table[i][j];

        while (true) {
            Object leftElem = (j - 1 >= 0) ? table[i][j - 1] : MIN_VALUE;
            Object topElem = (i - 1 >= 0) ? table[i - 1][j] : MIN_VALUE;

            if (compare(elem, leftElem) >= 0 && compare(elem, topElem) >= 0) break;

            if (compare(elem, leftElem) < 0 && compare(leftElem, topElem) >= 0) {
                table[i][j] = table[i][j - 1];
                table[i][--j] = elem;
                continue;
            }

            if (compare(elem, topElem) < 0) {
                table[i][j] = table[i - 1][j];
                table[--i][j] = elem;
            }
        }
    }

    private void moveRightDown(int i, int j) {
        Object elem = table[i][j];
        Object rightElem;
        Object downElem;

        while (true) {
            rightElem = (j + 1 < table.length) ? table[i][j + 1] : MAX_VALUE;
            downElem = (i + 1 < table.length) ? table[i + 1][j] : MAX_VALUE;

            if (compare(elem, rightElem) <= 0 && compare(elem, downElem) <= 0) break;

            if (compare(elem, rightElem) > 0 && compare(rightElem, downElem) <= 0) {
                table[i][j] = table[i][j + 1];
                table[i][++j] = elem;
                continue;
            }

            if (compare(elem, downElem) > 0) {
                table[i][j] = table[i + 1][j];
                table[++i][j] = elem;
            }
        }
    }

//    -----------------------------------------------------------------------------


    public void setComparator(Comparator<T> comparator) {
        this.comparator = comparator;
    }

    public Comparator<T> getComparator() {
        return comparator;
    }

    @Override
    public String toString() {
        StringBuilder s = new StringBuilder();
        for (int i = 0; i < table.length; i++) {
            for (int j = 0; j < table.length; j++) {
                if (table[i][j] == MAX_VALUE)
                    s.append("inf\t");
                else
                    s.append(table[i][j] + "\t");
            }
            s.append("\n");
        }

        return s.toString();
    }
}
