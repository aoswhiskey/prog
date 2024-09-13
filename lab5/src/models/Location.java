package models;
/**
 * Класс локации.
 */
public class Location {
    private final Double x; //Поле не может быть null
    private final Long y; //Поле не может быть null
    private final Integer z; //Поле не может быть null
    private final String name; //Поле может быть null

    public Location(Double x, Long y, Integer z, String name) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.name = name;
    }
    @Override
    public String toString() {
        String info = "";
        info += "\n          x: " + x;
        info += "\n          y: " + y;
        info += "\n          z: " + z;
        info += "\n          Наименование локации: " + name;
        return info;
    }
}
