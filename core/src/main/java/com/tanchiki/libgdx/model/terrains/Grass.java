package com.tanchiki.libgdx.model.terrains;

import com.badlogic.gdx.math.MathUtils;

public class Grass extends Terrain {
    public Grass(float x, float y) {
        super(x, y);
        s.setRegion(t[MathUtils.random(0, 5)]);
    }
}
