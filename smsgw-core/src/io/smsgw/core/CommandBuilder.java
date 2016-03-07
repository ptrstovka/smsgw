package io.smsgw.core;

public class CommandBuilder {

    // required empty constructor for singleton
    private CommandBuilder() {
    }

    public static CommandBuilder getInstance() {
        return new CommandBuilder();
    }

    public Command buildCommand(String number, String smsContent) {
        return new Command("AT+CMGS=\"" + number + "\"\n" + smsContent + "\u001A");
    }

}
