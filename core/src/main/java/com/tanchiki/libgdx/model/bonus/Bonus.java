package com.tanchiki.libgdx.model.bonus;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.utils.Align;
import com.tanchiki.libgdx.model.tanks.Tank;
import com.tanchiki.libgdx.model.tanks.TankUser;
import com.tanchiki.libgdx.model.terrains.MainTerrain;
import com.tanchiki.libgdx.model.tnt.TNT2;
import com.tanchiki.libgdx.stage.GameStage;
import com.tanchiki.libgdx.stage.PanelStage;
import com.tanchiki.libgdx.util.*;

public class Bonus extends Actor {
    public GameStage GameStage;
    float time;
    Animation<TextureRegion> anim;
    public Sprite s;
    public Sprite overlays;
    int type_bonus;
    public boolean animation = true;
    public TextureRegion[] t;
    public String code_name = "bonus";
    public Sound sound = SoundLoader.getInstance().getPowerupPickup();

    public Bonus(int[] index, int type_bonus, float x, float y) {
        this.type_bonus = type_bonus;
        GameStage = GameStage.getInstance();
        t = TextureLoader.getInstance().getBonus()[0];
        anim = new Animation(0.5f / 3f, t[index[0]], t[index[1]], t[index[2]]);
        s = new Sprite(t[0]);
        overlays = new Sprite(TextureLoader.getInstance().getOverlayers()[0][4]);
        setSize(ObjectVariables.size_block * 2, ObjectVariables.size_block * 2);
        setPosition(x, y, Align.center);
        GameStage.world_bonus[(int) x][(int) y] = this;

        s.setSize(getWidth(), getHeight());
        s.setPosition(getX(), getY());
    }

    public Bonus(int type_bonus, float x, float y) {
        switch (type_bonus) {
            case WeaponData.Type.fix:
                init(new int[]{9, 10, 11}, WeaponData.Type.fix, x, y);
                //sound = SoundLoader.getInstance().getRepairPickup();
                break;
            case WeaponData.Type.live:
                init(new int[]{3, 4, 5}, WeaponData.Type.live, x, y);
                break;
            case ObjectVariables.coin_id:
                init(new int[]{15, 16, 17}, ObjectVariables.coin_id, x, y);
                break;
            case WeaponData.Type.speed:
                init(new int[]{0, 1, 2}, WeaponData.Type.speed, x, y);
                //sound = SoundLoader.getInstance().getSpeedPickup();
                break;
            case ObjectVariables.star_id:
                init(new int[]{12, 13, 14}, ObjectVariables.star_id, x, y);
                //sound = SoundLoader.getInstance().getStarPickup();
                break;
            case WeaponData.Type.time:
                init(new int[]{6, 7, 8}, WeaponData.Type.time, x, y);
                //sound = SoundLoader.getInstance().getFreezePickup();
                break;
            default: {
                init(new int[3], type_bonus, x, y);
                s.setRegion(t[MathUtils.random(18, 19)]);
                animation = false;
            }
        }
        code_name += "" + type_bonus;
    }

    private void init(int[] index, int type_bonus, float x, float y) {
        this.type_bonus = type_bonus;
        GameStage = GameStage.getInstance();
        t = TextureLoader.getInstance().getBonus()[0];
        anim = new Animation(0.5f / 3f, t[index[0]], t[index[1]], t[index[2]]);
        s = new Sprite(t[0]);
        overlays = new Sprite(TextureLoader.getInstance().getOverlayers()[0][4]);
        setSize(ObjectVariables.size_block * 2, ObjectVariables.size_block * 2);
        setPosition(x, y, Align.center);
        GameStage.world_bonus[(int) x][(int) y] = this;

        s.setSize(getWidth(), getHeight());
        s.setPosition(getX(), getY());
    }

    @Override
    public boolean remove() {
        sound.play(Settings.volumeEffect);
        GameStage.world_bonus[(int) getX(Align.center)][(int) getY(Align.center)] = this;

        return super.remove();
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
        overlays.setSize(6, 6);
        overlays.setCenter(getX(Align.center), getY(Align.center));
        overlays.draw(batch);
        s.draw(batch);
    }

    @Override
    public void act(float delta) {
        time += delta;
        if (animation)
            s.setRegion(anim.getKeyFrame(time, true));

        Actor tank = GameStage.world_tank[(int) getX(Align.center)][(int) getY(Align.center)];
        if (tank != null) {
            int count = 0;
            switch (type_bonus) {
                case WeaponData.Type.speed:
                    ((Tank) tank).activateSpeedSkill();
                    remove();
                    break;
                case WeaponData.Type.time:
                    if (tank instanceof TankUser) {
                        GameStage.stop_time(((Tank) tank).fraction);
                        remove();
                    }
                    break;
                case WeaponData.Type.fix:
                    if (((Tank) tank).HP != ((Tank) tank).HPBackup) {
                        ((Tank) tank).HP = ((Tank) tank).HPBackup;
                        remove();
                    }
                    break;
                case WeaponData.Type.live:
                    if (tank instanceof TankUser) {
                        ++WeaponData.live;
                        remove();
                    }
                    break;
                case ObjectVariables.coin_id:
                    if (tank instanceof TankUser) {
                        Settings.TankUserSettings.coin += 5;
                        MainTerrain.getCurrentTerrain().coin += 5;
                        remove();
                    }
                    break;
                case ObjectVariables.star_id:
                    if (tank instanceof TankUser) {
                        ++Settings.TankUserSettings.star;
                        MainTerrain.getCurrentTerrain().star += 1;
                        remove();
                    }
                    break;
                default:
                    if (tank instanceof TankUser) {
                        PanelStage.getInstance().toasts.clear();
                        switch (MainTerrain.getCurrentTerrain().getParameters().getKey(123 + type_bonus - 1)) {
                            case 0:
                                PanelStage.getInstance().addToast("Подстава, пустой ящичек!");
                                break;
                            case 1:
                                PanelStage.getInstance().addToast("Снаряды — 30 зарядов");
                                WeaponData.light_bullet += 30;
                                break;
                            case 2:
                                PanelStage.getInstance().addToast("Плазма — 20 зарядов");
                                WeaponData.plazma += 20;
                                break;
                            case 3:
                                PanelStage.getInstance().addToast("Двойная плазма — 20 зарядов");
                                WeaponData.double_palzma += 20;
                                break;
                            case 4:
                                PanelStage.getInstance().addToast("Двойные снаряды — 20 зарядов");
                                WeaponData.double_light_bullet += 20;
                                break;
                            case 5:
                                PanelStage.getInstance().addToast("Бронебойные обычные — 10 зарядов");
                                WeaponData.bronet_bullet += 10;
                                break;
                            case 6:
                                PanelStage.getInstance().addToast("Бронебойные усиленные — 10 зарядов");
                                WeaponData.bronet_bullet2 += 10;
                                break;
                            case 7:
                                PanelStage.getInstance().addToast("Мины слабые — 10 штук");
                                WeaponData.mine1 += 10;
                                break;
                            case 8:
                                PanelStage.getInstance().addToast("Мины мощные — 5 штуки");
                                WeaponData.mine2 += 5;
                                break;
                            case 9:
                                PanelStage.getInstance().addToast("Динамит первого типа — 3 штуки");
                                WeaponData.tnt1 += 3;
                                break;
                            case 10:
                                PanelStage.getInstance().addToast("Динамит второго типа — 2 штуки");
                                WeaponData.tnt2 += 2;
                                break;
                            case 11:
                                PanelStage.getInstance().addToast("Динамит третьего типа — 1 штука");
                                WeaponData.tnt3 += 1;
                                break;
                            case 12:
                                PanelStage.getInstance().addToast("Артиллерия — 2 штуки");
                                WeaponData.art += 2;
                                break;
                            case 13:
                            case 14:
                                PanelStage.getInstance().addToast("Боеголовки — 2 штуки");
                                WeaponData.rocket += 2;
                                break;
                            case 15:
                                PanelStage.getInstance().addToast("Поддержка с воздуха — 1 штука");
                                WeaponData.air += 1;
                                break;
                            case 16:
                                PanelStage.getInstance().addToast("Союзник — 1 штука");
                                WeaponData.tank3 += 1;
                                break;
                            case 17:
                                PanelStage.getInstance().addToast("Силовое поле — 3 единицы");
                                WeaponData.shield += 3;
                                break;
                            case 18:
                                PanelStage.getInstance().addToast("Временная броня — 1 единица");
                                WeaponData.brone1 += 1;
                                GameStage.tankUser.HP++;
                                break;
                            case 19:
                                PanelStage.getInstance().addToast("Аааа! Allah Akbar!");
                                MainTerrain.getCurrentTerrain().decorGround.addActor(new TNT2(getX(Align.center), getY(Align.center)));
                                break;
                        }
                        remove();
                    }
            }
        }

        super.act(delta);
    }
}
