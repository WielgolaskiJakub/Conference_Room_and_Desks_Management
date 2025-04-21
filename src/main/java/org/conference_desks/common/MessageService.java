package org.conference_desks.common;

import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;

import java.util.Locale;

@Service
public class MessageService {
    private final MessageSource messageSource;

    public MessageService(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    // Pobiera komunikat bez parametrów
    public String getMessage(String key) {
        return getMessage(key, new Object[]{});
    }

    // Pobiera komunikat z dynamicznymi wartościami (np. ID w komunikacie)
    public String getMessage(String key, Object... args) {
        Locale locale = LocaleContextHolder.getLocale();
        return messageSource.getMessage(key, args, locale);
    }
}
