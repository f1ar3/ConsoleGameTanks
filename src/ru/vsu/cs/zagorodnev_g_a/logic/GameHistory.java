package ru.vsu.cs.zagorodnev_g_a.logic;

import ru.vsu.cs.zagorodnev_g_a.field.BattleMapConsole;

import java.io.Serializable;
import java.util.Stack;

public class GameHistory implements Serializable {
    private final Stack<Game> executedMoves = new Stack<>();
    private final Stack<Game> undoMoves = new Stack<>();

    public void saveMove(Game game) {
        executedMoves.push(game);
        undoMoves.clear(); // очищаем стек отменённых ходов, тк новый ход сделан
    }
    public boolean canUndo() {
        return !executedMoves.isEmpty();
    }

    public Game undo() {
        if (!canUndo()) {
            return null;
        }
        Game game = executedMoves.pop();
        undoMoves.push(game);
        return game;
    }
}

