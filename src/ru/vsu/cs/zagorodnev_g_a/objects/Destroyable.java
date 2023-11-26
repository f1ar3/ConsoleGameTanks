package ru.vsu.cs.zagorodnev_g_a.objects;

import ru.vsu.cs.zagorodnev_g_a.objects.movable.Bullet;
import ru.vsu.cs.zagorodnev_g_a.objects.movable.Position;

public interface Destroyable {
    default boolean destroy(Position position, Bullet bullet) {
        return position.x() == bullet.getPosition().x() && position.y() == bullet.getPosition().y();
    }
}

