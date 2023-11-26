package ru.vsu.cs.zagorodnev_g_a.objects;

import ru.vsu.cs.zagorodnev_g_a.objects.movable.Position;

public class BattleFieldObject {
    protected Position position;

    public BattleFieldObject(Position position) {
        this.position = position;
    }

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    public boolean intersects(Position p) {
        return this.position.x() == p.x() && this.position.y() == p.y();
    }
}
