package com.tanchiki.libgdx.model.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Array;
import com.tanchiki.libgdx.graphics.GameActor;
import com.tanchiki.libgdx.model.tanks.Object.Tank;
import com.tanchiki.libgdx.model.tanks.TankUser;
import com.tanchiki.libgdx.model.terrains.ConcreteWall;
import com.tanchiki.libgdx.model.terrains.Grass;
import com.tanchiki.libgdx.model.terrains.MainTerrain;
import com.tanchiki.libgdx.model.terrains.Sand;
import com.tanchiki.libgdx.util.ObjectClass;
import com.tanchiki.libgdx.util.Settings;
import com.tanchiki.libgdx.util.*;
import com.tanchiki.libgdx.model.buildes.*;
import com.tanchiki.libgdx.model.terrains.*;

public class MiniMap extends GameActor {
    public MiniMap() {
        setSize(Gdx.graphics.getHeight() / 3, Gdx.graphics.getHeight() / 3);
        setCenterPosition(Gdx.graphics.getWidth() / 2, Gdx.graphics.getHeight() / 2);
        setDebug(true);
        addListener(new ClickListener() {

            @Override
            public boolean keyUp(InputEvent e, int code) {
                hide();
                return super.keyUp(e, code);
            }

        });
    }

    Color colorGrass = new Color(67 / 255f, 74 / 255f, 21 / 255f, 1);
    Color colorSand = new Color(164 / 255f, 140 / 255f, 98 / 255f, 1);
    Color colorBlockConcrete = new Color(132 / 255f, 136 / 255f, 106 / 255f, 1);
    Color colorBlockStone = new Color(123 / 255f, 103 / 255f, 54 / 255f, 1);
	int render = 0;
	
    @Override
    public void drawDebug(ShapeRenderer shapes) {
		if (WeaponData.radar == 0) return;
        // TODO: Implement this method
		render++;
        ShapeRenderer.ShapeType currenttype = shapes.getCurrentType();
        shapes.set(ShapeRenderer.ShapeType.Filled);

        float size = getHeight() / (ObjectClass.GameStage.world_height / 2);
        setWidth(ObjectClass.GameStage.world_wight / 2 * size);

        MainTerrain m = ObjectClass.GameStage.MT;

        Array<Actor> ground = m.ground.getChildren();
        for (int i = 0; i < ground.size; i++) {
            GameActor a = (GameActor) ground.get(i);
            if (a instanceof Grass)
                shapes.setColor(colorGrass);
            if (a instanceof Sand)
                shapes.setColor(colorSand);

            shapes.rect(getX() + a.getCenterX() / 2 * size, getY() + a.getCenterY() / 2 * size, size, size);
        }

        Array<Actor> bonus = m.bonus.getChildren();
		if (render > 10 && WeaponData.termovisor >= 2)
        for (int i = 0; i < bonus.size; i++) {
            GameActor a = (GameActor) bonus.get(i);
            shapes.setColor(Color.SKY);

            shapes.rect(getX() + a.getCenterX() / 2 * (size) + size / 2 - (size / 1.5f / 2), getY() + a.getCenterY() / 2 * (size) + size / 2 - (size / 1.5f / 2), size / 1.5f, size / 1.5f);
        }

        for (int i = 0; i < m.walls.getChildren().size; i++) {
            GameActor a = (GameActor) m.walls.getChildren().get(i);
            if (a instanceof ConcreteWall)
                shapes.setColor(colorBlockConcrete);
            else
                shapes.setColor(colorBlockStone);
			if (a instanceof SmallTree || a instanceof Cactus) {
				shapes.setColor(Color.GREEN);
				shapes.circle(getX() + a.getCenterX() / 2 * size + size / 2, getY() + a.getCenterY() / 2 * size + size / 2, size / 2);
			} else shapes.rect(getX() + a.getCenterX() / 2 * size, getY() + a.getCenterY() / 2 * size, size, size);
        }

		for (int i = 0; i < m.builds.getChildren().size; i++) {
            GameActor a = (GameActor) m.builds.getChildren().get(i);
			shapes.set(ShapeRenderer.ShapeType.Line);
            if (a instanceof AngarUnity) {
				shapes.setColor(Color.GREEN);
				shapes.rect(getX() + a.getCenterX() / 2 * size, getY() + a.getCenterY() / 2 * size, size, size);
			}
			if (a instanceof Radar.RadarUnity) {
				shapes.setColor(Color.GREEN);
				Radar.RadarUnity r = (Radar.RadarUnity) a;
				shapes.rect(getX() + r.x / 2 * size, getY() + r.y / 2 * size - size, size * 2, size * 2);
			}
			if (a instanceof ReactorCore.ReactorCoreUnity) {
				shapes.setColor(Color.GREEN);
				ReactorCore.ReactorCoreUnity r = (ReactorCore.ReactorCoreUnity) a;
				shapes.rect(getX() + r.x / 2 * size, getY() + r.y / 2 * size - size, size * 2, size * 2);
			}
			if (a instanceof AngarEnemy) {
				shapes.setColor(Color.RED);
				shapes.rect(getX() + a.getCenterX() / 2 * size, getY() + a.getCenterY() / 2 * size, size, size);
			}
			if (a instanceof Radar.RadarEnemy) {
				shapes.setColor(Color.RED);
				Radar.RadarEnemy r = (Radar.RadarEnemy) a;
				shapes.rect(getX() + r.x / 2 * size, getY() + r.y / 2 * size - size, size * 2, size * 2);
			}
			if (a instanceof ReactorCore.ReactorCoreEnemy) {
				shapes.setColor(Color.RED);
				ReactorCore.ReactorCoreEnemy r = (ReactorCore.ReactorCoreEnemy) a;
				shapes.rect(getX() + r.x / 2 * size, getY() + r.y / 2 * size - size, size * 2, size * 2);
			}
			if (a instanceof RedFlag) {
				shapes.setColor(Color.PURPLE);
				shapes.triangle(getX() + a.getCenterX() / 2 * size, getY() + a.getCenterY() / 2 * size, 
								getX() + a.getCenterX() / 2 * size, getY() + a.getCenterY() / 2 * size + size,
								getX() + a.getCenterX() / 2 * size + size, getY() + a.getCenterY() / 2 * size + size);
			}
			if (a instanceof YellowFlag) {
				shapes.setColor(Color.YELLOW);
				shapes.triangle(getX() + a.getCenterX() / 2 * size, getY() + a.getCenterY() / 2 * size, 
								getX() + a.getCenterX() / 2 * size, getY() + a.getCenterY() / 2 * size + size,
								getX() + a.getCenterX() / 2 * size + size, getY() + a.getCenterY() / 2 * size + size);
			}
			if (a instanceof BlueFlag) {
				shapes.setColor(Color.BLUE);
				shapes.triangle(getX() + a.getCenterX() / 2 * size, getY() + a.getCenterY() / 2 * size, 
								getX() + a.getCenterX() / 2 * size, getY() + a.getCenterY() / 2 * size + size,
								getX() + a.getCenterX() / 2 * size + size, getY() + a.getCenterY() / 2 * size + size);
			}
			shapes.set(ShapeRenderer.ShapeType.Filled);
        }
		if (WeaponData.termovisor >= 1) {
			for (int i = 0; i < m.tanks_enemy.getChildren().size; i++) {
				GameActor a = (GameActor) m.tanks_enemy.getChildren().get(i);
				shapes.setColor(Color.RED);
				//shapes.rect(getX() + a.getCenterX()/2 * size,getY() + a.getCenterY()/2 * size,size,size);
				printTanks(shapes, a, size);
			}

			for (int i = 0; i < m.tanks_unity.getChildren().size; i++) {
				GameActor a = (GameActor) m.tanks_unity.getChildren().get(i);
				if (a instanceof TankUser)
					shapes.setColor(Color.YELLOW);
				else
					shapes.setColor(Color.GREEN);
				printTanks(shapes, a, size);
			}
		}
		if (render > 20) render = 0;
        shapes.set(currenttype);
    }

    private void printTanks(ShapeRenderer shapes, GameActor a, float size) {
        switch (((Tank) a).angle_for_bullet) {
            case Tank.DOWN:
                shapes.triangle(getX() + a.getCenterX() / 2 * size, getY() + a.getCenterY() / 2 * size + size,
                        getX() + a.getCenterX() / 2 * size + size, getY() + a.getCenterY() / 2 * size + size,
                        getX() + a.getCenterX() / 2 * size + size / 2, getY() + a.getCenterY() / 2 * size);
                break;
            case Tank.RIGHT:
                shapes.triangle(getX() + a.getCenterX() / 2 * size, getY() + a.getCenterY() / 2 * size,
                        getX() + a.getCenterX() / 2 * size, getY() + a.getCenterY() / 2 * size + size,
                        getX() + a.getCenterX() / 2 * size + size, getY() + a.getCenterY() / 2 * size + size / 2);
                break;
            case Tank.LEFT:
                shapes.triangle(getX() + a.getCenterX() / 2 * size + size, getY() + a.getCenterY() / 2 * size,
                        getX() + a.getCenterX() / 2 * size + size, getY() + a.getCenterY() / 2 * size + size,
                        getX() + a.getCenterX() / 2 * size, getY() + a.getCenterY() / 2 * size + size / 2);
                break;
            case Tank.UP:
                shapes.triangle(getX() + a.getCenterX() / 2 * size, getY() + a.getCenterY() / 2 * size,
                        getX() + a.getCenterX() / 2 * size + size, getY() + a.getCenterY() / 2 * size,
                        getX() + a.getCenterX() / 2 * size + size / 2, getY() + a.getCenterY() / 2 * size + size);
                break;
        }
    }

    public static MiniMap map = null;

    public static void show() {
        /*map = new MiniMap();
        Vector3 pos = ObjectClass.GameStage.cam.position;
        map.setCenterPosition(pos.x, pos.y);
        Settings.pause = true;
        ObjectClass.GameStage.addActor(map);*/
    }

    public static void hide() {
        /*if (map != null)
            map.remove();
        Settings.pause = false;
        map = null;*/
    }
}
