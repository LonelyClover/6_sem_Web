package ru.msu.cs.TheaterWeb.DAO;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Objects;

public class CommonFilter {
    public boolean isEmpty() {
        return Arrays.stream(this.getClass().getDeclaredFields())
                .peek(f -> f.setAccessible(true))
                .map(f -> getFieldValue(f, this))
                .allMatch(Objects::isNull);
    }

    private static Object getFieldValue(Field field, Object target) {
        try {
            return field.get(target);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }
}
