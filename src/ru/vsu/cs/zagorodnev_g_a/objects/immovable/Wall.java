package ru.vsu.cs.zagorodnev_g_a.objects.immovable;

import ru.vsu.cs.zagorodnev_g_a.field.Colors;
import ru.vsu.cs.zagorodnev_g_a.logic.Game;
import ru.vsu.cs.zagorodnev_g_a.objects.BattleFieldObject;
import ru.vsu.cs.zagorodnev_g_a.objects.ObjectFactory;
import ru.vsu.cs.zagorodnev_g_a.objects.movable.Position;

import java.io.Serializable;

public class Wall extends BattleFieldObject implements Serializable {

    private static class Factory implements ObjectFactory {
        @Override
        public void createObject(Position position, Game game) {
            Wall wall = new Wall(position);
            game.getWalls().add(wall);
        }
    }
    public static final ObjectFactory FACTORY_INSTANCE = new Factory();

    public Wall(Position position) {
        super(position, true, true);
    }

    @Override
    public String toString() {
        return Colors.RED_BACKGROUND + " W " + Colors.ANSI_RESET;
    }

}

