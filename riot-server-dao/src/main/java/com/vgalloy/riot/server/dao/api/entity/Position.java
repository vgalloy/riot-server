package com.vgalloy.riot.server.dao.api.entity;

import java.io.Serializable;
import java.util.Objects;

/**
 * Created by Vincent Galloy on 24/03/17.
 *
 * @author Vincent Galloy
 */
public final class Position implements Serializable {

    private static final long serialVersionUID = 5956726925713228155L;

    private int x;
    private int y;

    /**
     * Constructor.
     *
     * @param x the position in x
     * @param y the position in y
     */
    public Position(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Position)) {
            return false;
        }
        Position position = (Position) o;
        return x == position.x &&
            y == position.y;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }

    @Override
    public String toString() {
        return "Position{" +
            "x=" + x +
            ", y=" + y +
            '}';
    }
}
