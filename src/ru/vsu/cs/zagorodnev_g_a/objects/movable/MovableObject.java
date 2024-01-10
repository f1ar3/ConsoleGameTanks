package ru.vsu.cs.zagorodnev_g_a.objects.movable;

import ru.vsu.cs.zagorodnev_g_a.objects.BattleFieldObject;
import ru.vsu.cs.zagorodnev_g_a.objects.Destroyable;

import java.io.Serializable;

public class MovableObject extends BattleFieldObject implements Destroyable, Serializable {
        private MoveParameters mp;

        public MovableObject(Position position, MoveParameters mp) {
            super(position);
            this.mp = mp;
        }

        public MoveParameters getMp() {
            return mp;
        }

        public int getX() {
            return this.getPosition().x();
        }

        public int getY() {
            return this.getPosition().y();
        }

        public void move() {
            if (this.getMp().getDirection() == MoveDirections.UP) {
                this.setPosition(new Position(getX(), getMp().aboveOfX(this)));
            } else if (this.getMp().getDirection() == MoveDirections.DOWN) {
                this.setPosition(new Position(getX(), getMp().belowOfX(this)));
            } else if (this.getMp().getDirection() == MoveDirections.LEFT) {
                this.setPosition(new Position(getMp().leftOfX(this), getY()));
            } else if (this.getMp().getDirection() == MoveDirections.RIGHT) {
                this.setPosition(new Position(getMp().rightOfX(this), getY()));
            }
        }

        public void setMp(MoveParameters mp) {
            this.mp = mp;
        }

    }

