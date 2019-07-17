package com.syh.framework.design_patters.structure_type.proxy;

public class Agent implements Actor {
    private Actor star;

    public Agent(Actor star) {
        this.star = star;
    }

    @Override
    public void movie() {
        System.out.println(getClass().getSimpleName() + "：剧本很好，这部电影接下了");
        star.movie();
    }
}
