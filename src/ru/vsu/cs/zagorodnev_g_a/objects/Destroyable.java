package ru.vsu.cs.zagorodnev_g_a.objects;

import ru.vsu.cs.zagorodnev_g_a.objects.movable.Bullet;
import ru.vsu.cs.zagorodnev_g_a.objects.movable.Position;

public interface Destroyable extends Positionable {
    default boolean isDestroyMe(Bullet bullet) {
        Position position = getPosition();
        return position.x() == bullet.getPosition().x() && position.y() == bullet.getPosition().y();
    }
}

