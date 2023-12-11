package ru.vsu.cs.zagorodnev_g_a.objects.immovable;

import ru.vsu.cs.zagorodnev_g_a.objects.BattleFieldObject;
import ru.vsu.cs.zagorodnev_g_a.objects.ObjectFactory;
import ru.vsu.cs.zagorodnev_g_a.objects.movable.Position;

public class IndestructibleWall extends BattleFieldObject {

    public static class Factory implements ObjectFactory {
        @Override
        public BattleFieldObject createObject(Position position) {
            return new IndestructibleWall(position);
        }
    }
    public static final IndestructibleWall.Factory FACTORY_INSTANCE = new IndestructibleWall.Factory();

    public IndestructibleWall(Position position) {
        super(position);
    }

}