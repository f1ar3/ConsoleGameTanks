package ru.vsu.cs.zagorodnev_g_a.objects.immovable;

import ru.vsu.cs.zagorodnev_g_a.field.Colors;
import ru.vsu.cs.zagorodnev_g_a.logic.Game;
import ru.vsu.cs.zagorodnev_g_a.objects.BattleFieldObject;
import ru.vsu.cs.zagorodnev_g_a.objects.ObjectFactory;
import ru.vsu.cs.zagorodnev_g_a.objects.movable.Position;

import java.io.Serializable;

public class Forest extends BattleFieldObject implements Serializable {

    private static class Factory implements ObjectFactory {
        @Override
        public void createObject(Position position, Game game) {
            Forest forest = new Forest(position);
            game.getForest().add(forest);
        }
    }
    public static final Forest.Factory FACTORY_INSTANCE = new Forest.Factory();

    public Forest(Position position) {
        super(position);
    }

    @Override
    public String toString() {
        return Colors.BLACK_BACKGROUND + Colors.ANSI_GREEN + " F " + Colors.ANSI_RESET;
    }

}
