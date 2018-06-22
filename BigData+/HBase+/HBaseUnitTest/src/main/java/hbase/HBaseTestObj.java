package hbase;

/**
 * @author Aleksey Yablokov
 */
@SuppressWarnings("WeakerAccess")
class HBaseTestObj {
    private String rowKey;
    private String data1;
    private String data2;

    public String getRowKey() {
        return rowKey;
    }

    public void setRowKey(String rowKey) {
        this.rowKey = rowKey;
    }

    public String getData1() {
        return data1;
    }

    public void setData1(String data1) {
        this.data1 = data1;
    }

    public String getData2() {
        return data2;
    }

    public void setData2(String data2) {
        this.data2 = data2;
    }
}
