package com.example.reactivemvp.model;

import android.support.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * List Types are define here.
 */
@IntDef({ListType.LIST, ListType.GRID})
@Retention(RetentionPolicy.SOURCE)
@SuppressWarnings("checkstyle:abbreviationaswordinname")
public @interface ListType {
    int LIST = 1;
    int GRID = 2;
}
