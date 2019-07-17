package com.syh.framework.design_patters.behavior_type.memo;

public class MemoManager {
    private Memento mMemento;

    public void setMemento(Memento memento) {
        mMemento = memento;
    }

    public Memento getMemento() {
        return mMemento;
    }
}
