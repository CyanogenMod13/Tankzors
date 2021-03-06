package com.tanchiki.libgdx.model.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Touchpad;
import com.badlogic.gdx.scenes.scene2d.utils.DragListener;
import com.badlogic.gdx.scenes.scene2d.utils.SpriteDrawable;
import com.tanchiki.libgdx.model.tanks.TankUser;
import com.tanchiki.libgdx.stage.GameStage;

public class JoyStick extends Touchpad {
    private static SpriteDrawable t, t1 = null;
    TextButton.TextButtonStyle s1;
    GameStage gameStage = GameStage.getInstance();
    float size = Gdx.graphics.getHeight() / 7.2f;
    private float xx, yy;

    public JoyStick() {
        super(0, new Touchpad.TouchpadStyle());
        setDeadzone(size / 2);
        Texture texture = new Texture("texture/ui/knob.png");
        Sprite sp1 = new Sprite(texture);
        sp1.setSize(size, size);
        Sprite sp2 = new Sprite(texture);
        //this.center();
        t = new SpriteDrawable(sp1);
        t1 = new SpriteDrawable(sp2);
        getStyle().background = t1;
        getStyle().knob = t;
        setSize(size * 2, size * 2);
        setPosition(size / 2, size / 2);
        addListener(new DragListener() {
            @Override
            public void drag(InputEvent e, float xx, float yy, int pointer) {
                super.drag(e, xx, yy, pointer);

                Vector2 v = new Vector2(xx - (getX() + getWidth() / 2), yy - (getY() + getHeight() / 2));

                float x = v.x;
                float y = v.y;
                float len = (float) Math.sqrt(Math.pow(x, 2) + Math.pow(y, 2));
                float cos = x / len;
                float sin = y / len;

                float arcsin = (float) Math.toDegrees(Math.asin(sin));
                float arccos = (float) Math.toDegrees(Math.acos(cos));

                float angle = (arcsin >= 0) ? ((arccos <= 90) ? (arcsin) : (arccos)) : ((arccos >= 90) ? (Math.abs(arcsin) + 180) : (arcsin + 360));

                if (angle > 45 && angle < 135)
                    gameStage.getInstance().tankUser.setStateMotion(TankUser.Vec.UP);
                else if (angle > 135 && angle < 225)
                    gameStage.getInstance().tankUser.setStateMotion(TankUser.Vec.LEFT);
                else if (angle > 225 && angle < 315)
                    gameStage.getInstance().tankUser.setStateMotion(TankUser.Vec.DOWN);
                else if ((angle > 315 && angle <= 360) || (angle >= 0 && angle < 45))
                    gameStage.getInstance().tankUser.setStateMotion(TankUser.Vec.RIGHT);
            }

            @Override
            public void dragStop(InputEvent e, float x, float y, int pointer) {
                super.dragStop(e, x, y, pointer);
                gameStage.getInstance().tankUser.setStateMotion(TankUser.Vec.NONE);
            }
        });
    }
}
