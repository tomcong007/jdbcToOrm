package com.havaFun.transaction;

import java.io.Serializable;

public class SessionParams implements Serializable {
    private static final long serialVersionUID = 1L;
    private String sql;
    private Object[] args;

    public String getSql() {
        return this.sql;
    }

    public void setSql(String sql) {
        this.sql = sql;
    }

    public Object[] getArgs() {
        return this.args;
    }

    public void setArgs(Object[] args) {
        this.args = args;
    }

    public SessionParams(String sql, Object[] args) {
        this.sql = sql;
        this.args = args;
    }
}