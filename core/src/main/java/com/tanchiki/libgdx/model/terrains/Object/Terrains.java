package com.tanchiki.libgdx.model.terrains.Object;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.tanchiki.libgdx.graphics.GameActor;
import com.tanchiki.libgdx.stage.GameStage;
import com.tanchiki.libgdx.util.ObjectClass;
import com.tanchiki.libgdx.util.ObjectVarable;

public class Terrains extends GameActor {
    public Sprite s;
    protected TextureRegion[] t;
    private float a = ObjectVarable.size_block;
    protected float size = a * 2;
    protected GameStage g = ObjectClass.GameStage;
    public Object last_block = null;
	public boolean modify = true;
	
    public Terrains(float x, float y) {
        t = ObjectClass.GameStage.TextureLoader.getTerrains()[0];
        s = new Sprite(t[0]);
        setSize(a * 2, a * 2);
        setCenterPosition(x, y);
        if (g.world_obj[(int) x][(int) y] != null)
            last_block = g.world_obj[(int) x][(int) y];
        g.world_obj[(int) x][(int) y] = this;
		s.setSize(a * 2, a * 2);
        s.setCenter(getCenterX(), getCenterY());
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        // TODO: Implement this method
        super.draw(batch, parentAlpha);
        s.draw(batch);
    }

    @Override
    protected void drawDebugBounds(ShapeRenderer shapes) {
        shapes.setColor(getColor());
        shapes.rect(getX(), getY(), getWidth(), getHeight());
        setColor(Color.GREEN);
    }
}
