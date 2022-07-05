package sql.parsing;

import net.sf.jsqlparser.JSQLParserException;
import net.sf.jsqlparser.expression.LongValue;
import net.sf.jsqlparser.expression.operators.relational.ExpressionList;
import net.sf.jsqlparser.expression.operators.relational.InExpression;
import net.sf.jsqlparser.parser.CCJSqlParserUtil;
import net.sf.jsqlparser.schema.Column;
import net.sf.jsqlparser.statement.select.AllColumns;
import net.sf.jsqlparser.statement.select.PlainSelect;
import net.sf.jsqlparser.statement.select.Select;
import org.junit.jupiter.api.Test;

import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

class SqlParsingTest {
    @Test
    void palinSelect() throws JSQLParserException {
        var stmt = CCJSqlParserUtil.parse("SELECT * FROM tab1");
        var select = (Select) stmt;

        var selectBody = select.getSelectBody();
        var plainSelect = (PlainSelect) selectBody;

        var selectItems = plainSelect.getSelectItems();
        assertThat(selectItems).hasSize(1);

        var selectItem = selectItems.get(0);
        var allColumns = (AllColumns) selectItem;
    }

    @Test
    void inExpression() throws JSQLParserException {
        var stmt = CCJSqlParserUtil.parse("SELECT * FROM tab1 WHERE col IN (1, 2, 3)");
        var select = (Select) stmt;

        var selectBody = select.getSelectBody();
        var plainSelect = (PlainSelect) selectBody;

        var inExpression = (InExpression) plainSelect.getWhere();
        var columnExpression = (Column) inExpression.getLeftExpression();
        var inColumnName = columnExpression.getColumnName();

        assertThat(inColumnName).isEqualTo("col");

        var rightItemsList = (ExpressionList) inExpression.getRightItemsList();
        var expressions = rightItemsList.getExpressions();
        var longValues = expressions.stream()
                .map(expression -> ((LongValue) expression).getValue())
                .collect(Collectors.toList());

        assertThat(longValues).contains(1L, 2L, 3L);
    }
}
