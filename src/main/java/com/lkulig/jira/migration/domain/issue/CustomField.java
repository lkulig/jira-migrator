package com.lkulig.jira.migration.domain.issue;

import static com.google.common.base.MoreObjects.toStringHelper;
import static com.google.common.collect.Lists.newArrayList;
import static com.google.common.collect.Maps.newHashMap;
import com.atlassian.jira.rest.client.api.domain.input.ComplexIssueInputFieldValue;
import java.util.Map;

public class CustomField {

    String from;
    String to;
    boolean required;
    String type;

    public Object defaultValue() {
        switch (type) {
            case "text":
                return "Missing!";
            case "array":
                return newArrayList("Missing!");
            case "number":
                return 0;
            default:
                return null;
        }
    }

    public boolean isArray() {
        return "array".equals(type);
    }

    @Override
    public String toString() {
        return toStringHelper(this)
            .add("from", from)
            .add("to", to)
            .add("required", required)
            .add("type", type)
            .toString();
    }
}
