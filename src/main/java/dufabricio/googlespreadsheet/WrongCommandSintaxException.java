package dufabricio.googlespreadsheet;

import lombok.Data;

@Data
public class WrongCommandSintaxException extends RuntimeException {

    private String command;

    public WrongCommandSintaxException(String command) {
        super(command);
        this.command = command;
    }

    public String getCommand() {
        return command;
    }

    public void setCommand(String command) {
        this.command = command;
    }

}
