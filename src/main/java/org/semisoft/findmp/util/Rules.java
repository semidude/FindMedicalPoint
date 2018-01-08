package org.semisoft.findmp.util;

import java.util.Arrays;
import java.util.List;

public class Rules<T> {
    private T testedObject;
    private List<Rule<T>> rules;

    @SafeVarargs
    public Rules(T testedObject, Rule<T>... rules) {
        this.testedObject = testedObject;
        this.rules = Arrays.asList(rules);
    }

    public void applyRules() {
        for (Rule<T> rule : rules) {
            if (rule.checkCondition(testedObject))
                rule.callFunction(testedObject);
        }
    }
}
