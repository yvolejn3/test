package main.test;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Iterables;
import lombok.Getter;
import lombok.NonNull;

import java.util.List;
import java.util.Objects;

import static main.test.Condition.Operand.*;

@Getter
@SuppressWarnings({"unused", "WeakerAccess"})
public class Condition {

    public enum Operand {
        IS_NULL,
        IS_NOT_NULL,
        IS_EQUAL_TO,
        IS_NOT_EQUAL_TO,
        IS_LESS_THAN,
        IS_LESS_THAN_OR_EQUAL_TO,
        IS_GREATER_THAN,
        IS_GREATER_THAN_OR_EQUAL_TO,
        STARTS_WITH,
        ENDS_WITH,
        CONTAINS,
        IN,
        BETWEEN
    }

    private final String field;
    private final Operand operand;
    private final List<String> values;

    public Condition(@NonNull String field, @NonNull Operand operand) {
        this.field = field;
        this.operand = operand;
        this.values = ImmutableList.of();
    }

    public Condition(@NonNull String field, @NonNull Operand operand, @NonNull String value) {
        this(field, operand, ImmutableList.of(value));
    }

    public Condition(@NonNull String field, @NonNull Operand operand, @NonNull List<String> values) {
        values = ImmutableList.copyOf(Iterables.filter(values, Objects::nonNull));
        if (values.isEmpty())
            throw new IllegalStateException("values list must not be empty");
        this.field = field;
        this.operand = operand;
        this.values = values;

    }

    public static Builder field(String field) {
        return new Builder(field);
    }

    public static class Builder {
        private String field;

        private Builder(String field) {
            this.field = field;
        }

        public Condition isNull() {
            return new Condition(field, IS_NULL);
        }

        public Condition isNotNull() {
            return new Condition(field, IS_NOT_NULL);
        }

        public Condition isEqualTo(String value) {
            return new Condition(field, IS_EQUAL_TO, value);
        }

        public Condition isNotEqualTo(String value) {
            return new Condition(field, IS_NOT_EQUAL_TO, value);
        }

        public Condition isLessThan(String value) {
            return new Condition(field, IS_LESS_THAN, value);
        }

        public Condition isLessThanOrEqualTo(String value) {
            return new Condition(field, IS_LESS_THAN_OR_EQUAL_TO, value);
        }

        public Condition isGreaterThan(String value) {
            return new Condition(field, IS_GREATER_THAN, value);
        }

        public Condition isGreaterThanOrEqualTo(String value) {
            return new Condition(field, IS_GREATER_THAN_OR_EQUAL_TO, value);
        }

        public Condition startsWith(String value) {
            return new Condition(field, STARTS_WITH, value);
        }

        public Condition endsWith(String value) {
            return new Condition(field, ENDS_WITH, value);
        }

        public Condition contains(String value) {
            return new Condition(field, CONTAINS, value);
        }

        public Condition in(List<String> values) {
            return new Condition(field, IN, values);
        }

        public Condition between(List<String> values) {
            return new Condition(field, BETWEEN, values);
        }

    }

}
