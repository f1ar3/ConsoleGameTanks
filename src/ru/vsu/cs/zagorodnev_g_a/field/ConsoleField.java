package ru.vsu.cs.zagorodnev_g_a.field;

import ru.vsu.cs.zagorodnev_g_a.logic.*;
import ru.vsu.cs.zagorodnev_g_a.player.Player;
import ru.vsu.cs.zagorodnev_g_a.objects.BattleFieldObject;
import ru.vsu.cs.zagorodnev_g_a.objects.movable.MoveDirections;
import ru.vsu.cs.zagorodnev_g_a.objects.movable.Tank;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class ConsoleField {

    Game game;
    protected final Scanner sc = new Scanner(System.in);

    public int height;
    private int width;
    private int numberOfPlayers;
    BattleMapConsole battleMapConsole;

    GameState gameState;
    GameHistory gameHistory;

    public ConsoleField() throws CloneNotSupportedException {
        gameParameters();
        game = new Game(this.height, this.width);
        battleMapConsole = new BattleMapConsole(this.game, this.height, this.width, this.numberOfPlayers);
        gameState = new GameState(battleMapConsole);
        gameHistory = new GameHistory();
        updateField(gameState);
        action();
    }

    interface Quitable {
        default boolean isContinueGame() {
            return true;
        }
    }

    interface PlayerAction extends Quitable {
        void applyActionTo(Game g, int numPlayer);


        public static class DummyImpl implements PlayerAction {
            @Override
            public void applyActionTo(Game g, int numPlayer) {
            }
        };
        public static final PlayerAction NO_ACTION = new DummyImpl();
    }

    private static class MovePlayerAction implements PlayerAction {

        private final MoveDirections md;

        public MovePlayerAction(MoveDirections md) {
            this.md = md;
        }


        @Override
        public void applyActionTo(Game g, int numPlayer) {
            g.uniButton(numPlayer, md);
        }
    }

    private static class FirePlayerAction implements PlayerAction {

        @Override
        public void applyActionTo(Game g, int numPlayer) {
            g.fireButton(numPlayer);
        }
    }

    private final Map<String, PlayerAction> dirs = new HashMap<>();
    {
        dirs.put("w", new MovePlayerAction(MoveDirections.UP));
        dirs.put("s", new MovePlayerAction(MoveDirections.DOWN));
        dirs.put("a", new MovePlayerAction(MoveDirections.LEFT));
        dirs.put("d", new MovePlayerAction(MoveDirections.RIGHT));
        dirs.put("f", new FirePlayerAction());
        dirs.put("q", new PlayerAction.DummyImpl(){
            @Override
            public boolean isContinueGame() {
                return false;
            }
        });
    }
    private PlayerAction getActionByName(String name) {
        return dirs.getOrDefault(name, PlayerAction.NO_ACTION);
    }

    private boolean inputKey(int numberOfPlayer) {
        System.out.println("player's" + " " + (numberOfPlayer + 1) + " action");
        String str;
        str = sc.next();

        PlayerAction pa = getActionByName(str);
        pa.applyActionTo(game, numberOfPlayer);
        return pa.isContinueGame();
    }

    private void action() throws CloneNotSupportedException {
        boolean flag = false;
        while (true) {
            BattleMapConsole clonedBattleMapConsole = (BattleMapConsole) battleMapConsole.clone();
            GameState gameStateBeforeMove = new GameState(clonedBattleMapConsole);
            for (int i = 0; i < game.getPlayers().size(); i++) {
                if (game.getPlayers().get(i).isCondition()) {
                    flag = true;
                    if (inputKey(i)) {
                        inputActions(game.getPlayers().get(i).getTank());
                    }
                    updateField(gameState);
                }
            }
            System.out.println("Do you want replay the move?");
            String response = sc.next();
            if (response.equals("yes")) {
                updateField(gameHistory.undo());
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


    private void updateField(GameState gameState) {

        for (BattleFieldObject tile : game.getTiles()) {
            gameState.getBattleMapConsole().field[tile.getPosition().y()][tile.getPosition().x()] = tile.toString();
        }

        for (BattleFieldObject indestructibleWall : game.getIndestructibleWalls()) {
            gameState.getBattleMapConsole().field[indestructibleWall.getPosition().y()][indestructibleWall.getPosition().x()] = indestructibleWall.toString();
        }

        for (BattleFieldObject wall : game.getWalls()) {
            gameState.getBattleMapConsole().field[wall.getPosition().y()][wall.getPosition().x()] = wall.toString();
        }

        for (BattleFieldObject water : game.getWater()) {
            gameState.getBattleMapConsole().field[water.getPosition().y()][water.getPosition().x()] = water.toString();
        }

        for (BattleFieldObject forest : game.getForest()) {
            gameState.getBattleMapConsole().field[forest.getPosition().y()][forest.getPosition().x()] = forest.toString();
        }

        for (BattleFieldObject eagle : game.getEagles()) {
            gameState.getBattleMapConsole().field[eagle.getPosition().y()][eagle.getPosition().x()] = eagle.toString();
        }

        for (Player player : game.getPlayers()) {
            if (player.isCondition()) {
                if (player.getTank().getMp().getDirection() == MoveDirections.LEFT) {
                    gameState.getBattleMapConsole().field[player.getTank().getPosition().y()][player.getTank().getPosition().x()] = player.getTank().toString();
                } else if (player.getTank().getMp().getDirection() == MoveDirections.RIGHT) {
                    gameState.getBattleMapConsole().field[player.getTank().getPosition().y()][player.getTank().getPosition().x()] = player.getTank().toString();
                } else if (player.getTank().getMp().getDirection() == MoveDirections.UP) {
                    gameState.getBattleMapConsole().field[player.getTank().getPosition().y()][player.getTank().getPosition().x()] = player.getTank().toString();
                } else if (player.getTank().getMp().getDirection() == MoveDirections.DOWN) {
                    gameState.getBattleMapConsole().field[player.getTank().getPosition().y()][player.getTank().getPosition().x()] = player.getTank().toString();
                }
            }
        }
        printField();

    }

    private void printField() {
        for (String[] chars : battleMapConsole.field) {
            for (int j = 0; j < battleMapConsole.field[0].length; j++) {
                System.out.print(chars[j] + " ");
            }
            System.out.println();
        }
        for (int i = 0; i < battleMapConsole.field[0].length; i++) {
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
                if (numberOfPlayers > 4 || numberOfPlayers < 2) {
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
    private void messageAboutEnd() {
        System.out.println("End of the game");
    }
}