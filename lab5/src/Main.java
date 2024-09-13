import java.util.Scanner;

import commands.*;
import managers.CollectionManager;
import managers.CommandManager;
import managers.DumpManager;
import utility.Interrogator;
import utility.Runner;
import utility.StandardConsole;
public class Main {
    public static void main(String[] args) {
        Interrogator.setUserScanner(new Scanner(System.in));
        var console = new StandardConsole();

        if (args.length == 0) {
            console.println("Введите имя загружаемого файла как аргумент командной строки");
            System.exit(1);
        }

        console.println("Вечер в хату!");
//        String FILENAME = "file.json";

        DumpManager dumpManager = new DumpManager(args[0], console);
        CollectionManager collectionManager = new CollectionManager(dumpManager);
        CommandManager commandManager = new CommandManager() {{
            register("help", new Help(console, this));
            register("info", new Info(console, collectionManager));
            register("show", new Show(console, collectionManager));
            register("add", new Add(console, collectionManager));
            register("update", new Update(console, collectionManager));
            register("remove", new Remove(console, collectionManager));
            register("clear", new Clear(console, collectionManager));
            register("save", new Save(console, collectionManager));
            register("execute_script", new ExecuteScript(console));
            register("exit", new Exit(console));
            register("remove_first", new RemoveFirst(console, collectionManager));
            register("head", new Head(console, collectionManager));
            register("remove_at", new RemoveAt(console, collectionManager));
            register("max_by_minimal_point", new MaxByMinimalPoint(console, collectionManager));
            register("filter_less_than_author", new FilterLessThanAuthor(console, collectionManager));
            register("print_field_descending_difficulty", new PrintFieldDescendingDifficulty(console, collectionManager));
        }};

        new Runner(console, commandManager).interactiveMode();
    }
}

