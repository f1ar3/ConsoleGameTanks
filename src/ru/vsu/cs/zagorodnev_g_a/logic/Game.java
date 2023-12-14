package ru.vsu.cs.zagorodnev_g_a.logic;

import ru.vsu.cs.zagorodnev_g_a.field.Colors;
import ru.vsu.cs.zagorodnev_g_a.objects.immovable.*;
import ru.vsu.cs.zagorodnev_g_a.player.Player;
import ru.vsu.cs.zagorodnev_g_a.objects.BattleFieldObject;
import ru.vsu.cs.zagorodnev_g_a.objects.movable.Bullet;
import ru.vsu.cs.zagorodnev_g_a.objects.movable.MoveDirections;
import ru.vsu.cs.zagorodnev_g_a.objects.movable.Position;
import ru.vsu.cs.zagorodnev_g_a.objects.movable.Tank;

import java.util.ArrayList;
import java.util.List;

public class Game {
    private List<Player> players = new ArrayList<>();
    private List<BattleFieldObject> tanks = new ArrayList<>();
    private List<BattleFieldObject> tiles = new ArrayList<>();
    private List<BattleFieldObject> walls = new ArrayList<>();
    private List<BattleFieldObject> indestructibleWalls = new ArrayList<>();
    private List<BattleFieldObject> water = new ArrayList<>();
    private List<BattleFieldObject> forest = new ArrayList<>();
    private List<BattleFieldObject> eagles = new ArrayList<>();
    private List<Turn> turns = new ArrayList<>();
    private final int velocity = 1;
    private boolean condition;
    private boolean gameWasFinished = false;

    public List<Turn> getTurns() {
        return turns;
    }

    public void setTurns(List<Turn> turns) {
        this.turns = turns;
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

    public List<Player> getPlayers() {
        return players;
    }

    public void setPlayers(List<Player> players) {
        this.players = players;
    }

    public List<BattleFieldObject> getTanks() {
        return tanks;
    }

    public void setTanks(List<BattleFieldObject> tanks) {
        this.tanks = tanks;
    }
    public List<BattleFieldObject> getTiles() {return tiles;}
    public void setTiles(List<BattleFieldObject> tiles) {this.tiles = tiles;}
    public List<BattleFieldObject> getWalls() {
        return walls;
    }

    public void setWalls(List<BattleFieldObject> walls) {
        this.walls = walls;
    }

    public List<BattleFieldObject> getIndestructibleWalls() {
        return indestructibleWalls;
    }

    public void setIndestructibleWalls(List<BattleFieldObject> indestructibleWalls) {
        this.indestructibleWalls = indestructibleWalls;
    }

    public boolean isGameWasFinished() {
        return gameWasFinished;
    }

    public void setGameWasFinished(boolean gameWasFinished) {
        this.gameWasFinished = gameWasFinished;
    }

    public List<BattleFieldObject> getWater() {
        return water;
    }

    public void setLakes(List<BattleFieldObject> water) {
        this.water = water;
    }

    public List<BattleFieldObject> getForest() {
        return forest;
    }

    public void setForest(List<BattleFieldObject> forest) {
        this.forest = forest;
    }

    public List<BattleFieldObject> getEagles() {
        return eagles;
    }

    public void setEagles(List<BattleFieldObject> eagles) {
        this.eagles = eagles;
    }


    private void destroySeparatedObjects(List<BattleFieldObject> objects, int indexOfPlayer) {
        for (int i = 0; i < players.get(indexOfPlayer).getTank().getBullets().size(); i++) {
            for (BattleFieldObject object : objects) {
                if (object.intersects(players.get(indexOfPlayer).getTank().getBullets().get(i).getPosition())) {
                    if (!(object instanceof IndestructibleWall)) {
                            objects.remove(object);
                            messageAboutDestroyedObject(indexOfPlayer);
                    }
                    players.get(indexOfPlayer).getTank().getBullets().remove(i);
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
                destroySeparatedObjects(walls, i);
                destroySeparatedObjects(indestructibleWalls, i);
                destroySeparatedObjects(eagles, i);
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

    private boolean isCollision(Tank tank, List<BattleFieldObject> list, int changeX, int changeY) {
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
//                victory(players, eagles);
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

    private void eagleCapture(Tank tank, List<BattleFieldObject> eagles, int changeX, int changeY) {
        for (BattleFieldObject eagle : eagles) {
            if (eagle.intersects(new Position(tank.getPosition().x() + changeX, tank.getPosition().y() + changeY)) && eagle instanceof Eagle) {
                ((Eagle) eagle).setAlive(false);
                tank.setPoints(tank.getPoints() + ((Eagle) eagle).getPointsForEagle());
                updatePointsForEagle(tank);
            }
        }
    }

    private boolean isInWater(Tank tank, List<BattleFieldObject> list) {
        for (BattleFieldObject object : list) {
            if (object instanceof Water && object.intersects(new Position(tank.getPosition().x(), tank.getPosition().y()))) {
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

    private boolean isInForest(Tank tank, List<BattleFieldObject> list) {
        for (BattleFieldObject object : list) {
            if (object instanceof Forest && object.intersects(new Position(tank.getPosition().x(), tank.getPosition().y()))) {
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

    private void maxNumberOfPoints(List<Player> players) {
        int maxNumberOfPoints = 0;
        int indexOfPlayer = 0;
        for (int i = 0; i < players.size(); i++) {
            System.out.println("Points of tank " + players.get(i).getTank().getColor() + " ^ " + players.get(i).getTank().getPoints() + Colors.ANSI_RESET);
            if (maxNumberOfPoints < players.get(i).getTank().getPoints()) {
                maxNumberOfPoints = players.get(i).getTank().getPoints();
                i = indexOfPlayer;
            }
        }
        System.out.println("The winner is" + players.get(indexOfPlayer).getTank().getColor() + " ^ " + players.get(indexOfPlayer).getTank().getPoints() + Colors.ANSI_RESET);
    }

    public void victory(List<Player> players, List<BattleFieldObject> eagles) {
        for (int i = 0; i < eagles.size(); i++) {
            if (!((Eagle) eagles.get(i)).isAlive()) {
                gameWasFinished = true;
                maxNumberOfPoints(players);
                eagles.remove(i);
            }
        }
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

    private void messageAboutDestroyedObject(int indexOfPlayer) {
        System.out.println("\n" + Colors.CYAN_BACKGROUND + Colors.ANSI_BLACK + "Tank " + Colors.ANSI_RESET + players.get(indexOfPlayer).getTank().getColor() + Colors.ANSI_BLACK + " ^ "
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
