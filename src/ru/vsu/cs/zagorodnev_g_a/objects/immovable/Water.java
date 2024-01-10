package ru.vsu.cs.zagorodnev_g_a.objects.immovable;

import ru.vsu.cs.zagorodnev_g_a.field.Colors;
import ru.vsu.cs.zagorodnev_g_a.logic.Game;
import ru.vsu.cs.zagorodnev_g_a.objects.BattleFieldObject;
import ru.vsu.cs.zagorodnev_g_a.objects.ObjectFactory;
import ru.vsu.cs.zagorodnev_g_a.objects.movable.Position;

import java.io.Serializable;

public class Water extends BattleFieldObject implements Serializable {
    private static class Factory implements ObjectFactory {
        @Override
        public void createObject(Position position, Game game) {
            Water water = new Water(position);
            game.getWater().add(water);
        }
    }
    public static final Factory FACTORY_INSTANCE = new Factory();
    public Water(Position position) {
        super(position);
    }

    @Override
    public String toString() {
        return Colors.BLUE_BACKGROUND + " ~ " + Colors.ANSI_RESET;
    }

}
