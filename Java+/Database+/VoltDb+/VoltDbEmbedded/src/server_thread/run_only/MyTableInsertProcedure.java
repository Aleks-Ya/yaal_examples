package server_thread.run_only;

import org.voltdb.SQLStmt;
import org.voltdb.VoltProcedure;
import org.voltdb.VoltTable;

/**
 * Хранимая процедура для insert в таблицу my_table.
 */
public class MyTableInsertProcedure extends VoltProcedure {
    private static final SQLStmt sqlAdd
            = new SQLStmt("insert into my_table (id, number, text) values (?, ?, ?);");

    public VoltTable[] run(int id, int number, String text) throws VoltAbortException {
        voltQueueSQL(sqlAdd, EXPECT_ONE_ROW, id, number, text);
        return voltExecuteSQL();
    }
}