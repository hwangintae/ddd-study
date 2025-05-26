package org.intaehwang.dddstudy.chapter4;

import jakarta.persistence.AttributeConverter;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

public class EmailSetConverter implements AttributeConverter<EmailSet, String> {
    @Override
    public String convertToDatabaseColumn(EmailSet emailSet) {
        if (emailSet == null) return null;

        return emailSet.getEmails().stream()
                .map(Email::getAddress)
                .collect(Collectors.joining(","));
    }

    @Override
    public EmailSet convertToEntityAttribute(String dbData) {
        if (dbData == null) return null;

        String[] emails = dbData.split(",");

        Set<Email> collect = Arrays.stream(emails)
                .map(Email::new)
                .collect(Collectors.toSet());

        return new EmailSet(collect);
    }
}
