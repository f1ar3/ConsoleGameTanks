package ru.vsu.cs.zagorodnev_g_a.objects;

import ru.vsu.cs.zagorodnev_g_a.objects.movable.Position;

public interface EaglePositionFactory {
    static Position respawnPosition(int x, int y) {
        return new Position(x,y);
    }
}
