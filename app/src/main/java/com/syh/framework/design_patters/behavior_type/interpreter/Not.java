package com.syh.framework.design_patters.behavior_type.interpreter;

public class Not extends Expression {
    private Expression exp;
    public Not(Expression exp) {
        this.exp = exp;
    }
    @Override
    public boolean interpret(MyContext ctx) {
        return !exp.interpret(ctx);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj != null && obj instanceof Not) {
            return exp.equals(((Not)obj).exp);
        }
        return false;
    }

    @Override
    public int hashCode() {
        return this.toString().hashCode();
    }

    @Override
    public String toString() {
        return "(NOT " + exp.toString() + ")";
    }
}
