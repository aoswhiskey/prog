package utility;
/**
 * Консоль для ввода команд и вывода результата.
 */
public interface Console {
    void printError(Object obj);
    void print(Object obj);
    void println(Object obj);
    String readln();
    void printTable(Object obj1, Object obj2);
    void prompt();
    String getPrompt();
}
