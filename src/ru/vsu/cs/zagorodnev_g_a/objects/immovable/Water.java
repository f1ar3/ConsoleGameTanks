package ru.vsu.cs.zagorodnev_g_a.objects.immovable;

import ru.vsu.cs.zagorodnev_g_a.field.Colors;
import ru.vsu.cs.zagorodnev_g_a.logic.Game;
import ru.vsu.cs.zagorodnev_g_a.logic.addables.AddWall;
import ru.vsu.cs.zagorodnev_g_a.logic.addables.AddWater;
import ru.vsu.cs.zagorodnev_g_a.objects.BattleFieldObject;
import ru.vsu.cs.zagorodnev_g_a.objects.ObjectFactory;
import ru.vsu.cs.zagorodnev_g_a.objects.movable.Position;

import java.io.Serializable;

public class Water extends BattleFieldObject implements Serializable {
    private static class Factory implements ObjectFactory {

        private AddWater addWater;

        public Factory(AddWater addWater) {
            this.addWater = addWater;
        }

        @Override
        public void createObject(Position position) {
            Water water = new Water(position);
            addWater.add(water);
        }
    }
//    public static final Factory FACTORY_INSTANCE = new Factory();

    public static ObjectFactory createFactoryInstance(AddWater addWater) {
        return new Factory(addWater);
    }
    public Water(Position position) {
        super(position);
    }

    @Override
    public String toString() {
        return Colors.BLUE_BACKGROUND + " ~ " + Colors.ANSI_RESET;
    }

}
