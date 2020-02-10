package com.sleep.common;

import androidx.annotation.NonNull;

/**
 * author：xingkong on 2020-02-10
 * e-mail：xingkong@changjinglu.net
 */
public class TagUtil {
    @NonNull
    public static String getTag(@NonNull Object o) {
        return o.getClass().getSimpleName();
    }
}
