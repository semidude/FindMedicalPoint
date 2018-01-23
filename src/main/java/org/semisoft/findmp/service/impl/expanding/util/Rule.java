package org.semisoft.findmp.service.impl.expanding.util;

import java.util.function.Consumer;
import java.util.function.Predicate;

public class Rule<T> {
    private Predicate<T> when;
    private Consumer<T> then;

    public Rule(Predicate<T> when, Consumer<T> then) {
        this.when = when;
        this.then = then;
    }

    public boolean checkCondition(T testedObject) {
        return when.test(testedObject);
    }

    void callFunction(T testedObject) {
        then.accept(testedObject);
    }
}
