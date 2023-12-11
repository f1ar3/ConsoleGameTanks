package ru.vsu.cs.zagorodnev_g_a.objects.immovable;

import ru.vsu.cs.zagorodnev_g_a.objects.BattleFieldObject;
import ru.vsu.cs.zagorodnev_g_a.objects.movable.Position;

public class Eagle extends BattleFieldObject {
    private boolean isAlive = true;
    private int pointsForEagle = 3;

    public Eagle(Position position) {
        super(position);
    }

    public boolean isAlive() {
        return isAlive;
    }

    public void setAlive(boolean alive) {
        isAlive = alive;
    }

    public int getPointsForEagle() {
        return pointsForEagle;
    }

    public void setPointsForEagle(int pointsForEagle) {
        this.pointsForEagle = pointsForEagle;
    }
}
