package org.itachi.cms.dto;

import java.io.Serializable;

/**
 * Created by itachi on 2017/3/19.
 * User: itachi
 * Date: 2017/3/19
 * Time: 13:17
 */
public class PagerDTO implements Serializable {
    private int page ;
    private int rows  ;
    private int begin ;

    public PagerDTO(int page, int rows) {
        this.page = page;
        this.rows = rows;
        this.begin = (this.page - 1) * this.rows;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getRows() {
        return rows;
    }

    public void setRows(int rows) {
        this.rows = rows;
    }

    public int getBegin() {

        return begin;
    }

    public void setBegin(int begin) {
        this.begin = begin;
    }
}
