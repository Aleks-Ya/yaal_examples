package testclient;

import org.voltdb.ProcInfo;
import org.voltdb.SQLStmt;
import org.voltdb.VoltProcedure;
import org.voltdb.VoltTable;

//@ProcInfo (
//        partitionInfo = "t1.number: 0",
//        singlePartition = true
//)
public class TestProcedure extends VoltProcedure {
    public final SQLStmt
            sqlAdd = new SQLStmt("insert into t1 (id, number, text) values (?, ?, ?);");

    public VoltTable[] run(int id, int number, String text)
            throws VoltAbortException {
        voltQueueSQL(sqlAdd, id, number, text);
        voltExecuteSQL();
        return null;
    }
}