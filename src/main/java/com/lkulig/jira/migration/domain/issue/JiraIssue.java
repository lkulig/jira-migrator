package com.lkulig.jira.migration.domain.issue;

import static com.atlassian.util.concurrent.Promises.promise;
import static com.google.common.base.MoreObjects.firstNonNull;
import static com.google.common.collect.FluentIterable.from;
import static com.google.common.collect.Lists.newArrayList;
import static com.lkulig.jira.migration.domain.JiraAttachment.TO_JIRA_ATTACHMENT;
import static com.lkulig.jira.migration.domain.JiraComment.TO_JIRA_COMMENT;
import com.atlassian.jira.rest.client.api.domain.Attachment;
import com.atlassian.jira.rest.client.api.domain.Issue;
import com.atlassian.jira.rest.client.api.domain.IssueField;
import com.atlassian.util.concurrent.Promise;
import com.google.common.base.Function;
import com.lkulig.jira.migration.domain.JiraAttachment;
import com.lkulig.jira.migration.domain.JiraComment;
import com.lkulig.jira.migration.domain.JiraEntity;
import java.net.URI;
import java.util.List;

public class JiraIssue extends JiraEntity<Promise<Issue>> {

    public static final Function<Issue, JiraIssue> TO_JIRA_ISSUE = new Function<Issue, JiraIssue>() {

        @Override
        public JiraIssue apply(Issue issue) {
            return createFrom(issue);
        }
    };

    public static JiraIssue createFrom(Promise<Issue> issuePromise) {
        return new JiraIssue(issuePromise);
    }

    public static JiraIssue createFrom(Issue issue) {
        return new JiraIssue(promise(issue));
    }

    private JiraIssue(Promise<Issue> issue) {
        super(issue);
    }

    public String getKey() {
        return underlying.claim().getKey();
    }

    public String getSummary() {
        return underlying.claim().getSummary();
    }

    public JiraIssueType getIssueType() {
        return new JiraIssueType(underlying.claim().getIssueType());
    }

    public String getDescription() {
        return underlying.claim().getDescription();
    }

    public long getPriorityId() {
        return underlying.claim().getPriority().getId();
    }

    public URI getCommentsUri() {
        return underlying.claim().getCommentsUri();
    }

    public URI getAttachmentsUri() {
        return underlying.claim().getAttachmentsUri();
    }

    public URI getUrl() {
        return underlying.claim().getSelf();
    }

    public Iterable<IssueField> getFields() {
        return underlying.claim().getFields();
    }

    public List<JiraAttachment> getAttachments() {
        Iterable<Attachment> attachments = (Iterable<Attachment>) firstNonNull(underlying.claim().getAttachments(),
            newArrayList());
        return from(attachments).transform(TO_JIRA_ATTACHMENT).toList();
    }

    public List<JiraComment> getComments() {
        return from(underlying.claim().getComments()).transform(TO_JIRA_COMMENT).toList();
    }
}
