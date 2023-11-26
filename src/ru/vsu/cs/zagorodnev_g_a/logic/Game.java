package ru.vsu.cs.zagorodnev_g_a.logic;

import ru.vsu.cs.zagorodnev_g_a.player.Player;
import ru.vsu.cs.zagorodnev_g_a.objects.BattleFieldObject;
import ru.vsu.cs.zagorodnev_g_a.objects.immovable.Eagle;
import ru.vsu.cs.zagorodnev_g_a.objects.immovable.IndestructibleWall;
import ru.vsu.cs.zagorodnev_g_a.objects.movable.Bullet;
import ru.vsu.cs.zagorodnev_g_a.objects.movable.MoveDirections;
import ru.vsu.cs.zagorodnev_g_a.objects.movable.Position;
import ru.vsu.cs.zagorodnev_g_a.objects.movable.Tank;

import java.util.ArrayList;
import java.util.List;

public class Game {
    private List<Player> players = new ArrayList<>();
    private List<BattleFieldObject> tanks = new ArrayList<>();
    private List<BattleFieldObject> walls = new ArrayList<>();
    private List<BattleFieldObject> indestructibleWalls = new ArrayList<>();
    private List<BattleFieldObject> lakes = new ArrayList<>();
    private List<BattleFieldObject> thickets = new ArrayList<>();
    private List<BattleFieldObject> eagles = new ArrayList<>();
    private List<Turn> turns = new ArrayList<>();
    private final int velocity = 50;
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

    public List<BattleFieldObject> getLakes() {
        return lakes;
    }

    public void setLakes(List<BattleFieldObject> lakes) {
        this.lakes = lakes;
    }

    public List<BattleFieldObject> getThickets() {
        return thickets;
    }

    public void setThickets(List<BattleFieldObject> thickets) {
        this.thickets = thickets;
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

    private boolean isLayering(Tank tank, List<BattleFieldObject> list, int changeX, int changeY) {
        for (BattleFieldObject object : list) {
            if (object.intersects(new Position(tank.getPosition().x() + changeX, tank.getPosition().y() + changeY))) {
                return false;
            }
        }
        return true;
    }

    public void moveInViewOfLayering(int changeX, int changeY, int numberOfPlayer) {
        for (int j = 0; j < players.size(); j++) {
            if (isLayering(players.get(numberOfPlayer).getTank(), walls, changeX, changeY)
                    && isLayering(players.get(numberOfPlayer).getTank(), indestructibleWalls, changeX, changeY)
                    && isLayering(players.get(numberOfPlayer).getTank(), lakes, changeX, changeY)
                    && isLayering(players.get(numberOfPlayer).getTank(), eagles, changeX, changeY)
                    && !pairTankLayering(players.get(numberOfPlayer), players.get(j), changeX, changeY) && numberOfPlayer != j) {
                players.get(numberOfPlayer).getTank().move();
            }
        }
    }

    private boolean pairTankLayering(Player player1, Player player2, int changeX, int changeY) {
        if (player1.isCondition() && player2.isCondition()) {
            return player2.getTank().intersects(new Position(player1.getTank().getPosition().x() + changeX, player1.getTank().getPosition().y() + changeY));
        }
        return false;
    }

    public void restart(){
        players.clear();
        tanks.clear();
        walls.clear();
        indestructibleWalls.clear();
        lakes.clear();
        thickets.clear();
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
                moveInViewOfLayering(-velocity, 0, indexOfPlayer);
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
                moveInViewOfLayering(velocity, 0, indexOfPlayer);
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
                moveInViewOfLayering(0, -velocity, indexOfPlayer);
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
                moveInViewOfLayering(0, velocity, indexOfPlayer);
            }
        }
    }

    public void fireButton(int indexOfPlayer){
        players.get(indexOfPlayer).getTank().shoot();
    }

}
