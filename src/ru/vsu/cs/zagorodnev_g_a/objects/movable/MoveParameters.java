package ru.vsu.cs.zagorodnev_g_a.objects.movable;

public class MoveParameters {
    private MoveDirections direction = MoveDirections.NONE;
    private final int velocity;

    public MoveParameters(int velocity) {
        this.velocity = velocity;
    }

    public MoveDirections getDirection() {
        return direction;
    }

    public void setDirection(MoveDirections direction) {
        this.direction = direction;
    }

    public int getVelocity() {
        return velocity;
    }

    public int aboveOfX(MovableObject movableObject) {
        return movableObject.getY() - this.velocity;
    }

    public int belowOfX(MovableObject movableObject) {
        return movableObject.getY() + this.velocity;
    }

    public int leftOfX(MovableObject movableObject) {
        return movableObject.getX() - this.velocity;
    }

    public int rightOfX(MovableObject movableObject) {
        return movableObject.getX() + this.velocity;
    }

}
