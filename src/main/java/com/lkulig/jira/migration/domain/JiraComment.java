package com.lkulig.jira.migration.domain;

import com.atlassian.jira.rest.client.api.domain.Comment;
import com.google.common.base.Function;

public class JiraComment extends JiraEntity<Comment> {

    public static final Function<Comment, JiraComment> TO_JIRA_COMMENT = new Function<Comment, JiraComment>() {

        @Override
        public JiraComment apply(Comment attachment) {
            return new JiraComment(attachment);
        }
    };

    public JiraComment(Comment underlying) {
        super(underlying);
    }

    public String getBody() {
        return underlying.getBody();
    }
}
