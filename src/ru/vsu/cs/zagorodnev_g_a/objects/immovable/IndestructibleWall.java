package ru.vsu.cs.zagorodnev_g_a.objects.immovable;

import ru.vsu.cs.zagorodnev_g_a.field.Colors;
import ru.vsu.cs.zagorodnev_g_a.logic.Game;
import ru.vsu.cs.zagorodnev_g_a.logic.addables.AddIndestructibleWall;
import ru.vsu.cs.zagorodnev_g_a.logic.addables.AddWall;
import ru.vsu.cs.zagorodnev_g_a.objects.BattleFieldObject;
import ru.vsu.cs.zagorodnev_g_a.objects.ObjectFactory;
import ru.vsu.cs.zagorodnev_g_a.objects.movable.Position;

import java.io.Serializable;

public class IndestructibleWall extends BattleFieldObject implements Serializable {

    private static class Factory implements ObjectFactory {

        private AddIndestructibleWall addIndestructibleWall;

        public Factory(AddIndestructibleWall addIndestructibleWall) {
            this.addIndestructibleWall = addIndestructibleWall;
        }

        @Override
        public void createObject(Position position) {
            IndestructibleWall indestructibleWall = new IndestructibleWall(position);
            addIndestructibleWall.add(indestructibleWall);
        }
    }
//    public static final IndestructibleWall.Factory FACTORY_INSTANCE = new IndestructibleWall.Factory();

    public static ObjectFactory createFactoryInstance(AddIndestructibleWall addIndestructibleWall) {
        return new Factory(addIndestructibleWall);
    }

    public IndestructibleWall(Position position) {
        super(position, true, false);
    }

    @Override
    public String toString() {
        return Colors.WHITE_BACKGROUND + " D " + Colors.ANSI_RESET;
    }
}