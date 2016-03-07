package io.smsgw.core;

public class Command {

    public String command;

    public Command(String command){
        this.setCommand(command);
    }

    public String getCommandValue(){
        return this.command;
    }

    public void setCommand(String command){
        this.command = command;
    }

}
