package com.flyingwillow.common.util.branch;

import java.util.function.BooleanSupplier;
import java.util.function.Predicate;
import java.util.function.Supplier;

public class BranchCondition<T> {
    private BooleanSupplier condition;
    private Predicate predicate;
    private Supplier<T> supplier;
    private BranchBuilder<T> builder;

    public BranchCondition(BooleanSupplier condition, BranchBuilder<T> builder) {
        this.condition = condition;
        this.builder = builder;
    }

    protected Boolean condition() {
        return condition.getAsBoolean();
    }

    public BranchBuilder<T> then(Supplier<T> supplier) {
        this.supplier = supplier;
        return this.builder;
    }

    public BranchBuilder<T> then(VoidSupplier supplier) {
        this.supplier = (Supplier<T>) supplier;
        return this.builder;
    }

    protected T get() {
        return this.supplier.get();
    }
}
