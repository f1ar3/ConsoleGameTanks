package ru.vsu.cs.zagorodnev_g_a.logic;

import ru.vsu.cs.zagorodnev_g_a.field.BattleMapConsole;

public class GameState {

    private final BattleMapConsole battleMapConsole;

    public GameState(BattleMapConsole battleMapConsole) {
        this.battleMapConsole = battleMapConsole;
    }

    public BattleMapConsole getBattleMapConsole() {
        return battleMapConsole;
    }
}
