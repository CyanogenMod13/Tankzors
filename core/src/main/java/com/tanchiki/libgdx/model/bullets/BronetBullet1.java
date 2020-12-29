package com.tanchiki.libgdx.model.bullets;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;
import com.tanchiki.libgdx.model.bullets.Object.Bullet;
import com.tanchiki.libgdx.util.ObjectClass;
import com.tanchiki.libgdx.util.*;
import com.tanchiki.libgdx.model.tanks.Object.*;

public class BronetBullet1 extends Bullet {
    public BronetBullet1(float x, float y, int angle, float f) {
        super(x, y, angle, 2 / 5f, f, new Array<TextureRegion>(new TextureRegion[]{ObjectClass.GameStage.TextureLoader.getBullets()[0][13], ObjectClass.GameStage.TextureLoader.getBullets()[0][14], ObjectClass.GameStage.TextureLoader.getBullets()[0][15], ObjectClass.GameStage.TextureLoader.getBullets()[0][16]}));
        HP = 3;
		expl = TextureLoader.getInstance().getRedExpl();
    }
	
	public BronetBullet1(float x, float y, int angle, float f, Tank tank) {
		this(x, y, angle, f);
		parent = tank;
	}
}
