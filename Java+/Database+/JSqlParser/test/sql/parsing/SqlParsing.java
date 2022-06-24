package sql.parsing;

import net.sf.jsqlparser.JSQLParserException;
import net.sf.jsqlparser.expression.Expression;
import net.sf.jsqlparser.expression.LongValue;
import net.sf.jsqlparser.expression.operators.relational.ExpressionList;
import net.sf.jsqlparser.expression.operators.relational.InExpression;
import net.sf.jsqlparser.parser.CCJSqlParserUtil;
import net.sf.jsqlparser.schema.Column;
import net.sf.jsqlparser.statement.Statement;
import net.sf.jsqlparser.statement.select.AllColumns;
import net.sf.jsqlparser.statement.select.PlainSelect;
import net.sf.jsqlparser.statement.select.Select;
import net.sf.jsqlparser.statement.select.SelectBody;
import net.sf.jsqlparser.statement.select.SelectItem;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.stream.Collectors;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;

public class SqlParsing {
    @Test
    void palinSelect() throws JSQLParserException {
        Statement stmt = CCJSqlParserUtil.parse("SELECT * FROM tab1");
        Select select = (Select) stmt;

        SelectBody selectBody = select.getSelectBody();
        PlainSelect plainSelect = (PlainSelect) selectBody;

        List<SelectItem> selectItems = plainSelect.getSelectItems();
        assertThat(selectItems, hasSize(1));

        SelectItem selectItem = selectItems.get(0);
        AllColumns allColumns = (AllColumns) selectItem;
    }

    @Test
    void inExpression() throws JSQLParserException {
        Statement stmt = CCJSqlParserUtil.parse("SELECT * FROM tab1 WHERE col IN (1, 2, 3)");
        Select select = (Select) stmt;

        SelectBody selectBody = select.getSelectBody();
        PlainSelect plainSelect = (PlainSelect) selectBody;

        InExpression inExpression = (InExpression) plainSelect.getWhere();
        Column columnExpression = (Column) inExpression.getLeftExpression();
        String inColumnName = columnExpression.getColumnName();

        assertThat(inColumnName, equalTo("col"));

        ExpressionList rightItemsList = (ExpressionList) inExpression.getRightItemsList();
        List<Expression> expressions = rightItemsList.getExpressions();
        List<Long> longValues = expressions.stream()
                .map(expression -> ((LongValue) expression).getValue())
                .collect(Collectors.toList());

        assertThat(longValues, Matchers.contains(1L, 2L, 3L));
    }
}
