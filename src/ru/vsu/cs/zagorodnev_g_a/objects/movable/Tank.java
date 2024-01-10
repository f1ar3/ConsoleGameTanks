package ru.vsu.cs.zagorodnev_g_a.objects.movable;

import ru.vsu.cs.zagorodnev_g_a.field.Colors;
import ru.vsu.cs.zagorodnev_g_a.objects.BattleFieldObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Tank extends MovableObject implements Serializable {
    private boolean isFire = false;
    private List<Bullet> bullets = new ArrayList<>();
    private String color;
    private int points = 0;
    private int pointsForKill = 1;
    private final Position startPosition = new Position(getPosition().x(), getPosition().y());

    public Tank(Position position, MoveParameters mp) {
        super(position, mp);
    }

    public static class TankBackUp extends Tank{

        private Position pos;

        private int points;

        private TankBackUp(Position pos, MoveParameters mp, int points) {
            super(pos, mp);
            this.pos = pos;
            this.points = points;
        }
    }

    public TankBackUp createBackUp() {
        return new TankBackUp(new Position(this.getX(), this.getY()), this.getMp(), getPoints());
    }

    public void loadBackUp(TankBackUp backUp) {
        this.points = backUp.points;
        this.position = new Position(backUp.pos.x(), backUp.pos.y());
    }

    public Position getStartPosition() {
        return startPosition;
    }

    public boolean isFire() {
        return isFire;
    }

    public void setFire(boolean fire) {
        isFire = fire;
    }

    public List<Bullet> getBullets() {
        return bullets;
    }

    public void setBullets(List<Bullet> bullets) {
        this.bullets = bullets;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }
    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public int getPointsForKill() {
        return pointsForKill;
    }

    public void setPointsForKill(int pointsForKill) {
        this.pointsForKill = pointsForKill;
    }

    public void shoot() {
        isFire = true;
        if (this.getMp().getDirection() == MoveDirections.LEFT) {
            this.getBullets().add(new Bullet(new Position(getMp().leftOfX(this), getY()), new MoveParameters(this.getMp().getVelocity())));
        } else if (this.getMp().getDirection() == MoveDirections.RIGHT) {
            this.getBullets().add(new Bullet(new Position(getMp().rightOfX(this), getY()), new MoveParameters(this.getMp().getVelocity())));
        } else if (this.getMp().getDirection() == MoveDirections.UP) {
            this.getBullets().add(new Bullet(new Position(getX(), getMp().aboveOfX(this)), new MoveParameters(this.getMp().getVelocity())));
        } else if (this.getMp().getDirection() == MoveDirections.DOWN) {
            this.getBullets().add(new Bullet(new Position(getX(), getMp().belowOfX(this)), new MoveParameters(this.getMp().getVelocity())));
        }
        this.getBullets().getLast().getMp().setDirection(this.getMp().getDirection());
    }

    public void turn(MoveDirections direction) {
        this.getMp().setDirection(direction);
    }

    @Override
    public String toString() {
        if (this.getMp().getDirection() == MoveDirections.LEFT) {
            return this.getColor() + Colors.ANSI_BLACK + " < " + Colors.ANSI_RESET;
        } else if (this.getMp().getDirection() == MoveDirections.RIGHT) {
            return this.getColor() + Colors.ANSI_BLACK + " > " + Colors.ANSI_RESET;
        } else if (this.getMp().getDirection() == MoveDirections.UP) {
            return this.getColor() + Colors.ANSI_BLACK + " ^ " + Colors.ANSI_RESET;
        } else if (this.getMp().getDirection() == MoveDirections.DOWN) {
        return this.getColor() + Colors.ANSI_BLACK + " v " + Colors.ANSI_RESET;
        }
        return null;
    }

    public BattleFieldObject clone() throws CloneNotSupportedException {
        Tank clonedTank = new Tank(this.position, this.getMp());
        clonedTank.setPoints(this.getPoints());
        clonedTank.setBullets(this.bullets);
        clonedTank.setColor(this.getColor());
        clonedTank.setFire(this.isFire);
        clonedTank.setPointsForKill(this.getPointsForKill());
        return clonedTank;
    }
}
