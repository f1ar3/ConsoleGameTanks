package ru.vsu.cs.zagorodnev_g_a.objects;

import ru.vsu.cs.zagorodnev_g_a.objects.movable.Position;

public interface ObjectFactory {
    BattleFieldObject createObject(Position position);
}
