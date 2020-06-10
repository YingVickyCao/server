package com.example.hades.springbootserver;

public class LoginResult {
    String status;
    long ts;

    public LoginResult(String status, long ts) {
        this.status = status;
        this.ts = ts;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public long getTs() {
        return ts;
    }

    public void setTs(long ts) {
        this.ts = ts;
    }
}
