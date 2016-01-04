package com.lkulig.jira.migration.util;

import static com.google.common.base.Strings.isNullOrEmpty;

public class StringUtils {

    private static final String NEW_LINE = "\n";

    public static String removeAllNewLineCharacters(String input) {
        if (isNullOrEmpty(input)) {
            return input;
        }
        return input.replace(NEW_LINE, " ");
    }

    public static String changeInvalidCharacters(String input) {
        return input.replace(" \\", "\\\\").replace("\"", "\\\"").replace("?", " ").replace("*", "").replace("{", "")
            .replace(" -", "\\\\-").replace("[", "").replace("}", "").replace("]", "").replace("(", "")
            .replace(")", "").replace(" !", "").replace("!", "");
    }
}
