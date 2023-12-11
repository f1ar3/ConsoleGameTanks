package ru.vsu.cs.zagorodnev_g_a.field;

import ru.vsu.cs.zagorodnev_g_a.logic.TankGameException;
import ru.vsu.cs.zagorodnev_g_a.player.Player;
import ru.vsu.cs.zagorodnev_g_a.logic.Game;
import ru.vsu.cs.zagorodnev_g_a.objects.BattleFieldObject;
import ru.vsu.cs.zagorodnev_g_a.objects.movable.MoveDirections;
import ru.vsu.cs.zagorodnev_g_a.objects.movable.Tank;

import java.util.Objects;
import java.util.Scanner;

public class ConsoleField {

    Game game;
    protected final Scanner sc = new Scanner(System.in);

    int height;
    int width;
    int numberOfPlayers;

    public ConsoleField(Game game) {
        this.game = game;
        gameParameters();
        BattleMapConsole.initializeGame(game, height, width, numberOfPlayers);
        if (game.isGameWasFinished()) {return;}
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
                    updateField();
                }
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

        for (BattleFieldObject tile : game.getTiles()) {
            BattleMapConsole.field[tile.getPosition().y()][tile.getPosition().x()] = Colors.BLACK_BACKGROUND + " x " + Colors.ANSI_RESET;
        }

        for (BattleFieldObject indestructibleWall : game.getIndestructibleWalls()) {
            BattleMapConsole.field[indestructibleWall.getPosition().y()][indestructibleWall.getPosition().x()] = Colors.WHITE_BACKGROUND + " D " + Colors.ANSI_RESET;
        }

        for (BattleFieldObject currentWall : game.getWalls()) {
            BattleMapConsole.field[currentWall.getPosition().y()][currentWall.getPosition().x()] = Colors.RED_BACKGROUND + " W " + Colors.ANSI_RESET;
        }

        for (BattleFieldObject water : game.getWater()) {
            BattleMapConsole.field[water.getPosition().y()][water.getPosition().x()] = Colors.BLUE_BACKGROUND + " ~ " + Colors.ANSI_RESET;
        }

        for (BattleFieldObject forest : game.getForest()) {
            BattleMapConsole.field[forest.getPosition().y()][forest.getPosition().x()] = Colors.BLACK_BACKGROUND + Colors.ANSI_GREEN + " F " + Colors.ANSI_RESET;
        }

        for (BattleFieldObject eagle : game.getEagles()) {
            BattleMapConsole.field[eagle.getPosition().y()][eagle.getPosition().x()] = Colors.WHITE_BACKGROUND_BRIGHT  + Colors.ANSI_BLACK + " E " + Colors.ANSI_RESET;
        }

        for (Player player : game.getPlayers()) {
            if (player.isCondition()) {
                if (player.getTank().getMp().getDirection() == MoveDirections.LEFT) {
                    BattleMapConsole.field[player.getTank().getPosition().y()][player.getTank().getPosition().x()] = player.getTank().getColor() + Colors.ANSI_BLACK + " < " + Colors.ANSI_RESET;
                } else if (player.getTank().getMp().getDirection() == MoveDirections.RIGHT) {
                    BattleMapConsole.field[player.getTank().getPosition().y()][player.getTank().getPosition().x()] = player.getTank().getColor() + Colors.ANSI_BLACK + " > " + Colors.ANSI_RESET;
                } else if (player.getTank().getMp().getDirection() == MoveDirections.UP) {
                    BattleMapConsole.field[player.getTank().getPosition().y()][player.getTank().getPosition().x()] = player.getTank().getColor() + Colors.ANSI_BLACK + " ^ " + Colors.ANSI_RESET;
                } else if (player.getTank().getMp().getDirection() == MoveDirections.DOWN) {
                    BattleMapConsole.field[player.getTank().getPosition().y()][player.getTank().getPosition().x()] = player.getTank().getColor() + Colors.ANSI_BLACK + " v " + Colors.ANSI_RESET;
                }
            }
        }
        printField();

    }

    private void printField() {
        for (String[] chars : BattleMapConsole.field) {
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

    private void gameParameters() {
        while (true) {
            try {
                if (height < 10) {
                    System.out.print(Colors.CYAN_BACKGROUND + Colors.ANSI_BLACK + "Enter the number of dimensions of the playing field (height):" + Colors.ANSI_RESET + " ");
                    height = Integer.parseInt(sc.nextLine());
                    if (height < 10) {
                        throw new TankGameException("Field height must be greater than 10.");
                    }
                }
                if (width < 10) {
                    System.out.print(Colors.CYAN_BACKGROUND + Colors.ANSI_BLACK + "Enter the number of dimensions of the playing field (width):" + Colors.ANSI_RESET + " ");
                    width = Integer.parseInt(sc.nextLine());
                    if (width < 10) {
                        throw new TankGameException("Field width must be greater than 10.");
                    }
                }
                System.out.print(Colors.CYAN_BACKGROUND + Colors.ANSI_BLACK + "Enter the number player's:" + Colors.ANSI_RESET + " ");
                numberOfPlayers = sc.nextInt();
                    if (numberOfPlayers > 4) {
                        throw new TankGameException("The number of players must be from 1 to 4.");
                    }
                    break;
            } catch (NumberFormatException e) {
                System.out.println(Colors.ANSI_RED + "Error: Incorrect type of input value." + Colors.ANSI_RESET);
            } catch (TankGameException e) {
                System.out.println(e.getMessage());
            }
        }
        System.out.println();
    }
}
