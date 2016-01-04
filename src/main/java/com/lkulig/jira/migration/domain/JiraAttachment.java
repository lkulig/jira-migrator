package com.lkulig.jira.migration.domain;

import com.atlassian.jira.rest.client.api.domain.Attachment;
import com.google.common.base.Function;
import java.net.URI;

public class JiraAttachment extends JiraEntity<Attachment> {

    public static final Function<Attachment, JiraAttachment> TO_JIRA_ATTACHMENT = new Function<Attachment, JiraAttachment>() {

        @Override
        public JiraAttachment apply(Attachment attachment) {
            return new JiraAttachment(attachment);
        }
    };

    public JiraAttachment(Attachment attachment) {
        super(attachment);
    }

    public URI getContentUri() {
        return underlying.getContentUri();
    }

    public String getFilename() {
        return underlying.getFilename();
    }

}
