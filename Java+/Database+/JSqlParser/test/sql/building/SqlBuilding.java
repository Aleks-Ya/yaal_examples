package sql.building;

import net.sf.jsqlparser.schema.Table;
import net.sf.jsqlparser.statement.select.Select;
import net.sf.jsqlparser.util.SelectUtils;
import org.junit.Test;

public class SqlBuilding {
    @Test
    public void select() {
        Select select = SelectUtils.buildSelectFromTable(new Table("mytable"));
    }
}