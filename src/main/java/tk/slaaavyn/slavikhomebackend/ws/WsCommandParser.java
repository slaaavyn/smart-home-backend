package tk.slaaavyn.slavikhomebackend.ws;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import tk.slaaavyn.slavikhomebackend.ws.models.commands.BaseCommand;
import tk.slaaavyn.slavikhomebackend.ws.models.commands.RelayCommand;
import tk.slaaavyn.slavikhomebackend.ws.models.commands.ThermometerCommand;

public class WsCommandParser {
    private static final Logger logger = LoggerFactory.getLogger(WsCommandParser.class);
    private static final Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();

    private WsCommandParser() {
    }

    public static BaseCommand parseBaseCommand(String message) {
        try {
            BaseCommand command = gson.fromJson(message, BaseCommand.class);
            if (command == null) throw new JsonSyntaxException("invalid base command");

            return command;
        } catch (IllegalStateException | JsonSyntaxException e) {
            logger.warn("parse incoming base command: " + e);
            return null;
        }
    }

    public static RelayCommand parseRelayCommand(String message) {
        try {
            RelayCommand command = gson.fromJson(message, RelayCommand.class);
            if (command == null) throw new JsonSyntaxException("invalid relay command");

            return command;
        } catch (IllegalStateException | JsonSyntaxException e) {
            logger.warn("parse incoming relay command: " + e);
            return null;
        }
    }

    public static ThermometerCommand parseThermometerCommand(String message) {
        try {
            ThermometerCommand command = gson.fromJson(message, ThermometerCommand.class);
            if (command == null) throw new JsonSyntaxException("invalid thermometer command");

            return command;
        } catch (IllegalStateException | JsonSyntaxException e) {
            logger.warn("parse incoming thermometer command: " + e);
            return null;
        }
    }
}
