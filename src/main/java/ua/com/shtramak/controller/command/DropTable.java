package ua.com.shtramak.controller.command;

import ua.com.shtramak.model.DataBaseManager;
import ua.com.shtramak.util.Commands;
import ua.com.shtramak.view.View;

import java.util.Arrays;

public class DropTable implements Command {
    DataBaseManager dataBaseManager;
    View view;

    public DropTable(DataBaseManager dataBaseManager, View view) {
        this.dataBaseManager = dataBaseManager;
        this.view = view;
    }

    @Override
    public boolean isDetected(String command) {
        return command.startsWith("drop|");
    }

    @Override
    public void execute(String command) {

        String[] commands = Commands.arrayOf(command);
        if (commands.length != 2) {
            view.writeln("Incorrect usage of 'drop' command. Use 'help' command for details");
        }

        int tableNameIndex = 1;
        String tableName = commands[tableNameIndex];
        if (!dataBaseManager.hasTable(tableName)) {
            view.writeln(String.format("Table %s doesn't exists! See the list with available tables below:", tableName));
            view.writeln("ShowTablesList with available tables: " + Arrays.toString(dataBaseManager.getTableNames()));
            return;
        }

        String message = String.format("You are going to drop existing table '%s'! Are you sure? [Yes/No]", tableName);
        if (!Commands.isSureInActingWithTable(tableName, message, view)) {
            view.writeln("Command 'drop' was canceled...");
        }

        dataBaseManager.dropTable(tableName);
        view.writeln("The table was successfully dropped from database");
    }

    @Override
    public String description() {
        return "\tdrop|tableName" +
                System.lineSeparator() +
                "\t\tdrop an existing table in a database";
    }
}