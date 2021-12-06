package com.example.hello.utils;

import android.content.Context;

import com.example.hello.data.source.DataSource;
import com.example.hello.data.source.Repository;

public final class Dependency {
    private static volatile Dependency instance = null;

    Context context;

    DataSource dataSource;

    public static Dependency getInstance(Context context) {
        if (instance == null) {
            synchronized (Dependency.class) {
                if (instance == null) {
                    instance = new Dependency(context);
                }
            }

        }
        return instance;
    }

    private Dependency(Context context) {
        this.context = context;
        this.dataSource = new Repository(context);
    }

    public DataSource getDataSource() {
        return dataSource;
    }
}
