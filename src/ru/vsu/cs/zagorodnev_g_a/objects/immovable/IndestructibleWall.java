package ru.vsu.cs.zagorodnev_g_a.objects.immovable;

import ru.vsu.cs.zagorodnev_g_a.field.Colors;
import ru.vsu.cs.zagorodnev_g_a.logic.Game;
import ru.vsu.cs.zagorodnev_g_a.objects.BattleFieldObject;
import ru.vsu.cs.zagorodnev_g_a.objects.ObjectFactory;
import ru.vsu.cs.zagorodnev_g_a.objects.movable.Position;

import java.io.Serializable;

public class IndestructibleWall extends BattleFieldObject implements Serializable {

    private static class Factory implements ObjectFactory {
        @Override
        public void createObject(Position position, Game game) {
            IndestructibleWall indestructibleWall = new IndestructibleWall(position);
            game.getIndestructibleWalls().add(indestructibleWall);
        }
    }
    public static final IndestructibleWall.Factory FACTORY_INSTANCE = new IndestructibleWall.Factory();

    public IndestructibleWall(Position position) {
        super(position, true, false);
    }

    @Override
    public String toString() {
        return Colors.WHITE_BACKGROUND + " D " + Colors.ANSI_RESET;
    }
}