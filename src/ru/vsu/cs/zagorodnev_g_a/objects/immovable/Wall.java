package ru.vsu.cs.zagorodnev_g_a.objects.immovable;

import ru.vsu.cs.zagorodnev_g_a.objects.BattleFieldObject;
import ru.vsu.cs.zagorodnev_g_a.objects.ObjectFactory;
import ru.vsu.cs.zagorodnev_g_a.objects.movable.Position;

public class Wall extends BattleFieldObject {

    public static class Factory implements ObjectFactory {
        @Override
        public BattleFieldObject createObject(Position position) {
            return new Wall(position);
        }
    }
    public static final Factory FACTORY_INSTANCE = new Factory();

    public Wall(Position position) {
        super(position);
    }

}

