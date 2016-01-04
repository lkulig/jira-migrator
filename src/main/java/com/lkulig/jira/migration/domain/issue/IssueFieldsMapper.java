package com.lkulig.jira.migration.domain.issue;

import static com.google.common.collect.FluentIterable.from;
import static com.google.common.collect.Lists.newArrayList;
import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.base.Optional;
import com.google.common.base.Predicate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.Resource;
import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.List;

public class IssueFieldsMapper {

    private static final Logger LOGGER = LoggerFactory.getLogger(IssueFieldsMapper.class);
    private static final String TYPE_ID = "typeId";
    private static final String ISSUES = "issues";
    private static final String NAME = "name";
    private static final String FIELDS = "customFields";
    private static final String FROM = "from";
    private static final String TO = "to";
    private static final String REQUIRED = "required";
    private static final String TYPE = "type";
    private Resource mappings;
    private JsonFactory jsonFactory;
    private List<IssueMap> issueMapping = newArrayList();

    public IssueFieldsMapper(Resource mappings, JsonFactory jsonFactory) {
        this.mappings = mappings;
        this.jsonFactory = jsonFactory;
    }

    public Optional<IssueMap> getIssueMappingFor (final String issueTypeName) {
        return from(issueMapping).firstMatch(new Predicate<IssueMap>() {

            @Override
            public boolean apply(IssueMap issueMap) {
                return issueMap.name.equals(issueTypeName);
            }
        });
    }

    @PostConstruct
    public void loadIssueTypes() throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        JsonParser parser = jsonFactory.createParser(mappings.getFile());
        JsonNode node = objectMapper.readTree(parser);
        JsonNode issues = node.get(ISSUES);
        for (JsonNode issue : issues) {
            processIssue(issue);
        }
        LOGGER.debug("Following Issue Mappings will be used [{}].", issueMapping);
    }

    private void processIssue(JsonNode issue) {
        IssueMap issueMap = new IssueMap();
        issueMap.name = issue.get(NAME).textValue();
        issueMap.typeId = issue.get(TYPE_ID).longValue();
        JsonNode fields = issue.get(FIELDS);
        if(fields != null) {
            processCustomFields(issueMap, fields);
        }
        issueMapping.add(issueMap);
    }

    private void processCustomFields(IssueMap issueMap, JsonNode fields) {
        for (JsonNode fieldsMapping : fields) {
            CustomField customField = new CustomField();
            customField.from = fieldsMapping.get(FROM).asText();
            customField.to = fieldsMapping.get(TO).asText();
            customField.required = fieldsMapping.get(REQUIRED).asBoolean();
            customField.type = fieldsMapping.get(TYPE).asText();
            issueMap.add(customField);
        }
    }
}
