package ru.vsu.cs.zagorodnev_g_a.objects.immovable;

import ru.vsu.cs.zagorodnev_g_a.field.Colors;
import ru.vsu.cs.zagorodnev_g_a.objects.BattleFieldObject;
import ru.vsu.cs.zagorodnev_g_a.objects.ObjectFactory;
import ru.vsu.cs.zagorodnev_g_a.objects.movable.Position;

public class Water extends BattleFieldObject {
    private static class Factory implements ObjectFactory {
        @Override
        public BattleFieldObject createObject(Position position) {
            return new Water(position);
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
