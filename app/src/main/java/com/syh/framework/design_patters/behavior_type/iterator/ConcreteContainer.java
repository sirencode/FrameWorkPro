package com.syh.framework.design_patters.behavior_type.iterator;

import java.util.List;

public class ConcreteContainer implements Container {
    private List<Object> list;

    public ConcreteContainer(List<Object> list) {
        this.list = list;
    }

    @Override
    public void add(Object obj) {
        list.add(obj);
    }

    @Override
    public void remove(Object obj) {
        list.remove(obj);
    }

    @Override
    public Iterator createIterator() {
        return new ConcreteIterator();
    }

    //具体迭代器角色（Concrete Iterator）
    class ConcreteIterator implements Iterator {
        private int cursor;

        public ConcreteIterator() {
        }

        @Override
        public Object first() {
            cursor = 0;
            return list.get(cursor);
        }

        @Override
        public Object next() {
            Object ret = null;
            if (hasNext()) {
                ret = list.get(cursor);
            }
            cursor++;
            return ret;
        }

        @Override
        public boolean hasNext() {
            return !(cursor == list.size());
        }

        @Override
        public Object currentItem() {
            return list.get(cursor);
        }
    }
}
