package valid;

import javax.validation.constraints.NotEmpty;

class NotEmptyConstraint {
    @NotEmpty
    String violateNull;

    @NotEmpty
    String violateEmpty;

    @NotEmpty
    String comply;
}
