package vgalloy.riot.server.webservice.api.dto.impl;

import vgalloy.riot.server.webservice.api.dto.Dto;

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
}
