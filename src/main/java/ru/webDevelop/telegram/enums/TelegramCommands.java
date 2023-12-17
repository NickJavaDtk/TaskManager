package ru.webDevelop.telegram.enums;

public enum TelegramCommands {
    START("/start"),
    NAME("/name"),
    HELP("/help"),
    APPEAL("/appeal"),
    END("/end");
    private final String command;


    TelegramCommands(String command) {
        this.command = command;
    }

    @Override
    public String toString() {
        return command;
    }

    public boolean equals(String command) {
        return this.toString().equals(command);
    }

}
