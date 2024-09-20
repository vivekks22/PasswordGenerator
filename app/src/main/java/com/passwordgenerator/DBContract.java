package com.passwordgenerator;

import android.provider.BaseColumns;

public final class DBContract {

    private DBContract() {}

    public static class PasswordEntry implements BaseColumns {
        public static final String TABLE_NAME = "passwords";
        public static final String COLUMN_NAME = "name";
        public static final String COLUMN_PASSWORD = "password";
    }
}
