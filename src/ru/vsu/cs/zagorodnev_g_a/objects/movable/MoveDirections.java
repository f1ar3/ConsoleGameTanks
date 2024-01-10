package ru.vsu.cs.zagorodnev_g_a.objects.movable;

import java.io.Serializable;

public enum MoveDirections implements Serializable {
    UP(0, -1),
    DOWN(0, 1),
    LEFT(-1, 0),
    RIGHT(1, 0),
    NONE(0, 0);

    public final int dx, dy;

    MoveDirections(int dx, int dy) {
        this.dx = dx;
        this.dy = dy;
    }
}
