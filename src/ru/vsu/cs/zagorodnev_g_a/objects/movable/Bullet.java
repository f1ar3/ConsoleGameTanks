package ru.vsu.cs.zagorodnev_g_a.objects.movable;

import java.io.Serializable;

public class Bullet extends MovableObject implements Serializable {

    public Bullet(Position position, MoveParameters mp) {
        super(position, mp);
    }
}
