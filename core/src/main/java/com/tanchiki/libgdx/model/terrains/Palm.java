package com.tanchiki.libgdx.model.terrains;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.Align;
import com.tanchiki.libgdx.stage.GameStage;
import com.tanchiki.libgdx.util.ObjectVariables;
import com.tanchiki.libgdx.util.TextureLoader;

public class Palm extends Decorate {
    public Sprite s;
    public Sprite s2;
    protected TextureRegion[] t;
    private float a = ObjectVariables.size_block;
    private GameStage g = GameStage.getInstance();

    public Palm(float x, float y) {
        TextureRegion[][] r = TextureLoader.getInstance().getPalms();
        t = new TextureRegion[5];
        for (int i = 0; i < 5; i++) {
            t[i] = r[0][i];
        }

        s = new Sprite(t[MathUtils.random(0, 2)]);
        s2 = new Sprite(t[MathUtils.random(3, 4)]);
        setPosition(x, y, Align.center);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {

        super.draw(batch, parentAlpha);
        s.setSize(a * 8, a * 8);
        s.setCenter(getX(Align.center), getY(Align.center));
        s.draw(batch);

        s2.setSize(a * 8, a * 8);
        s2.setCenter(getX(Align.center) + a * 2, getY(Align.center) - a * 2);
        s2.draw(batch);

        setBounds(s.getX(), s.getY(), s.getWidth(), s.getHeight());
    }
}
