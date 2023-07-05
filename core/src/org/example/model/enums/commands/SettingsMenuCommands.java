package org.example.model.enums.commands;

import java.io.Serializable;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public enum SettingsMenuCommands  implements Serializable {
    // TODO: 5/11/2023 in Graphics
    ;

    final Pattern pattern;

    SettingsMenuCommands(String regex) {
        this.pattern = Pattern.compile(regex);
    }

    static public Matcher getMatcher(String input, SettingsMenuCommands command) {
        Matcher matcher = command.pattern.matcher(input);
        return matcher.matches() ? matcher : null;
    }
}
