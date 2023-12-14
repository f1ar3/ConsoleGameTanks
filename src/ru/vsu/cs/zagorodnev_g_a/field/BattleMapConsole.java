package ru.vsu.cs.zagorodnev_g_a.field;

import ru.vsu.cs.zagorodnev_g_a.objects.BattleFieldObject;
import ru.vsu.cs.zagorodnev_g_a.objects.ObjectFactory;
import ru.vsu.cs.zagorodnev_g_a.objects.immovable.*;
import ru.vsu.cs.zagorodnev_g_a.player.Player;
import ru.vsu.cs.zagorodnev_g_a.logic.Game;
import ru.vsu.cs.zagorodnev_g_a.logic.Turn;
import ru.vsu.cs.zagorodnev_g_a.objects.movable.MoveDirections;
import ru.vsu.cs.zagorodnev_g_a.objects.movable.MoveParameters;
import ru.vsu.cs.zagorodnev_g_a.objects.movable.Position;
import ru.vsu.cs.zagorodnev_g_a.objects.movable.Tank;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class BattleMapConsole {
    private static final int[] random = {0, 1, 2, 3, 4};
    protected static String[][] field;
    private static int currentNumberOfPlayers = 0;

    private static final ObjectFactory[] factories = new ObjectFactory[]{
            IndestructibleWall.FACTORY_INSTANCE,
            Wall.FACTORY_INSTANCE,
            Water.FACTORY_INSTANCE,
            Forest.FACTORY_INSTANCE,
            new ObjectFactory() {
                @Override
                public BattleFieldObject createObject(Position position) {
                    return null;
                }
            }
    };

    public static void initializeGame(Game game, int height, int width, int numberOfPlayers) {

        field = new String[height][width];

        setTiles(game,height, width);
        setIndestructibleWalls(game, height, width);
        setObjects(game, height, width);
        setTanks(game, height, width, numberOfPlayers);
        setPlayers(game);
        setTurns(game);
    }

    private static void setTiles(Game game, int height, int width) {
        Tile tile;
        for (int i = 1; i < height - 1; i++) {
            for (int j = 1; j < width - 1; j++) {
                tile = new Tile(new Position(j, i));
                game.getTiles().add(tile);
            }
        }
    }
    private static void setIndestructibleWalls(Game game, int height, int width) {
        BattleFieldObject indestructibleWall;
        for (int i = 0; i < width; i++) {
            indestructibleWall = factories[0].createObject(new Position(i, 0));
            game.getIndestructibleWalls().add(indestructibleWall);
        }

        for (int i = 0; i < width; i++) {
            indestructibleWall = factories[0].createObject(new Position(i, height - 1));
            game.getIndestructibleWalls().add(indestructibleWall);
        }

        for (int i = 0; i < height; i++) {
            indestructibleWall = factories[0].createObject(new Position(0, i));
            game.getIndestructibleWalls().add(indestructibleWall);
        }

        for (int i = 0; i < height; i++) {
            indestructibleWall = factories[0].createObject(new Position(width - 1, i));
            game.getIndestructibleWalls().add(indestructibleWall);
        }
    }
    private static void setTanks(Game game, int height, int width, int numberOfPlayers) {
        Tank tank;
        for (int i = 1; i < height / 2 + 1 ; i += 2) {
            if (currentNumberOfPlayers == numberOfPlayers) break;
            for (int j = width / 2; j < width - 1; j++) {
                if (i + (width / 2) == j) {
                    tank = new Tank(new Position(j, i), new MoveParameters(game.getVelocity()));
                    tank.getMp().setDirection(MoveDirections.LEFT);
                    tank.setColor(Colors.colorizeTank(currentNumberOfPlayers));
                    game.getTanks().add(tank);
                    currentNumberOfPlayers++;
                }
            }
        }
    }

    private static void setObjects(Game game, int height, int width) {
        for (int i = 1; i < height - 1; i++) {
            for (int j = 1; j < width - 1; j++) {
                setEagle(game, height, width, i, j);
                if ((i < height / 2 && j > width / 2 - 1 && i + (width / 2) == j)) {continue;}
                BattleFieldObject object = factories[getRandom(random)].createObject(new Position(j, i));
                if (object instanceof Wall) {
                    game.getWalls().add(object);
                } else if (object instanceof Water) {
                    game.getWater().add(object);
                } else if (object instanceof IndestructibleWall) {
                    game.getIndestructibleWalls().add(object);
                } else if (object instanceof Forest) {
                    game.getForest().add(object);
                }
            }
        }
    }

    private static void setEagle(Game game, int height, int width, int i, int j) {
        Eagle eagle;
        if (i == height - height / 4 && j == width / 4) {
           eagle = new Eagle(new Position(j,i));
           game.getEagles().add(eagle);
        }
    }

    private static void setPlayers(Game game) {
        Player player;
        for (BattleFieldObject tanks : game.getTanks()) {
            player = new Player((Tank) tanks, true);
            player.getTank().setBullets(new ArrayList<>());
            game.getPlayers().add(player);
        }
    }

    private static void setTurns(Game game) {
        Turn turn;
        for (Player value : game.getPlayers()) {
            turn = new Turn(false, value.getTank().getMp().getDirection());
            game.getTurns().add(turn);
        }
    }
    public static int getRandom(int[] array) {
        Random rnd = new Random();
        double probability = 0.30;
        if (rnd.nextDouble() <= probability) {
            int randomIndex = rnd.nextInt(array.length);
            return array[randomIndex];
        }
        return array.length - 1;
    }
}