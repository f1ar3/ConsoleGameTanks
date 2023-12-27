package ru.vsu.cs.zagorodnev_g_a.objects.movable;

import ru.vsu.cs.zagorodnev_g_a.field.Colors;

import java.util.ArrayList;
import java.util.List;

public class Tank extends MovableObject{
    private boolean isFire = false;
    private List<Bullet> bullets = new ArrayList<>();
    private String color;
    private int points = 0;
    private int pointsForKill = 1;
    private final Position startPosition = new Position(getPosition().x(), getPosition().y());

    private boolean undoStep = false;

    public Tank(Position position, MoveParameters mp) {
        super(position, mp);
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

    public boolean isUndoStep() {
        return undoStep;
    }

    public void setUndoStep(boolean undoStep) {
        this.undoStep = undoStep;
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
}
