package ru.msu.cs.TheaterWeb.entities;

public enum PlaceType {
    LODGE,
    PARTERRE,
    AMPHITHEATER,
    MEZZANINE,
    BALCONY;

    public String toString() {
        return switch (this) {
            case LODGE -> "Лоджия";
            case PARTERRE -> "Партер";
            case AMPHITHEATER -> "Амфитеатр";
            case MEZZANINE -> "Бельэтаж";
            case BALCONY -> "Балкон";
        };
    }

    public static PlaceType fromLong(Long value) {
        if (value == 0L) {
            return LODGE;
        } else if (value == 1L) {
            return PARTERRE;
        } else if (value == 2L) {
            return AMPHITHEATER;
        } else if (value == 3L) {
            return MEZZANINE;
        } else if (value == 4L) {
            return BALCONY;
        } else {
            return null;
        }
    }
}