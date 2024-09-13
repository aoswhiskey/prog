package models;
/**
 * Класс сложности.
 */
public enum Difficulty {
    VERY_EASY,
    HARD,
    VERY_HARD,
    INSANE,
    HOPELESS;
    /**
     * @return список сложности.
     */
    public static String names() {
        StringBuilder nameList = new StringBuilder();
        for (var difficulty : values()) {
            nameList.append(difficulty.name()).append(", ");
        }
        return nameList.substring(0, nameList.length()-2);
    }
}
