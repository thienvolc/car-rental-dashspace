package dashspace.fun.car_rental_server.infrastructure.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Component;

import java.util.Locale;

@Component
public class I18nUtil {

    private static MessageSource messageSource;

    @Autowired
    public I18nUtil(MessageSource messageSource) {
        I18nUtil.messageSource = messageSource;
    }

    public static String get(String key, Object... args) {
        return (messageSource != null ? getMessageOrKey(key, args) : key);
    }

    private static String getMessageOrKey(String key, Object... args) {
        try {
            Locale locale = LocaleContextHolder.getLocale();
            return messageSource.getMessage(key, args, locale);
        } catch (Exception _) {
            return key;
        }
    }
}
