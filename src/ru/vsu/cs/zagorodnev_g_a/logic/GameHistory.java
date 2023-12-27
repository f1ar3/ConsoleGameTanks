package ru.vsu.cs.zagorodnev_g_a.logic;

import ru.vsu.cs.zagorodnev_g_a.field.BattleMapConsole;

import java.util.Stack;

public class GameHistory {
    private final Stack<GameState> executedMoves = new Stack<>();
    private final Stack<GameState> undoMoves = new Stack<>();

    public void saveMove(GameState gameState) {
        executedMoves.push(gameState);
        undoMoves.clear(); // очищаем стек отменённых ходов, тк новый ход сделан
    }
    public boolean canUndo() {
        return !executedMoves.isEmpty();
    }

    public BattleMapConsole undo() {
        if (!canUndo()) {
            return null;
        }
        GameState gameState = executedMoves.pop();
        undoMoves.push(gameState);
        return gameState.getBattleMapConsole();
    }
}

