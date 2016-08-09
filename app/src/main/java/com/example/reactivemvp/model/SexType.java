package com.example.reactivemvp.model;

import android.support.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Sex Types are define here.
 */
@IntDef({SexType.UNKNOWN, SexType.MALE, SexType.FEMALE})
@Retention(RetentionPolicy.SOURCE)
@SuppressWarnings("checkstyle:abbreviationaswordinname")
public @interface SexType {
    int UNKNOWN = 1;
    int MALE = 2;
    int FEMALE = 3;
}
