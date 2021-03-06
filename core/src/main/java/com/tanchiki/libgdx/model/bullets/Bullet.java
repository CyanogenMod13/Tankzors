package com.tanchiki.libgdx.model.bullets;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Array;
import com.tanchiki.libgdx.model.buildes.Build;
import com.tanchiki.libgdx.model.buildes.ObjBuild;
import com.tanchiki.libgdx.model.explosions.SmallExplosion;
import com.tanchiki.libgdx.model.tanks.Tank;
import com.tanchiki.libgdx.model.tanks.TankUser;
import com.tanchiki.libgdx.model.tanks.Turret;
import com.tanchiki.libgdx.model.terrains.Block;
import com.tanchiki.libgdx.model.terrains.DestroyableBlock;
import com.tanchiki.libgdx.model.terrains.IronWall;
import com.tanchiki.libgdx.model.terrains.Spike;
import com.tanchiki.libgdx.stage.GameStage;
import com.tanchiki.libgdx.util.ObjectVariables;
import com.tanchiki.libgdx.util.Settings;
import com.tanchiki.libgdx.util.SoundLoader;
import com.tanchiki.libgdx.util.TextureLoader;

public class Bullet extends Actor {
    Sprite s;
    TextureRegion[] t;
    protected GameStage gameStage;
    float speed;
    public float HP = 1;
    public float fraction;
    public int angle;
    public Tank parent;
    public int ID = 0;
    public boolean isPlay = true;

    private final Vector2 pos = new Vector2();
    private Rectangle body = new Rectangle();

    public TextureRegion[][] expl;

    public Sound sound = SoundLoader.getInstance().getShellBullet();

    public Bullet(float x, float y, int angle, float speed, float f, Array<TextureRegion> r) {
        expl = TextureLoader.getInstance().getExpl();
        this.angle = angle;
        fraction = f;
        this.speed = speed;
        gameStage = gameStage.getInstance();
        t = new TextureRegion[4];

        for (int i = 0; i < 4; i++) {
            t[i] = r.get(i);
        }
        s = new Sprite(t[0]);
        s.setSize(s.getWidth() / 10 * ObjectVariables.size_block, s.getHeight() / 10 * ObjectVariables.size_block);
        setSize(s.getWidth(), s.getHeight());
        setPosition(x, y, Align.center);
        pos.set(Math.round(getX(Align.center)), Math.round(getY(Align.center)));
        if (fraction == ObjectVariables.tank_ally) gameStage.world_bullets_unity[(int) pos.x][(int) pos.y] = this;
        if (fraction == ObjectVariables.tank_enemy) gameStage.world_bullets_enemy[(int) pos.x][(int) pos.y] = this;
        body.setSize(2, 2);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
        if (angle == 1)
            s.setRegion(t[0]);
        if (angle == 4)
            s.setRegion(t[1]);
        if (angle == 3)
            s.setRegion(t[2]);
        if (angle == 2)
            s.setRegion(t[3]);
        s.setCenter(getX(Align.center), getY(Align.center));
        s.draw(batch);
    }

    public void destroyBullet() {
        if (HP <= 0) {
            gameStage.mainTerrain.explosions.addActor(new SmallExplosion(getX(Align.center), getY(Align.center), expl));

            if (fraction == ObjectVariables.tank_ally)
                if (pos.x >= 0 && pos.x < gameStage.world_wight && pos.y >= 0 && pos.y < gameStage.world_height)
                    gameStage.world_bullets_unity[(int) pos.x][(int) pos.y] = null;
            if (fraction == ObjectVariables.tank_enemy)
                if (pos.x >= 0 && pos.x < gameStage.world_wight && pos.y >= 0 && pos.y < gameStage.world_height)
                    gameStage.world_bullets_enemy[(int) pos.x][(int) pos.y] = null;
            if (fraction == ObjectVariables.tank_ally)
                if (x >= 0 && x < gameStage.world_wight && y >= 0 && y < gameStage.world_height)
                    gameStage.world_bullets_unity[x][y] = null;
            if (fraction == ObjectVariables.tank_enemy)
                if (x >= 0 && x < gameStage.world_wight && y >= 0 && y < gameStage.world_height)
                    gameStage.world_bullets_enemy[x][y] = null;
            remove();
        }
    }

    protected float a = 0;
    protected float a2 = 0;

    protected void speed(float delta) {
        if (a > a2)
            a = a2;
        switch (angle) {
            case 1: {
                moveBy(0, -(speed + this.a));
                break;
            }
            case 2: {
                moveBy(speed + this.a, 0);
                break;
            }
            case 3: {
                moveBy(0, speed + this.a);
                break;
            }
            case 4: {
                moveBy(-(speed + this.a), 0);
                break;
            }
        }
        if (this.a <= this.a2)
            this.a += this.a * delta;
        else
            this.a = this.a2;
    }

    private int x, y;

    @Override
    public void act(float delta) {
        if (sound != null) {
            sound.play(Settings.volumeEffect);
            sound = null;
        }

        isPlay = false;

        if (!gameStage.mainTerrain.rect.contains(getX(Align.center), getY(Align.center))) {
            HP = 0;
            destroyBullet();
            return;
        }

        destroyBullet();
        speed(delta);

        x = (int) getX(Align.center);
        y = (int) getY(Align.center);
        x += x % 2;
        y += y % 2;

        if (!(x >= 0 && x < gameStage.world_wight && y >= 0 && y < gameStage.world_height)) return;

        if (fraction == ObjectVariables.tank_ally) gameStage.world_bullets_unity[(int) pos.x][(int) pos.y] = null;
        if (fraction == ObjectVariables.tank_enemy) gameStage.world_bullets_enemy[(int) pos.x][(int) pos.y] = null;
        pos.set(x, y);
        if (fraction == ObjectVariables.tank_ally) gameStage.world_bullets_unity[(int) pos.x][(int) pos.y] = this;
        if (fraction == ObjectVariables.tank_enemy) gameStage.world_bullets_enemy[(int) pos.x][(int) pos.y] = this;

        Tank tank = gameStage.mainTerrain.hashTanks.get(gameStage.world_block[x][y]);
        if (tank != null)
            if (tank.fraction != fraction) {
                body.setCenter(tank.getX(Align.center), tank.getY(Align.center));
                if (body.contains(getX(Align.center), getY(Align.center))) {
                    if (parent instanceof TankUser) {
                        tank.giveCoin = true;
                        tank.giveDamage += HP;
                    }
                    final float tankHP = tank.HP + tank.HPShield;
                    if (tank.HPShield > 0)
                        SoundLoader.getInstance().getHitShield().play(Settings.volumeEffect);
                    else
                        SoundLoader.getInstance().getHitMetal().play(Settings.volumeEffect);
                    tank.destroyTank(HP);
                    HP -= Math.max(tankHP, 0);
                    destroyBullet();
                }
            }

        Bullet bullet = (fraction == ObjectVariables.tank_enemy) ? gameStage.world_bullets_unity[(int) pos.x][(int) pos.y] : gameStage.world_bullets_enemy[(int) pos.x][(int) pos.y];
        if (bullet != null) {
            final float bulletHP = bullet.HP;
            bullet.HP -= Math.max(HP, 0);
            HP -= Math.max(bulletHP, 0);
            bullet.destroyBullet();
            destroyBullet();
        }

        Block block = gameStage.world_physic_block[x][y];
        if (block != null && !(block instanceof Spike) && !(parent instanceof Turret) || block instanceof IronWall) {
            block.bullet = this;
            final float blockHP = block.HP;
            block.HP -= Math.max(HP, 0);
            if (block instanceof DestroyableBlock) HP -= blockHP;
            else HP = 0;
            block.destroyWall();
            block.sound.play(Settings.volumeEffect);
            destroyBullet();
        }

        ObjBuild objbuild = gameStage.worldBuilds[x][y];
        Build build = objbuild instanceof Build ? (Build) objbuild : null;
        if (build != null)
            if (build.fraction != fraction) {
                if (objbuild != null && parent instanceof TankUser) ((Build) objbuild).give_coin = true;
                final float buildHP = build.HP;
                build.HP -= Math.max(HP, 0);
                HP -= Math.max(buildHP, 0);
                destroyBullet();
            }
        super.act(delta);
    }
}
