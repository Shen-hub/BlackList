package com.company;

public class BlackList {
    String date;
    String url;
    String domain;
    String ip;

    BlackList (String date, String url, String domain, String ip) {
        this.date = date;
        this.url = url;
        this.domain = domain;
        this.ip = ip;
    }
}
