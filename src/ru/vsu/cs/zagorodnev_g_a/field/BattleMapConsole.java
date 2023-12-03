package ru.vsu.cs.zagorodnev_g_a.field;

import ru.vsu.cs.zagorodnev_g_a.objects.BattleFieldObject;
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
    public static final int[] random = {0, 1, 2};
    public static String[][] field;

    public static void initializeGame(Game game, int n, int k) {

        field = new String[n][k];

        Tile tile;
        for (int i = 1; i < n - 1; i++) {
            for (int j = 1; j < k - 1; j++) {
                tile = new Tile(new Position(j,i));
                game.getTiles().add(tile);
            }
        }

        IndestructibleWall indestructibleWall;
        for (int i = 0; i < k; i++) {
            indestructibleWall = new IndestructibleWall(new Position(i, 0));
            game.getIndestructibleWalls().add(indestructibleWall);
        }

        for (int i = 0; i < k; i++) {
            indestructibleWall = new IndestructibleWall(new Position(i, n - 1));
            game.getIndestructibleWalls().add(indestructibleWall);
        }

        for (int i = 0; i < n; i++) {
            indestructibleWall = new IndestructibleWall(new Position(0, i));
            game.getIndestructibleWalls().add(indestructibleWall);
        }

        for (int i = 0; i < n; i++) {
            indestructibleWall = new IndestructibleWall(new Position(k - 1, i));
            game.getIndestructibleWalls().add(indestructibleWall);
        }

        indestructibleWall = new IndestructibleWall(new Position(7, 8));
        game.getIndestructibleWalls().add(indestructibleWall);

        Wall wall;
        Water water;
        Forest forest;
        for (int i = 1; i < n - 1; i++) {
            for (int j = 1; j < k - 1; j++) {
                switch (getRandom(random)) {
                    case 0:
                        wall = new Wall(new Position(j,i));
                        game.getWalls().add(wall);
                        break;
                    case 1:
                        water = new Water(new Position(j,i));
                        game.getWater().add(water);
                        break;
                    case 2:
                        forest = new Forest(new Position(j,i));
                        game.getForest().add(forest);
                        break;
                }
            }
        }

        Tank tank1 = new Tank(new Position(5, 5), new MoveParameters(game.getVelocity()));
        tank1.getMp().setDirection(MoveDirections.UP);
        tank1.setColor(Colors.YELLOW_BACKGROUND_BRIGHT);
        game.getTanks().add(tank1);


        Tank tank2 = new Tank(new Position(8, 5), new MoveParameters(game.getVelocity()));
        tank2.getMp().setDirection(MoveDirections.UP);
        tank2.setColor(Colors.PURPLE_BACKGROUND_BRIGHT);
        game.getTanks().add(tank2);

        Player player;
        for (BattleFieldObject tank : game.getTanks()) {
            player = new Player((Tank) tank, true);
            player.getTank().setBullets(new ArrayList<>());
            game.getPlayers().add(player);
        }

        Turn turn;
        for (Player value : game.getPlayers()) {
            turn = new Turn(false, value.getTank().getMp().getDirection());
            game.getTurns().add(turn);
        }
    }
    public static int getRandom(int[] array) {
        Random rnd = new Random();
        double probability = 0.2;
        if (rnd.nextDouble() <= probability) {
            int randomIndex = rnd.nextInt(array.length);
            return array[randomIndex];
        }
        return -1;
    }
}