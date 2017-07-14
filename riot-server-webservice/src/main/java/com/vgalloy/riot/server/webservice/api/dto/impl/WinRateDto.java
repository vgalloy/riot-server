package com.vgalloy.riot.server.webservice.api.dto.impl;

import java.util.Objects;

import com.vgalloy.riot.server.webservice.api.dto.Dto;

/**
 * Created by Vincent Galloy on 01/05/17.
 *
 * @author Vincent Galloy
 */
public class WinRateDto implements Dto {

    private static final long serialVersionUID = 1172498940795727401L;

    private Integer win;
    private Integer lose;

    public Integer getWin() {
        return win;
    }

    public void setWin(Integer win) {
        this.win = win;
    }

    public Integer getLose() {
        return lose;
    }

    public void setLose(Integer lose) {
        this.lose = lose;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        WinRateDto that = (WinRateDto) o;
        return Objects.equals(win, that.win) &&
            Objects.equals(lose, that.lose);
    }

    @Override
    public int hashCode() {
        return Objects.hash(win, lose);
    }
}
