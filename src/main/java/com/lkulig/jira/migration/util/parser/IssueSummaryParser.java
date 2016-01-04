package com.lkulig.jira.migration.util.parser;

public class IssueSummaryParser {

    private static final String SLASH = "\\";
    private static final String QUOTATION_MARK = "\"";
    private static final String ESCAPE_CHARACTER = "\\";
    private static final String SINGLE_QUOTE = "'";

    public static String parse(String issueSummary) {
        return issueSummary.replace(SLASH, ESCAPE_CHARACTER + ESCAPE_CHARACTER)
            .replace(SINGLE_QUOTE, ESCAPE_CHARACTER + SINGLE_QUOTE)
            .replace(QUOTATION_MARK, ESCAPE_CHARACTER + QUOTATION_MARK);
    }
}
