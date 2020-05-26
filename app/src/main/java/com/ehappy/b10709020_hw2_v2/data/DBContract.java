package com.ehappy.b10709020_hw2_v2.data;

import android.provider.BaseColumns;

public class DBContract {
    public static final class DBEntry implements BaseColumns {
        public static final String T_NAME = "party";
        public static final String COLUMN_NAME = "Name";
        public static final String COLUMN_PEOPLE = "People";
        public static final String COLUMN_TIMESTAMP = "timestamp";
    }
}