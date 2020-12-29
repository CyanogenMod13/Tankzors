package com.tanchiki.libgdx.model.ui.WeaponMenu;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.tanchiki.libgdx.graphics.GameActor;
import com.tanchiki.libgdx.model.tanks.TankUser;
import com.tanchiki.libgdx.stage.GameStage;
import com.tanchiki.libgdx.util.*;

public class Weapon extends GameActor {
    public float w = Gdx.graphics.getWidth() / 10;
    public float h = Gdx.graphics.getHeight() / 10;

    public Sprite s;

    public Sprite background;

    public TextureRegion r[];

    public BitmapFont font;

    int count;
    int icon;

    public Weapon(int icon, int count) {
        setSize(h, h);
        this.icon = icon;
        this.count = count;
        r = ObjectClass.GameStage.TextureLoader.getIcons()[0];
        s = new Sprite(r[icon]);
        background = new Sprite(r[14]);
        background.setAlpha(0.5f);
        font = FontLoader.f16;
        addListener(new CL());

    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        // TODO: Implement this method
        super.draw(batch, parentAlpha);
        background.setSize(getWidth(), getHeight());
        background.setPosition(getX(), getY());
        background.draw(batch);

        s.setSize(getWidth() / 2f, getHeight() / 2f);
        s.setCenter(getCenterX(), getCenterY());
        s.draw(batch);
        float q = font.getData().getGlyph('Q').height;
        font.draw(batch, "" + count, getX(), getY() + q);
    }

    private class CL extends ClickListener {

        @Override
        public void clicked(InputEvent event, float x, float y) {
            TankUser t = ObjectClass.GameStage.TankUser;
            // TODO: Implement this method
            super.clicked(event, x, y);
            if (t != null)
                switch (icon) {
                    case WeaponData.Type.light_bullet: {
                        Settings.TankUserSettings.bullet_type = 1;
                        Settings.pause = false;
                        ObjectClass.WeaponMenuStage.hideMenu();
                        break;
                    }
                    case WeaponData.Type.plazma: {
                        Settings.TankUserSettings.bullet_type = 2;
                        Settings.pause = false;
                        ObjectClass.WeaponMenuStage.hideMenu();
                        break;
                    }
					case WeaponData.Type.double_light_bullet: {
							Settings.TankUserSettings.bullet_type = 3;
							Settings.pause = false;
							ObjectClass.WeaponMenuStage.hideMenu();
							break;
						}
					case WeaponData.Type.double_plazma_bullet: {
							Settings.TankUserSettings.bullet_type = 4;
							Settings.pause = false;
							ObjectClass.WeaponMenuStage.hideMenu();
							break;
						}	
                    case WeaponData.Type.bronet_bullet: {
                        Settings.TankUserSettings.bullet_type = 5;
                        Settings.pause = false;
                        ObjectClass.WeaponMenuStage.hideMenu();
                        break;
                    }
                    case WeaponData.Type.bronet_bullet2: {
                        Settings.TankUserSettings.bullet_type = 6;
                        Settings.pause = false;
                        ObjectClass.WeaponMenuStage.hideMenu();
                        break;
                    }
                    case WeaponData.Type.rocket: {
                        Settings.TankUserSettings.bullet_type = 7;
                        Settings.pause = false;
                        ObjectClass.WeaponMenuStage.hideMenu();
                        break;
                    }


                    case WeaponData.Type.mine1: {
                        Settings.TankUserSettings.plus_bullet_type1 = 1;
                        Settings.pause = false;
                        ObjectClass.WeaponMenuStage.hideMenu();
                        break;
                    }

                    case WeaponData.Type.mine2: {
                        Settings.TankUserSettings.plus_bullet_type1 = 2;
                        Settings.pause = false;
                        ObjectClass.WeaponMenuStage.hideMenu();
                        break;
                    }

                    case WeaponData.Type.tnt1: {
                        Settings.TankUserSettings.plus_bullet_type1 = 3;
                        Settings.pause = false;
                        ObjectClass.WeaponMenuStage.hideMenu();
                        break;
                    }

                    case WeaponData.Type.tnt2: {
                        Settings.TankUserSettings.plus_bullet_type1 = 4;
                        Settings.pause = false;
                        ObjectClass.WeaponMenuStage.hideMenu();
                        break;
                    }

                    case WeaponData.Type.tnt3: {
                        Settings.TankUserSettings.plus_bullet_type1 = 5;
                        Settings.pause = false;
                        ObjectClass.WeaponMenuStage.hideMenu();
                        break;
                    }
                    case WeaponData.Type.art: {
                        Settings.TankUserSettings.plus_bullet_type2 = 1;
                        Settings.pause = false;
                        ObjectClass.WeaponMenuStage.hideMenu();
                        break;
                    }
                    case WeaponData.Type.air: {
                        Settings.TankUserSettings.plus_bullet_type2 = 2;
                        Settings.pause = false;
                        ObjectClass.WeaponMenuStage.hideMenu();
                        break;
                    }
                    case WeaponData.Type.time: {
                        WeaponData.time -= 1;
                        GameStage.stop_time(ObjectVarable.tank_unity);
                        Settings.pause = false;
                        ObjectClass.WeaponMenuStage.hideMenu();
                        break;
                    }
                    case WeaponData.Type.speed: {
                        WeaponData.speed -= 1;
                        ObjectClass.GameStage.TankUser.activate_speed_skill();
                        Settings.pause = false;
                        ObjectClass.WeaponMenuStage.hideMenu();
                        break;
                    }
                    case WeaponData.Type.shield: {
                        int count = WeaponData.Upgrade.shield >= 3 ? 25 : 15;
                        int d = Math.min(count - Settings.TankUserSettings.HPShieldBackup, WeaponData.shield);
                        //if(Settings.TankUserSettings.HPShieldBackup)
                        Settings.TankUserSettings.HPShieldBackup += d;
                        WeaponData.shield -= d;
                        Settings.pause = false;
                        ObjectClass.WeaponMenuStage.hideMenu();
                        break;
                    }
                    case WeaponData.Type.fix: {
                        GameStage GameStage = ObjectClass.GameStage;
                        if (GameStage.TankUser != null) {
                            if (GameStage.TankUser.HP != GameStage.TankUser.HPbackup)
                                if (WeaponData.fix > 0) {
                                    GameStage.TankUser.HP = GameStage.TankUser.HPbackup;
                                    WeaponData.fix -= 1;
                                }
                        }
                        Settings.pause = false;
                        ObjectClass.WeaponMenuStage.hideMenu();
                        break;
                    }
                }
        }
    }
}
