package ua.com.shtramak.sqlcmd.controller.command;

import ua.com.shtramak.sqlcmd.model.DataBaseManager;
import ua.com.shtramak.sqlcmd.utils.Commands;
import ua.com.shtramak.sqlcmd.view.View;

public abstract class AbstractCommand {
    protected DataBaseManager dataBaseManager;
    protected View view;
    private CommandType commandType;

    public AbstractCommand(CommandType commandType) {
        this.commandType = commandType;
    }

    public String description(){
        return commandType.description();
    }

    public boolean isDetected(String command) {
        if (command == null) return false;
        String[] splittedCommand = Commands.arrayOf(command);
        return splittedCommand[0].equals(commandType.getName())/* && isValidCommand(splittedCommand)*/;

    }


    private boolean isValidCommand(String[] splittedCommand) {
        if (splittedCommand.length == Commands.sizeOf(commandType.template())) return true;
        view.writeln(commandType.getName()+" command failed because of wrong input. Use 'help' command for details");
        return false;
    }

    abstract public void execute(String command);

    //TODO перенести валидацию из команд"
}