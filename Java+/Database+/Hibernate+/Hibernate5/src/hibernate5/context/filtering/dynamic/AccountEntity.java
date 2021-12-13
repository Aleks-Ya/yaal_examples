package hibernate5.context.filtering.dynamic;

import org.hibernate.annotations.Filter;
import org.hibernate.annotations.FilterDef;
import org.hibernate.annotations.ParamDef;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.math.BigDecimal;

import static hibernate5.context.filtering.dynamic.AccountEntity.DELETED_ACCOUNT_FILTER;
import static hibernate5.context.filtering.dynamic.AccountEntity.DELETED_STATUS_PARAM;

@Entity
@FilterDef(name = DELETED_ACCOUNT_FILTER,
        parameters = @ParamDef(name = DELETED_STATUS_PARAM, type = "boolean"))
@Filter(name = DELETED_ACCOUNT_FILTER, condition = "deleted = :" + DELETED_STATUS_PARAM)
class AccountEntity {
    public static final String DELETED_ACCOUNT_FILTER = "deletedAccountFilter";
    public static final String DELETED_STATUS_PARAM = "deletedStatus";

    @Id
    @GeneratedValue
    private Long id;

    private String owner;

    private BigDecimal balance;

    private Boolean deleted;

    public AccountEntity() {
    }

    public AccountEntity(String owner, BigDecimal balance, boolean deleted) {
        this.owner = owner;
        this.balance = balance;
        this.deleted = deleted;
    }

    @Override
    public String toString() {
        return String.format("Account[id=%d, owner=%s, balance=%f, deleted=%b]", id, owner, balance, deleted);
    }
}
