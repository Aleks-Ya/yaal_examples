package assertj;

import org.assertj.core.api.Condition;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.allOf;
import static org.assertj.core.api.Assertions.anyOf;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.Assertions.not;

class ConditionTest {
    private static final Condition<Member> SENIOR_CONDITION = new Condition<>(m -> m.getAge() >= 60, "senior");
    private static final Condition<Member> NAME_JOHN_CONDITION = new Condition<>(
            m -> m.getName().equalsIgnoreCase("John"), "name John");

    @Test
    void scalar() {
        var member = new Member("John", 65);
        assertThat(member).is(SENIOR_CONDITION);
        assertThatThrownBy(() -> assertThat(member).isNot(SENIOR_CONDITION)).isInstanceOf(AssertionError.class);

        var member2 = new Member("Jane", 60);
        assertThat(member2).doesNotHave(NAME_JOHN_CONDITION);
        assertThatThrownBy(() -> assertThat(member2).has(NAME_JOHN_CONDITION)).isInstanceOf(AssertionError.class);
    }

    @Test
    void collections() {
        List<Member> members = new ArrayList<>();
        members.add(new Member("Alice", 50));
        members.add(new Member("Bob", 60));

        assertThat(members).haveExactly(1, SENIOR_CONDITION);
        assertThat(members).doNotHave(NAME_JOHN_CONDITION);
    }

    @Test
    void combining() {
        var john = new Member("John", 60);
        var jane = new Member("Jane", 50);

        assertThat(john).is(allOf(SENIOR_CONDITION, NAME_JOHN_CONDITION));
        assertThat(jane).is(allOf(not(NAME_JOHN_CONDITION), not(SENIOR_CONDITION)));

        assertThat(john).is(anyOf(SENIOR_CONDITION, NAME_JOHN_CONDITION));
        assertThatThrownBy(() -> assertThat(jane).is(anyOf(NAME_JOHN_CONDITION, SENIOR_CONDITION))).isInstanceOf(AssertionError.class);
    }

    private static class Member {
        private final String name;
        private final int age;

        public Member(String name, int age) {
            this.name = name;
            this.age = age;
        }

        public String getName() {
            return name;
        }

        public int getAge() {
            return age;
        }

        @Override
        public String toString() {
            return "Member{" +
                    "name='" + name + '\'' +
                    ", age=" + age +
                    '}';
        }
    }
}
