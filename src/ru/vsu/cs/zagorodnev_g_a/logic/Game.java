package ru.vsu.cs.zagorodnev_g_a.logic;

import ru.vsu.cs.zagorodnev_g_a.Main;
import ru.vsu.cs.zagorodnev_g_a.field.BattleMapConsole;
import ru.vsu.cs.zagorodnev_g_a.field.Colors;
import ru.vsu.cs.zagorodnev_g_a.field.ConsoleField;
import ru.vsu.cs.zagorodnev_g_a.logic.addables.*;
import ru.vsu.cs.zagorodnev_g_a.objects.EaglePositionFactory;
import ru.vsu.cs.zagorodnev_g_a.objects.immovable.*;
import ru.vsu.cs.zagorodnev_g_a.player.Player;
import ru.vsu.cs.zagorodnev_g_a.objects.BattleFieldObject;
import ru.vsu.cs.zagorodnev_g_a.objects.movable.Bullet;
import ru.vsu.cs.zagorodnev_g_a.objects.movable.MoveDirections;
import ru.vsu.cs.zagorodnev_g_a.objects.movable.Position;
import ru.vsu.cs.zagorodnev_g_a.objects.movable.Tank;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Game implements Serializable, AddTile, AddWall, AddIndestructibleWall, AddForest, AddWater, AddTurn, AddPlayer, AddEagle {
    private final int height;
    private final int width;
    public Game(int height, int width) {
        this.height = height;
        this.width = width;
    }

    private List<Player> players = new ArrayList<>();
    private List<Tank> tanks = new ArrayList<>();
    private List<Tile> tiles = new ArrayList<>();
    private List<Wall> walls = new ArrayList<>();
    private List<IndestructibleWall> indestructibleWalls = new ArrayList<>();
    private List<Water> water = new ArrayList<>();
    private List<Forest> forest = new ArrayList<>();
    private List<Eagle> eagles = new ArrayList<>();
    private List<Turn> turns = new ArrayList<>();
    private final int velocity = 1;
    private boolean condition;
    private boolean gameWasFinished = false;

    public Iterable<Turn> getTurns() {
        return turns;
    }

    public void setTurns(List<Turn> turns) {
        this.turns = turns;
    }

    public void add(Turn turn) {
        turns.add(turn);
    }

    public int getVelocity() {
        return velocity;
    }

    public boolean isCondition() {
        return condition;
    }

    public void setCondition(boolean condition) {
        this.condition = condition;
    }

    public Iterable<Player> getPlayers() {
        return players;
    }

    public void setPlayers(List<Player> players) {
        this.players = players;
    }

    public void add(Player player) {
        players.add(player);
    }

    public Iterable<Tank> getTanks() {
        return tanks;
    }

    public void setTanks(List<Tank> tanks) {
        this.tanks = tanks;
    }

    public void add(Tank tank) {
        tanks.add(tank);
    }

    public Iterable<Tile> getTiles() {return tiles;}
    public void add(Tile t) {
        tiles.add(t);
    }
    public void setTiles(List<Tile> tiles) {this.tiles = tiles;}
    public Iterable<Wall> getWalls() {
        return walls;
    }
    public void add(Wall w) {
        walls.add(w);
    }

    public void setWalls(List<Wall> walls) {
        this.walls = walls;
    }

    public Iterable<IndestructibleWall> getIndestructibleWalls() {
        return indestructibleWalls;
    }

    public void setIndestructibleWalls(List<IndestructibleWall> indestructibleWalls) {
        this.indestructibleWalls = indestructibleWalls;
    }

    public void add(IndestructibleWall indestructibleWall) {
        indestructibleWalls.add(indestructibleWall);
    }

    public boolean isGameWasFinished() {
        return gameWasFinished;
    }

    public void setGameWasFinished(boolean gameWasFinished) {
        this.gameWasFinished = gameWasFinished;
    }

    public Iterable<Water> getWater() {
        return water;
    }

    public void setWater(List<Water> water) {
        this.water = water;
    }

    public void add(Water wat) {
        water.add(wat);
    }

    public Iterable<Forest> getForest() {
        return forest;
    }

    public void setForest(List<Forest> forest) {
        this.forest = forest;
    }

    public void add(Forest f) {
        forest.add(f);
    }

    public Iterable<Eagle> getEagles() {
        return eagles;
    }

    public void setEagles(List<Eagle> eagles) {
        this.eagles = eagles;
    }

    public void add(Eagle eagle) {
        eagles.add(eagle);
    }

    public Game deepCopy() {
        try {
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            ObjectOutputStream out = new ObjectOutputStream(bos);
            out.writeObject(this);

            ByteArrayInputStream bis = new ByteArrayInputStream(bos.toByteArray());
            ObjectInputStream in = new ObjectInputStream(bis);
            return (Game) in.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }

    private Position respawnPosition(int i) {
        Position[] rp = new Position[] {
                (EaglePositionFactory.respawnPosition(width / 4, height / 4)),
                (EaglePositionFactory.respawnPosition(width - width / 4 - 1, height / 4)),
                (EaglePositionFactory.respawnPosition(width - width / 4 - 1, height - height / 4)),
                (EaglePositionFactory.respawnPosition(width / 4, height - height / 4))};
        return rp[i];
    }

    private static void destroySeparatedObjects(List<? extends BattleFieldObject> objects, Player player) {
        for (int i = 0; i < player.getTank().getBullets().size(); i++) {
            for (BattleFieldObject object : objects) {
                if (object.intersects(player.getTank().getBullets().get(i).getPosition())) {
                    if (object.isDestroyable()) {
                        objects.remove(object);
                        messageAboutDestroyedObject(player);
                    }
                    player.getTank().getBullets().remove(i);
                    break;
                }
            }
        }
    }

    public void destroyObjectsByBullet() {
        for (int i = 0; i < players.size(); i++) {
            if (players.get(i).isCondition()) {
                for (int j = 0; j < players.get(i).getTank().getBullets().size(); j++) {
                    for (Player value : players) {
                        if (!players.get(i).equals(value) && checkTankIntersectsBullet(players.get(i).getTank().getBullets(), j, value)){
                            messageAboutDestroyedTank(i, value);
                            updatePointsForKill(i, value);
                            value.setCondition(true);
                            value.getTank().setPosition(new Position(value.getTank().getStartPosition().x(), value.getTank().getStartPosition().y()));
                            break;
                        }
                    }
                }
                destroySeparatedObjects(walls, players.get(i));
                destroySeparatedObjects(indestructibleWalls, players.get(i));
            }
        }

        for (int i = 0; i < players.size(); i++) {
            for (int j = 0; j < players.size(); j++) {
                if (i != j) {
                    destroyBulletsByCollision();
                }
            }
        }
    }

    public void destroyBulletsByCollision() {
        Player player1 = players.get(0);
        Player player2 = players.get(1);
        for (int i = 0; i < player1.getTank().getBullets().size(); i++) {
            for (int j = 0; j < player2.getTank().getBullets().size(); j++) {
                if (player1.isCondition()) {
                    if (player1.getTank().getBullets().get(i).intersects(player2.getTank().getBullets().get(j).getPosition())
                            || ((((player1.getTank().getBullets().get(i).intersects(new Position(player2.getTank().getBullets().get(j).getPosition().x() + player2.getTank().getBullets().get(j).getMp().getVelocity(), player2.getTank().getBullets().get(j).getPosition().y()))
                            && player1.getTank().getBullets().get(i).intersects(new Position(player2.getTank().getBullets().get(j).getPosition().x() - player2.getTank().getBullets().get(j).getMp().getVelocity(), player2.getTank().getBullets().get(j).getPosition().y())))
                            && player1.getTank().getBullets().get(i).getPosition().y() == player2.getTank().getBullets().get(j).getPosition().y())
                            || ((player1.getTank().getBullets().get(i).intersects(new Position(player2.getTank().getBullets().get(j).getPosition().x(), player2.getTank().getBullets().get(j).getPosition().y() + player2.getTank().getBullets().get(j).getMp().getVelocity()))
                            && player1.getTank().getBullets().get(i).intersects(new Position(player2.getTank().getBullets().get(j).getPosition().x(), player2.getTank().getBullets().get(j).getPosition().y() - player2.getTank().getBullets().get(j).getMp().getVelocity())))
                            && player1.getTank().getBullets().get(i).getPosition().x() == player2.getTank().getBullets().get(j).getPosition().x())))) {
                        player1.getTank().getBullets().remove(i);
                        player2.getTank().getBullets().remove(j);
                        break;
                    }
                }
            }
        }
    }

    private boolean checkTankIntersectsBullet(List<Bullet> bullets, int indexPlayer, Player player) {
        if (player.isCondition()) {
            if (player.getTank().intersects(bullets.get(indexPlayer).getPosition())) {
                player.setCondition(false);
                bullets.remove(indexPlayer);
                return true;
            }
        }
        return false;
    }

    private static boolean isCollision(Tank tank, List<? extends BattleFieldObject> list, int changeX, int changeY) {
        for (BattleFieldObject object : list) {
            if (object.intersects(new Position(tank.getPosition().x() + changeX, tank.getPosition().y() + changeY))
                    && object.isCollision()) {
                return true;
            }
        }
        return false;
    }

    public void moveInViewOfCollision(int changeX, int changeY, int numberOfPlayer) {
        for (int i = 0; i < players.size(); i++) {
            if (!isCollision(players.get(numberOfPlayer).getTank(), walls, changeX, changeY)
                    && !isCollision(players.get(numberOfPlayer).getTank(), indestructibleWalls, changeX, changeY)
                    && !isCollision(players.get(numberOfPlayer).getTank(), water, changeX, changeY)
                    && !tanksCollision(players.get(numberOfPlayer), players.get(i), changeX, changeY) && numberOfPlayer != i) {
                eagleCapture(players.get(numberOfPlayer).getTank(), eagles, changeX, changeY);
                players.get(numberOfPlayer).getTank().move();
                return;
            }
        }
        messageAboutObstacle(numberOfPlayer);
    }

    private boolean tanksCollision(Player player1, Player player2, int changeX, int changeY) {
        if (player1.isCondition() && player2.isCondition()) {
            return player2.getTank().intersects(new Position(player1.getTank().getPosition().x() + changeX, player1.getTank().getPosition().y() + changeY));
        }
        return false;
    }

    private void eagleCapture(Tank tank, List<Eagle> eagles, int changeX, int changeY) {
        for (Eagle eagle : eagles) {
            if (eagle.intersects(new Position(tank.getPosition().x() + changeX, tank.getPosition().y() + changeY))) {
                int numberOfRespawn = eagle.getNumberOfRespawn();
                tank.setPoints(tank.getPoints() + eagle.getPointsForEagle());
                updatePointsForEagle(tank);
                eagle.setPosition(respawnPosition(numberOfRespawn));
                eagle.setNumberOfRespawn(numberOfRespawn + 1);
                if (numberOfRespawn == 3) {
                    eagle.setNumberOfRespawn(0);
                }
            }
        }
    }

    public void victory(Player player) {
        if (player.getTank().getPoints() > 20) {
            System.out.println("The winner is " + player.getTank().getColor() + Colors.ANSI_BLACK + " ^ " + Colors.ANSI_RESET);
            setGameWasFinished(true);
        }
    }

    private boolean isInWater(Tank tank, List<Water> waters) {
        for (Water water : waters) {
            if (water.intersects(new Position(tank.getPosition().x(), tank.getPosition().y()))) {
                return true;
            }
        }
        return false;
    }

    public void backToStartPosition(int numberOfPlayer) {
        if (isInWater(players.get(numberOfPlayer).getTank(), water)) {
            players.get(numberOfPlayer).getTank().setPosition(new Position(players.get(numberOfPlayer).getTank().getStartPosition().x(), players.get(numberOfPlayer).getTank().getStartPosition().y()));
            messageAboutWater(numberOfPlayer);
        }
    }

    private boolean isInForest(Tank tank, List<Forest> forests) {
        for (Forest forest : forests) {
            if (forest.intersects(new Position(tank.getPosition().x(), tank.getPosition().y()))) {
                return true;
            }
        }
        return false;
    }

    public void restart(){
        players.clear();
        tanks.clear();
        walls.clear();
        indestructibleWalls.clear();
        water.clear();
        forest.clear();
        eagles.clear();
    }


    public void timerBulletRunning(int indexOfPlayer, int indexOfCurrBullet){
        players.get(indexOfPlayer).getTank().getBullets().get(indexOfCurrBullet).move();
    }

    public void uniButton(int indexOfPlayer, MoveDirections direction){
        turns.get(indexOfPlayer).setTurned(turns.get(indexOfPlayer).getDirection() == direction);
        if (!turns.get(indexOfPlayer).isTurned()) {
            players.get(indexOfPlayer).getTank().turn(direction);
            turns.get(indexOfPlayer).setDirection(direction);
        } else {
            if (players.get(indexOfPlayer).isCondition()) {
                moveInViewOfCollision(velocity * direction.dx, velocity * direction.dy, indexOfPlayer);
                backToStartPosition(indexOfPlayer);
            }
        }
    }

    public void fireButton(int indexOfPlayer){
        players.get(indexOfPlayer).getTank().setFire(true);
        players.get(indexOfPlayer).getTank().shoot();
    }

    private void updatePointsForKill (int indexOfPlayer, Player player) {
        players.get(indexOfPlayer).getTank().setPoints(player.getTank().getPointsForKill() + players.get(indexOfPlayer).getTank().getPoints());
        System.out.println(Colors.CYAN_BACKGROUND + Colors.ANSI_BLACK + "Points of tank " + Colors.ANSI_RESET + players.get(indexOfPlayer).getTank().getColor() + Colors.ANSI_BLACK + " ^ "
                + Colors.ANSI_RESET + Colors.CYAN_BACKGROUND + Colors.ANSI_BLACK + " : " + players.get(indexOfPlayer).getTank().getPoints() + Colors.ANSI_RESET + "\n");
    }

    private void updatePointsForEagle(Tank tank) {
        System.out.println(Colors.CYAN_BACKGROUND + Colors.ANSI_BLACK + "Tank " + Colors.ANSI_RESET + tank.getColor() + Colors.ANSI_BLACK + " ^ "
                + Colors.ANSI_RESET + Colors.CYAN_BACKGROUND + Colors.ANSI_BLACK + " captured eagle" + Colors.ANSI_RESET + "\n");
        System.out.println(Colors.CYAN_BACKGROUND + Colors.ANSI_BLACK + "Points of tank " + Colors.ANSI_RESET + tank.getColor() + Colors.ANSI_BLACK + " ^ "
                + Colors.ANSI_RESET + Colors.CYAN_BACKGROUND + Colors.ANSI_BLACK + " : " + tank.getPoints() + Colors.ANSI_RESET + "\n");
    }

    private static void messageAboutDestroyedObject(Player player) {
        System.out.println("\n" + Colors.CYAN_BACKGROUND + Colors.ANSI_BLACK + "Tank " + Colors.ANSI_RESET + player.getTank().getColor() + Colors.ANSI_BLACK + " ^ "
                + Colors.ANSI_RESET + Colors.CYAN_BACKGROUND + Colors.ANSI_BLACK + " destroyed the object." + Colors.ANSI_RESET + "\n");
    }

    private void messageAboutDestroyedTank(int indexOfPlayer, Player player) {
        System.out.println("\n" + Colors.CYAN_BACKGROUND + Colors.ANSI_BLACK + "Tank " + Colors.ANSI_RESET + players.get(indexOfPlayer).getTank().getColor() + Colors.ANSI_BLACK + " ^ "
                + Colors.ANSI_RESET + Colors.CYAN_BACKGROUND + Colors.ANSI_BLACK + " destroyed tank " + Colors.ANSI_RESET + player.getTank().getColor() +Colors.ANSI_BLACK + " ^ " + Colors.ANSI_RESET + "\n");
    }

    private void messageAboutObstacle (int indexOfPlayer) {
        System.out.println("\n" + Colors.CYAN_BACKGROUND + Colors.ANSI_BLACK + "Tank " + Colors.ANSI_RESET + players.get(indexOfPlayer).getTank().getColor() + Colors.ANSI_BLACK + " ^ "
                + Colors.ANSI_RESET + Colors.CYAN_BACKGROUND + Colors.ANSI_BLACK + " can't go further, there's an obstacle ahead." + Colors.ANSI_RESET + "\n");
    }

    private void messageAboutWater(int indexOfPlayer) {
        System.out.println("\n" + Colors.CYAN_BACKGROUND + Colors.ANSI_BLACK + "Oops, tank " + Colors.ANSI_RESET + players.get(indexOfPlayer).getTank().getColor() + Colors.ANSI_BLACK + " ^ " +
                Colors.ANSI_RESET + Colors.CYAN_BACKGROUND + Colors.ANSI_BLACK + " hit in the water, it returns to the starting point." + Colors.ANSI_RESET + "\n");
    }
}