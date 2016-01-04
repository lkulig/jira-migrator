package com.lkulig.jira.migration.domain.issue;

import static com.google.common.base.Optional.absent;
import static com.google.common.base.Optional.of;
import static com.google.common.collect.Lists.newArrayList;
import static com.lkulig.jira.migration.util.StringUtils.removeAllNewLineCharacters;
import com.atlassian.jira.rest.client.api.domain.IssueField;
import com.atlassian.jira.rest.client.api.domain.input.IssueInput;
import com.atlassian.jira.rest.client.api.domain.input.IssueInputBuilder;
import com.google.common.base.Optional;
import com.lkulig.jira.migration.domain.JiraProject;
import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class JiraIssueFactory {

    @Autowired
    private IssueFieldsMapper issueFieldsMapper;

    public Optional<IssueInput> createFrom(JiraIssue issue, JiraProject project) {

        Optional<IssueMap> issueMapping = issueFieldsMapper.getIssueMappingFor(issue.getIssueType().getName());
        if (issueMapping.isPresent()) {
            IssueMap issueMap = issueMapping.get();
            IssueInputBuilder issueBuilder = new IssueInputBuilder(project.getKey(), issueMap.typeId,
                removeAllNewLineCharacters(issue.getSummary()));
            issueBuilder.setDescription(issue.getDescription());
            issueBuilder.setPriorityId(issue.getPriorityId());

            setCustomFields(issueBuilder, issue, issueMap);
            return of(issueBuilder.build());
        }
        return absent();
    }

    private void setCustomFields(IssueInputBuilder issueBuilder, JiraIssue issue, IssueMap issueMap) {
        for (IssueField issueField : issue.getFields()) {
            Optional<CustomField> optionalCustomField = issueMap.customFieldFor(issueField.getId());
            if (optionalCustomField.isPresent()) {
                Object fieldValue = issueField.getValue();
                CustomField customField = optionalCustomField.get();
                if (fieldValue == null && customField.required) {
                    issueBuilder.setFieldValue(customField.to, customField.defaultValue());
                } else {
                    if (customField.isArray()) {
                        try {
                            issueBuilder.setFieldValue(customField.to, newArrayList(((JSONArray) fieldValue).get(0)));
                        } catch (JSONException e) {}
                    } else {
                        issueBuilder.setFieldValue(customField.to, fieldValue);
                    }
                }
            }
        }
    }
}
