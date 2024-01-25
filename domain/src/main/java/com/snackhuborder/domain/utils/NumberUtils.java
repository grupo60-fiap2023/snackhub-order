package com.snackhuborder.domain.utils;

import java.math.BigDecimal;

public class NumberUtils {

    private NumberUtils() {
        throw new IllegalStateException("Utility class");
    }

    public static boolean isNegative (BigDecimal b) {
        return b.signum () == -1;
    }
}
