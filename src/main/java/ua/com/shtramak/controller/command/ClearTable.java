package ua.com.shtramak.controller.command;

import ua.com.shtramak.model.DataBaseManager;
import ua.com.shtramak.utils.Commands;
import ua.com.shtramak.view.View;

import java.util.Arrays;

public class ClearTable implements Command {

    private DataBaseManager dataBaseManager;
    private View view;

    public ClearTable(DataBaseManager dataBaseManager, View view) {
        this.dataBaseManager = dataBaseManager;
        this.view = view;
    }

    @Override
    public String description() {
        return "\tclear|tableName" +
                System.lineSeparator() +
                "\t\tdelete all data from selected table";
    }

    @Override
    public boolean isDetected(String command) {
        return command.startsWith("clear|");
    }

    @Override
    public void execute(String command) {
        final String[] commandsTemplate = Commands.arrayOf("clear|tableName");
        String[] commands = Commands.arrayOf(command);
        if (commands.length != commandsTemplate.length) {
            view.writeln("'clear' command failed because of wrong input. Use 'help' command for details");
            return;
        }

        int tableNameIndex = 1;
        String tableName = commands[tableNameIndex];

        if (!dataBaseManager.hasTable(tableName)) {
            view.writeln(String.format("Table %s doesn't exists! See the list with available tables: %s", tableName, Arrays.toString(dataBaseManager.getTableNames())));
            return;
        }

        String message = String.format("You are going to delete all data from table '%s'! Are you sure? [Yes/No]", tableName);
        if (!Commands.isSureInActingWithTable(tableName, message, view)) {
            view.writeln("Command 'clear' was canceled...");
            return;
        }

        dataBaseManager.clear(tableName);
        view.writeln(String.format("Data from '%s' was successfully deleted", tableName));
    }
}
