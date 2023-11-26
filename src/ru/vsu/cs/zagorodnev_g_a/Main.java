package ru.vsu.cs.zagorodnev_g_a;

import ru.vsu.cs.zagorodnev_g_a.field.ConsoleField;
import ru.vsu.cs.zagorodnev_g_a.logic.Game;

public class Main {
    public static void main(String[] args) {
        Game game= new Game();
        new ConsoleField(game);
    }
}
