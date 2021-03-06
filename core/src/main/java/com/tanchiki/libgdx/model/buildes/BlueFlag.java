package com.tanchiki.libgdx.model.buildes;

import com.tanchiki.libgdx.model.aircraft.Airplane;
import com.tanchiki.libgdx.model.terrains.MainTerrain;
import com.tanchiki.libgdx.stage.GameStage;
import com.tanchiki.libgdx.stage.PanelStage;
import com.tanchiki.libgdx.util.Settings;
import com.tanchiki.libgdx.util.SoundLoader;

public class BlueFlag extends Flag {
    public static int LAST_INDEX = 0;
    public static int COUNT = 0;

    public int index;

    public BlueFlag(float x, float y) {
        super(x, y, 0);
        COUNT++;
        index = LAST_INDEX++;
    }

    @Override
    void clicked() {
        remove();
        switch (MainTerrain.Mission.CODE) {
            case 6:
            case 35:
            case 36:
            case 37:
                if (COUNT == 0)
                    win();
                else
                    PanelStage.getInstance().addToast("Отлично, ищем дальше!");
                break;
            default:
                if (index < 3) {
                    int x0 = MainTerrain.getCurrentTerrain().getParameters().getKey(27 + index) * 2;
                    int y0 = GameStage.getInstance().world_height - MainTerrain.getCurrentTerrain().getParameters().getKey(28 + index) * 2;
                    MainTerrain.getCurrentTerrain().decor.addActor(new Airplane(x0, y0, 3, 10));
                }
        }
    }

    @Override
    public boolean remove() {
        SoundLoader.getInstance().getFlagPickup().play(Settings.volumeEffect);
        COUNT--;
        return super.remove();
    }

    @Override
    public void act(float delta) {
        LAST_INDEX = 0;
        super.act(delta);
    }
}
