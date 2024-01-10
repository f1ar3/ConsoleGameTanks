package ru.vsu.cs.zagorodnev_g_a.objects.immovable;

import ru.vsu.cs.zagorodnev_g_a.field.Colors;
import ru.vsu.cs.zagorodnev_g_a.logic.Game;
import ru.vsu.cs.zagorodnev_g_a.logic.addables.AddWall;
import ru.vsu.cs.zagorodnev_g_a.objects.BattleFieldObject;
import ru.vsu.cs.zagorodnev_g_a.objects.ObjectFactory;
import ru.vsu.cs.zagorodnev_g_a.objects.movable.Position;

import java.io.Serializable;

public class Wall extends BattleFieldObject implements Serializable {

    private static class Factory implements ObjectFactory {
        private AddWall aw;

        public Factory(AddWall aw) {
            this.aw = aw;
        }

        @Override
        public void createObject(Position position) {
            Wall wall = new Wall(position);
            aw.add(wall);
        }
    }
    //public static final ObjectFactory FACTORY_INSTANCE = new Factory();
    public static ObjectFactory createFactoryInstance(AddWall aw) {
        return new Factory(aw);
    }

    public Wall(Position position) {
        super(position, true, true);
    }

    @Override
    public String toString() {
        return Colors.RED_BACKGROUND + " W " + Colors.ANSI_RESET;
    }

}

