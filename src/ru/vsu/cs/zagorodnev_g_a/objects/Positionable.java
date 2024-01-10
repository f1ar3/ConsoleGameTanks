package ru.vsu.cs.zagorodnev_g_a.objects;

import ru.vsu.cs.zagorodnev_g_a.objects.movable.Bullet;
import ru.vsu.cs.zagorodnev_g_a.objects.movable.Position;

import java.io.Serializable;

public interface Positionable extends Serializable {
    Position getPosition();
}

