package ru.vsu.cs.zagorodnev_g_a.objects.immovable;

import ru.vsu.cs.zagorodnev_g_a.objects.BattleFieldObject;
import ru.vsu.cs.zagorodnev_g_a.objects.movable.Position;

public class Eagle extends BattleFieldObject {
    private boolean isAlive = true;
    private boolean isCollision = false;

    @Override
    public boolean isCollision() {
        return isCollision;
    }

    @Override
    public void setCollision(boolean collision) {
        isCollision = collision;
    }

    public Eagle(Position position) {
        super(position);
    }

    public boolean isAlive() {
        return isAlive;
    }

    public void setAlive(boolean alive) {
        isAlive = alive;
    }

}
