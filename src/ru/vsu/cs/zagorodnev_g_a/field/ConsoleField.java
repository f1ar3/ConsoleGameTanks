package ru.vsu.cs.zagorodnev_g_a.field;

import ru.vsu.cs.zagorodnev_g_a.player.Player;
import ru.vsu.cs.zagorodnev_g_a.logic.Game;
import ru.vsu.cs.zagorodnev_g_a.objects.BattleFieldObject;
import ru.vsu.cs.zagorodnev_g_a.objects.movable.MoveDirections;
import ru.vsu.cs.zagorodnev_g_a.objects.movable.Tank;

import java.util.Objects;
import java.util.Scanner;

public class ConsoleField {

    Game game;
    private final Scanner sc = new Scanner(System.in);

    public ConsoleField(Game game) {
        this.game = game;
        BattleMapConsole.initializeGame(game);
        updateField();
        action();
    }

    private boolean inputKey(int numberOfPlayer) {
        System.out.println("player's" + " " + (numberOfPlayer + 1) + " action");
        String str;
        str = sc.next();
        if (Objects.equals(str, "w")) {
            game.upButton(numberOfPlayer);
            return true;
        } else if (Objects.equals(str, "s")) {
            game.downButton(numberOfPlayer);
            return true;
        } else if (Objects.equals(str, "a")) {
            game.leftButton(numberOfPlayer);
            return true;
        } else if (Objects.equals(str, "d")) {
            game.rightButton(numberOfPlayer);
            return true;
        }
        if (Objects.equals(str, "f")) {
            game.getPlayers().get(numberOfPlayer).getTank().setFire(true);
            game.fireButton(numberOfPlayer);
            return true;
        }
        return !Objects.equals(str, "q");
    }

    private void action(){
        boolean flag = false;
        while (true) {
            for (int i = 0; i < game.getPlayers().size(); i++) {
                if (game.getPlayers().get(i).isCondition()) {
                    flag = true;
                    if (inputKey(i)) {
                        inputActions(game.getPlayers().get(i).getTank());
                    }
                }
                updateField();
            }
            if (!flag) {
                break;
            }
            flag = false;
        }
    }

    private void inputActions(Tank tank) {
        if (tank.isFire()) {
            if (!tank.getBullets().isEmpty()) {
                for (int i = 0; i < tank.getBullets().size(); i++) {
                    int size = tank.getBullets().size();
                    game.destroyObjectsByBullet();
                    while (size == tank.getBullets().size()) {
                        tank.getBullets().get(i).move();
                        game.destroyObjectsByBullet();
                    }
                }
            }
            tank.setFire(false);
        }
    }


    private void updateField() {

        for (int i = 0; i < BattleMapConsole.field.length; i++) {
            for (int j = 0; j < BattleMapConsole.field[0].length; j++) {
                BattleMapConsole.field[i][j] = 'x';
            }
        }

        for (BattleFieldObject indestructibleWall : game.getIndestructibleWalls()) {
            BattleMapConsole.field[indestructibleWall.getPosition().y()][indestructibleWall.getPosition().x()] = 'D';
        }

        for (BattleFieldObject currentWall : game.getWalls()) {
            BattleMapConsole.field[currentWall.getPosition().y()][currentWall.getPosition().x()] = 'W';
        }

        for (Player player : game.getPlayers()) {
            if (player.isCondition()) {
                if (player.getTank().getMp().getDirection() == MoveDirections.LEFT) {
                    BattleMapConsole.field[player.getTank().getPosition().y()][player.getTank().getPosition().x()] = '<';
                } else if (player.getTank().getMp().getDirection() == MoveDirections.RIGHT) {
                    BattleMapConsole.field[player.getTank().getPosition().y()][player.getTank().getPosition().x()] = '>';
                } else if (player.getTank().getMp().getDirection() == MoveDirections.UP) {
                    BattleMapConsole.field[player.getTank().getPosition().y()][player.getTank().getPosition().x()] = '^';
                } else if (player.getTank().getMp().getDirection() == MoveDirections.DOWN) {
                    BattleMapConsole.field[player.getTank().getPosition().y()][player.getTank().getPosition().x()] = 'v';
                }
            }
        }
        printField();

    }

    private void printField() {
        for (char[] chars : BattleMapConsole.field) {
            for (int j = 0; j < BattleMapConsole.field[0].length; j++) {
                System.out.print(chars[j] + " ");
            }
            System.out.println();
        }
        for (int i = 0; i < BattleMapConsole.field[0].length; i++) {
            System.out.print("- ");
        }
        System.out.println();
    }
}
