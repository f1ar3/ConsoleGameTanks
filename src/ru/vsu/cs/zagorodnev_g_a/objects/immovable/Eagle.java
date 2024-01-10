package ru.vsu.cs.zagorodnev_g_a.objects.immovable;

import ru.vsu.cs.zagorodnev_g_a.field.BattleMapConsole;
import ru.vsu.cs.zagorodnev_g_a.field.Colors;
import ru.vsu.cs.zagorodnev_g_a.objects.BattleFieldObject;
import ru.vsu.cs.zagorodnev_g_a.objects.movable.Position;

import java.io.Serializable;

public class Eagle extends BattleFieldObject implements Serializable {
    private int numberOfRespawn = 0;
    private int pointsForEagle = 3;

    private final Position startPosition = new Position(getPosition().x(), getPosition().y());

    public Eagle(Position position) {
        super(position);
    }

    public int getNumberOfRespawn() {
        return numberOfRespawn;
    }

    public void setNumberOfRespawn(int numberOfRespawn) {
        this.numberOfRespawn = numberOfRespawn;
    }

    public int getPointsForEagle() {
        return pointsForEagle;
    }

    public void setPointsForEagle(int pointsForEagle) {
        this.pointsForEagle = pointsForEagle;
    }

    public Position getStartPosition() {
        return startPosition;
    }

    @Override
    public String toString() {
        return Colors.WHITE_BACKGROUND_BRIGHT  + Colors.ANSI_BLACK + " E " + Colors.ANSI_RESET;
    }

    public BattleFieldObject clone() throws CloneNotSupportedException {
        Eagle clonedEagle = new Eagle(this.position);
        clonedEagle.setNumberOfRespawn(this.getNumberOfRespawn());
        clonedEagle.setPointsForEagle(this.getPointsForEagle());
        return clonedEagle;
    }
}
