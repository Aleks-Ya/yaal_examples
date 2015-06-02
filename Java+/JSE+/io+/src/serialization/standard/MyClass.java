package serialization.standard;

import java.io.Serializable;

public class MyClass implements Serializable {
    private int num = 1;

    private transient long transientLong = 2;

    public void setNum(int num) {
        this.num = num;
    }

    public int getNum() {
        return num;
    }

    public long getTransientLong() {
        return transientLong;
    }

    public void setTransientLong(long transientLong) {
        this.transientLong = transientLong;
    }
}
