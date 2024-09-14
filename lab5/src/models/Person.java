package models;

import java.time.LocalDateTime;

/**
 * Класс человека.
 */
public class Person implements Comparable<Person> {
    private final String name; //Поле не может быть null, Строка не может быть пустой
    private final java.time.LocalDateTime birthday; //Поле не может быть null
    private final Float height; //Поле может быть null, Значение поля должно быть больше 0
    private final Location location; //Поле не может быть null

    public Person(String name, java.time.LocalDateTime birthday, Float height,
                  Location location) {
        this.name = name;
        this.birthday = birthday;
        this.height = height;
        this.location = location;
    }
    @Override
    public String toString() {
        String info = "";
        info += "\n     Имя: " + name;
        info += "\n     Дата рождения: " + ((birthday == null) ? null :  birthday.toString());
        info += "\n     Рост: " + height;
        info += "\n     Местоположение:" + location;
        return info;
    }
    @Override
    public int compareTo(Person other) {
        return this.name.compareTo(other.name);
    }

    public String getName() {
        return name;
    }

    public LocalDateTime getBirthday() {
        return birthday;
    }

    public Float getHeight() {
        return height;
    }

    public Location getLocation() {
        return location;
    }
}