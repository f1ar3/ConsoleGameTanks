package ru.vsu.cs.zagorodnev_g_a.objects.movable;

import ru.vsu.cs.zagorodnev_g_a.objects.BattleFieldObject;
import ru.vsu.cs.zagorodnev_g_a.objects.Destroyable;

    public class MovableObject extends BattleFieldObject implements Destroyable {
        private MoveParameters mp;

        public MovableObject(Position position, MoveParameters mp) {
            super(position);
            this.mp = mp;
        }

        public MoveParameters getMp() {
            return mp;
        }

        public void move() {
            if (this.getMp().getDirection() == MoveDirections.UP) {
                this.setPosition(new Position(this.getPosition().x(), this.getPosition().y() - this.getMp().getVelocity()));
            } else if (this.getMp().getDirection() == MoveDirections.DOWN) {
                this.setPosition(new Position(this.getPosition().x(), this.getPosition().y() + this.getMp().getVelocity()));
            } else if (this.getMp().getDirection() == MoveDirections.LEFT) {
                this.setPosition(new Position(this.getPosition().x() - this.getMp().getVelocity(), this.getPosition().y()));
            } else if (this.getMp().getDirection() == MoveDirections.RIGHT) {
                this.setPosition(new Position(this.getPosition().x() + this.getMp().getVelocity(), this.getPosition().y()));
            }
        }

        public void setMp(MoveParameters mp) {
            this.mp = mp;
        }

    }

