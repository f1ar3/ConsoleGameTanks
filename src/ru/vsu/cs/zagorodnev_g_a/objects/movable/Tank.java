package ru.vsu.cs.zagorodnev_g_a.objects.movable;

import java.util.ArrayList;
import java.util.List;

public class Tank extends MovableObject{
    private boolean isFire = false;
    private List<Bullet> bullets = new ArrayList<>();
    private String color;
    private int points = 0;
    private int pointsForKill = 1;
    private final Position startPosition = new Position(getPosition().x(), getPosition().y());

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
            this.getBullets().add(new Bullet(new Position(this.getPosition().x() - this.getMp().getVelocity(), this.getPosition().y()), new MoveParameters(this.getMp().getVelocity())));
        } else if (this.getMp().getDirection() == MoveDirections.RIGHT) {
            this.getBullets().add(new Bullet(new Position(this.getPosition().x() + this.getMp().getVelocity(), this.getPosition().y()), new MoveParameters(this.getMp().getVelocity())));
        } else if (this.getMp().getDirection() == MoveDirections.UP) {
            this.getBullets().add(new Bullet(new Position(this.getPosition().x(), this.getPosition().y() - this.getMp().getVelocity()), new MoveParameters(this.getMp().getVelocity())));
        } else if (this.getMp().getDirection() == MoveDirections.DOWN) {
            this.getBullets().add(new Bullet(new Position(this.getPosition().x(), this.getPosition().y() + this.getMp().getVelocity()), new MoveParameters(this.getMp().getVelocity())));
        }
        this.getBullets().get(this.getBullets().size() - 1).getMp().setDirection(this.getMp().getDirection());
    }

    public void turn(MoveDirections direction) {
        this.getMp().setDirection(direction);
    }
}
