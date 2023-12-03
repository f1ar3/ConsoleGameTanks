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
                        if (object instanceof Eagle) {
                            ((Eagle) object).setAlive(false);
                        } else {
                            objects.remove(object);
                            System.out.println("\n" + Colors.CYAN_BACKGROUND + Colors.ANSI_BLACK + "Player " + Colors.ANSI_RESET + players.get(indexOfPlayer).getTank().getColor() + Colors.ANSI_BLACK + " ^ "
                                    + Colors.ANSI_RESET + Colors.CYAN_BACKGROUND + Colors.ANSI_BLACK + " destroyed the object." + Colors.ANSI_RESET + "\n");
                        }
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
                            System.out.println("\n" + Colors.CYAN_BACKGROUND + Colors.ANSI_BLACK + "Tank " + Colors.ANSI_RESET + players.get(i).getTank().getColor() + Colors.ANSI_BLACK + " ^ "
                                    + Colors.ANSI_RESET + Colors.CYAN_BACKGROUND + Colors.ANSI_BLACK + " destroyed tank " + Colors.ANSI_RESET + value.getTank().getColor() +Colors.ANSI_BLACK + " ^ " + Colors.ANSI_RESET + "\n");
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
                    && (object instanceof Wall || object instanceof IndestructibleWall)) {
                object.setCollision(true);
                return true;
            }
        }
        return false;
    }

    public void moveInViewOfCollision(int changeX, int changeY, int numberOfPlayer) {
        for (int i = 0; i < players.size(); i++) {
            if (!isCollision(players.get(numberOfPlayer).getTank(), walls, changeX, changeY)
                    && !isCollision(players.get(numberOfPlayer).getTank(), indestructibleWalls, changeX, changeY)
                    && !tanksCollision(players.get(numberOfPlayer), players.get(i), changeX, changeY) && numberOfPlayer != i) {
                players.get(numberOfPlayer).getTank().move();
                return;
            }
        }
        System.out.println("\n" + Colors.CYAN_BACKGROUND + Colors.ANSI_BLACK + "You can't go further, there's an obstacle ahead." + Colors.ANSI_RESET + "\n");
    }

    private boolean tanksCollision(Player player1, Player player2, int changeX, int changeY) {
        if (player1.isCondition() && player2.isCondition()) {
            return player2.getTank().intersects(new Position(player1.getTank().getPosition().x() + changeX, player1.getTank().getPosition().y() + changeY));
        }
        return false;
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
            System.out.println("\n" + Colors.CYAN_BACKGROUND + Colors.ANSI_BLACK + "Oops, your tank hit in the water, you are returning to the starting point." + Colors.ANSI_RESET + "\n");
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

    public void timerBulletRunning(int indexOfPlayer, int indexOfCurrBullet){
        players.get(indexOfPlayer).getTank().getBullets().get(indexOfCurrBullet).move();
    }

    public void leftButton(int indexOfPlayer){
        turns.get(indexOfPlayer).setTurned(turns.get(indexOfPlayer).getDirection() == MoveDirections.LEFT);
        if (!turns.get(indexOfPlayer).isTurned()) {
            players.get(indexOfPlayer).getTank().turn(MoveDirections.LEFT);
            turns.get(indexOfPlayer).setDirection(MoveDirections.LEFT);
        } else {
            if (players.get(indexOfPlayer).isCondition()) {
                moveInViewOfCollision(-velocity, 0, indexOfPlayer);
                backToStartPosition(indexOfPlayer);
            }
        }
    }

    public void rightButton(int indexOfPlayer){
        turns.get(indexOfPlayer).setTurned(turns.get(indexOfPlayer).getDirection() == MoveDirections.RIGHT);
        if (!turns.get(indexOfPlayer).isTurned()) {
            players.get(indexOfPlayer).getTank().turn(MoveDirections.RIGHT);
            turns.get(indexOfPlayer).setDirection(MoveDirections.RIGHT);
        } else {
            if (players.get(indexOfPlayer).isCondition()) {
                moveInViewOfCollision(velocity, 0, indexOfPlayer);
                backToStartPosition(indexOfPlayer);
            }
        }
    }

    public void upButton(int indexOfPlayer){
        turns.get(indexOfPlayer).setTurned(turns.get(indexOfPlayer).getDirection() == MoveDirections.UP);
        if (!turns.get(indexOfPlayer).isTurned()) {
            players.get(indexOfPlayer).getTank().turn(MoveDirections.UP);
            turns.get(indexOfPlayer).setDirection(MoveDirections.UP);
        } else {
            if (players.get(indexOfPlayer).isCondition()) {
                moveInViewOfCollision(0, -velocity, indexOfPlayer);
                backToStartPosition(indexOfPlayer);
            }
        }
    }

    public void downButton(int indexOfPlayer){
        turns.get(indexOfPlayer).setTurned(turns.get(indexOfPlayer).getDirection() == MoveDirections.DOWN);
        if (!turns.get(indexOfPlayer).isTurned()) {
            players.get(indexOfPlayer).getTank().turn(MoveDirections.DOWN);
            turns.get(indexOfPlayer).setDirection(MoveDirections.DOWN);
        } else {
            if (players.get(indexOfPlayer).isCondition()) {
                moveInViewOfCollision(0, velocity, indexOfPlayer);
                backToStartPosition(indexOfPlayer);
            }
        }
    }

    public void fireButton(int indexOfPlayer){
        players.get(indexOfPlayer).getTank().shoot();
    }

}
