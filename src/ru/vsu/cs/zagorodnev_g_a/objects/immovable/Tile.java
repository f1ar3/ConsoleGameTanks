package ru.vsu.cs.zagorodnev_g_a.objects.immovable;

import ru.vsu.cs.zagorodnev_g_a.field.BattleMapConsole;
import ru.vsu.cs.zagorodnev_g_a.field.Colors;
import ru.vsu.cs.zagorodnev_g_a.objects.BattleFieldObject;
import ru.vsu.cs.zagorodnev_g_a.objects.movable.Position;

import java.io.Serializable;
import java.util.List;

public class Tile extends BattleFieldObject implements Serializable {

    public Tile(Position position) {
        super(position);
    }

    @Override
    public String toString() {
        return Colors.BLACK_BACKGROUND + " x " + Colors.ANSI_RESET;
    }

    public BattleFieldObject clone() throws CloneNotSupportedException {
        return new Tile(this.position);
    }
}
