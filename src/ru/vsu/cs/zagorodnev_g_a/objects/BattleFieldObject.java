package ru.vsu.cs.zagorodnev_g_a.objects;

import ru.vsu.cs.zagorodnev_g_a.objects.movable.Position;

import java.io.Serializable;

public class BattleFieldObject implements Positionable, Serializable {
    protected Position position;
    protected final boolean collision;

    protected boolean destroyable;

    public BattleFieldObject(Position position) {
        this(position, false, false);
    }
    public BattleFieldObject(Position position, boolean collision, boolean destroyable) {
        this.position = position;
        this.collision = collision;
        this.destroyable = destroyable;
    }

    @Override
    public Position getPosition() {
        return position;
    }

    public boolean isDestroyable() {
        return destroyable;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    public boolean isCollision() {
        return collision;
    }

    public boolean intersects(Position p) {
        return this.position.x() == p.x() && this.position.y() == p.y();
    }
}
