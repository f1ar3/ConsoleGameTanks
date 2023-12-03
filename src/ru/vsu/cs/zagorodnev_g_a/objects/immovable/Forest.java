package ru.vsu.cs.zagorodnev_g_a.objects.immovable;

import ru.vsu.cs.zagorodnev_g_a.objects.BattleFieldObject;
import ru.vsu.cs.zagorodnev_g_a.objects.movable.Position;

public class Forest extends BattleFieldObject {
    private boolean isCollision = false;

    public Forest(Position position) {
        super(position);
    }

    @Override
    public boolean isCollision() {
        return isCollision;
    }

    @Override
    public void setCollision(boolean collision) {
        isCollision = collision;
    }
}
