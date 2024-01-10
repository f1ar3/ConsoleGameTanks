package ru.vsu.cs.zagorodnev_g_a.objects.immovable;

import ru.vsu.cs.zagorodnev_g_a.field.Colors;
import ru.vsu.cs.zagorodnev_g_a.logic.Game;
import ru.vsu.cs.zagorodnev_g_a.logic.addables.AddForest;
import ru.vsu.cs.zagorodnev_g_a.logic.addables.AddWall;
import ru.vsu.cs.zagorodnev_g_a.objects.BattleFieldObject;
import ru.vsu.cs.zagorodnev_g_a.objects.ObjectFactory;
import ru.vsu.cs.zagorodnev_g_a.objects.movable.Position;

import java.io.Serializable;

public class Forest extends BattleFieldObject implements Serializable {

    private static class Factory implements ObjectFactory {

        private AddForest addForest;

        public Factory(AddForest addForest) {
            this.addForest = addForest;
        }

        @Override
        public void createObject(Position position) {
            Forest forest = new Forest(position);
            addForest.add(forest);
        }
    }
//    public static final Forest.Factory FACTORY_INSTANCE = new Forest.Factory();

    public static ObjectFactory createFactoryInstance(AddForest addForest) {
        return new Factory(addForest);
    }

    public Forest(Position position) {
        super(position);
    }

    @Override
    public String toString() {
        return Colors.BLACK_BACKGROUND + Colors.ANSI_GREEN + " F " + Colors.ANSI_RESET;
    }

}
