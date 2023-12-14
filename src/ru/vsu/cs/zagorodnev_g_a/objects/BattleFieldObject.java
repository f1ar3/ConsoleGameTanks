package ru.vsu.cs.zagorodnev_g_a.objects;

import ru.vsu.cs.zagorodnev_g_a.objects.movable.Position;

public class BattleFieldObject implements Positionable {
    protected Position position;
    protected final boolean collision;

    public BattleFieldObject(Position position) {
        this(position, false);
    }
    public BattleFieldObject(Position position, boolean collision) {
        this.position = position;
        this.collision = collision;
    }

    @Override
    public Position getPosition() {
        return position;
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
