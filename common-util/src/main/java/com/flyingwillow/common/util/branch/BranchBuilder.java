package com.flyingwillow.common.util.branch;

import java.util.Optional;
import java.util.function.BooleanSupplier;
import java.util.function.Supplier;

public class BranchBuilder<T> {

    private BranchCompose compose;

    public BranchBuilder() {
        this.compose = new BranchCompose<T>();
    }

    public static BranchBuilder<Void> nullReturnBuilder(){
        return new BranchBuilder<Void>();
    }

    public BranchCondition on(BooleanSupplier condition) {
        BranchCondition branchCondition = new BranchCondition(condition, this);
        this.compose.addCondition(branchCondition);
        return branchCondition;
    }

    public BranchCompose otherwise(Supplier<T> supplier) {
        return this.compose.setOtherwise(supplier);
    }

    public BranchCompose otherwise(VoidSupplier supplier){
        return this.compose.setOtherwise(supplier);
    }

    public void apply(){
        this.compose.apply();
    }

    public Optional<T> get(){
        return this.compose.get();
    }
}
