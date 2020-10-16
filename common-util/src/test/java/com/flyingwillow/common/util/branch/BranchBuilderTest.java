package com.flyingwillow.common.util.branch;

import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

class BranchBuilderTest {

    @Test
    void 无返回值排序_无else() {

        final String[] r = new String[1];
        BranchBuilder.nullReturnBuilder()
                .on(() -> 3>5)
                .then(() -> r[0] = "a")
                .on(() -> 6>5)
                .then(() -> r[0] = "b")
                .apply();
        assertThat(r[0]).isEqualTo("b");
    }

    @Test
    void 有返回值排序_无else() {

        Optional optional = new BranchBuilder<String>().on(() -> 3 > 5)
                .then(() -> "if")
                .otherwise(() -> "else")
                .get();
        assertThat(optional).isNotNull();
        assertThat(optional.get()).isEqualTo("else");
    }
}
