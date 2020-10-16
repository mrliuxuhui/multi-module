package com.flyingwillow.common.util.branch;

import java.util.function.BooleanSupplier;
import java.util.function.Supplier;

public class BranchCondition<T> {
    private BooleanSupplier condition;
    private Supplier<T> supplier;
    private BranchBuilder builder;

    public BranchCondition(BooleanSupplier condition, BranchBuilder builder) {
        this.condition = condition;
        this.builder = builder;
    }

    protected Boolean condition() {
        return condition.getAsBoolean();
    }

    public BranchBuilder then(Supplier<T> supplier) {
        this.supplier = supplier;
        return this.builder;
    }

    public BranchBuilder then(VoidSupplier supplier) {
        this.supplier = (Supplier<T>) supplier;
        return this.builder;
    }

    protected T get() {
        return this.supplier.get();
    }
}
