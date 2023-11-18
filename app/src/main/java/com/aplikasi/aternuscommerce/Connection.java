package com.aplikasi.aternuscommerce;

public class Connection {
    private static final String IP = "10.0.2.2";
    private static final String DATABASE_NAME = "aternuscommerce_db";
    private static final String URL_SETUP = "http://" + IP + "/" + DATABASE_NAME + "/";

    public static String getUrlSetup() {
        return URL_SETUP;
    }
}
