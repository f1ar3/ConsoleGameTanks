package ru.vsu.cs.zagorodnev_g_a.logic;

import ru.vsu.cs.zagorodnev_g_a.field.Colors;

public class TankGameException extends Exception{
    public TankGameException(String errorMessage) {
        super(Colors.ANSI_RED + "Error: " + errorMessage + Colors.ANSI_RESET + "\n");
    }
}
