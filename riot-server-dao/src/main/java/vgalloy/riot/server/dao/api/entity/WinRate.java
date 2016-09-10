package vgalloy.riot.server.dao.api.entity;

import java.util.Objects;

/**
 * @author Vincent Galloy
 *         Created by Vincent Galloy on 09/09/16.
 */
public class WinRate {

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
