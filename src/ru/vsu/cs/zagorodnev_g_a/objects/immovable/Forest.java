package ru.vsu.cs.zagorodnev_g_a.objects.immovable;

import ru.vsu.cs.zagorodnev_g_a.field.Colors;
import ru.vsu.cs.zagorodnev_g_a.objects.BattleFieldObject;
import ru.vsu.cs.zagorodnev_g_a.objects.ObjectFactory;
import ru.vsu.cs.zagorodnev_g_a.objects.movable.Position;

public class Forest extends BattleFieldObject {

    private static class Factory implements ObjectFactory {
        @Override
        public Forest createObject(Position position) {
            return new Forest(position);
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
