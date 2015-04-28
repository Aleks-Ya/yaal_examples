package server_thread;

import org.voltdb.SQLStmt;
import org.voltdb.VoltProcedure;
import org.voltdb.VoltTable;

//@ProcInfo (
//        partitionInfo = "my_table.number: 0",
//        singlePartition = true
//)
public class TestProcedure extends VoltProcedure {
    public final SQLStmt
            sqlAdd = new SQLStmt("insert into my_table (id, number, text) values (?, ?, ?);");

    public VoltTable[] run(int id, int number, String text)
            throws VoltAbortException {
        System.out.println("TestProcedure runned");
        voltQueueSQL(sqlAdd, EXPECT_ONE_ROW, id, number, text);
        voltExecuteSQL();
        System.out.println("TestProcedure finished");
        return null;
    }
}