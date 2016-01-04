package com.lkulig.jira.migration.domain.issue;

import static com.google.common.base.MoreObjects.toStringHelper;
import static com.google.common.base.Optional.absent;
import static com.google.common.base.Optional.of;
import static com.google.common.collect.FluentIterable.from;
import static com.google.common.collect.Lists.newArrayList;
import com.google.common.base.Optional;
import com.google.common.base.Predicate;
import java.util.List;

public class IssueMap {

    String name;
    long typeId;
    List<CustomField> customFields = newArrayList();

    public Optional<CustomField> customFieldFor(final String sourceFieldId) {
        Optional<CustomField> customFieldOptional = from(customFields).firstMatch(
            new Predicate<CustomField>() {

                @Override
                public boolean apply(CustomField customField) {
                    return customField.from.equals(sourceFieldId);
                }
            });

        if (customFieldOptional.isPresent()) {
            return of(customFieldOptional.get());
        }

        return absent();
    }

    public void add(CustomField customField) {
        customFields.add(customField);
    }

    @Override
    public String toString() {
        return toStringHelper(this)
            .add("name", name)
            .add("typeId", typeId)
            .add("customFields", customFields)
            .toString();
    }
}
