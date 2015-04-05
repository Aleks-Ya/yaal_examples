import org.voltdb.SQLStmt;
import org.voltdb.VoltProcedure;
import org.voltdb.VoltTable;

public class TownsByState extends VoltProcedure {
    public final SQLStmt townsByState =
            new SQLStmt("SELECT * FROM towns WHERE state=?;");

    public VoltTable[] run(String state) throws VoltAbortException {
        voltQueueSQL(townsByState, state);
        return voltExecuteSQL();
    }
}
