package com.vgalloy.riot.server.dao.api.entity;

import java.io.Serializable;
import java.util.Objects;

/**
 * Created by Vincent Galloy on 09/09/16.
 *
 * @author Vincent Galloy
 */
public final class WinRate implements Serializable {

    private static final long serialVersionUID = 2798464362605793360L;

    private final int win;
    private final int lose;

    /**
     * Constructor.
     *
     * @param win  the number of win
     * @param lose the number of lose
     */
    public WinRate(int win, int lose) {
        this.win = win;
        this.lose = lose;
    }

    public int getWin() {
        return win;
    }

    public int getLose() {
        return lose;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        WinRate winRate = (WinRate) o;
        return win == winRate.win &&
            lose == winRate.lose;
    }

    @Override
    public int hashCode() {
        return Objects.hash(win, lose);
    }

    @Override
    public String toString() {
        return "WinRate{" +
            "win=" + win +
            ", lose=" + lose +
            '}';
    }
}
