package utility;
/**
 * Интерфейс для всех выполняемых команд.
 */
public interface Executable {
    /**
     * Выполняет что-либо.
     * @param arguments Аргумент для выполнения
     * @return результат выполнения
     */
    boolean execute(String[] arguments);
}