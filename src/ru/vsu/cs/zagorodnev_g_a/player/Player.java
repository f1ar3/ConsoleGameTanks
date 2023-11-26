package ru.vsu.cs.zagorodnev_g_a.player;

import ru.vsu.cs.zagorodnev_g_a.objects.movable.Tank;

public class Player {
    private final Tank tank;
    private boolean condition;

    public Player(Tank tank, boolean condition) {
        this.tank = tank;
        this.condition = condition;
    }

    public Tank getTank() {
        return tank;
    }

    public boolean isCondition() {
        return condition;
    }

    public void setCondition(boolean condition) {
        this.condition = condition;
    }
}
