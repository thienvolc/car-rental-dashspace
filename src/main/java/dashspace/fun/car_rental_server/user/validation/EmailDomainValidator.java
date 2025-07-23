package dashspace.fun.car_rental_server.user.validation;

import dashspace.fun.car_rental_server.config.prop.SecurityProperties;
import jakarta.annotation.Nullable;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class EmailDomainValidator implements ConstraintValidator<NonDisposableEmail, String> {

    private final SecurityProperties securityProperties;

    @Override
    public boolean isValid(@Nullable String email, ConstraintValidatorContext ctx) {
        if (!isEmailValid(email)) {
            return true;
        }
        String domain = extractDomain(email);
        return !this.securityProperties.getDisposableEmail().contains(domain);
    }

    private boolean isEmailValid(String email) {
        return email != null && email.contains("@");
    }

    private String extractDomain(String email) {
        int atIndex = email.indexOf('@') + 1;
        int dotIndex = email.lastIndexOf('.');
        return email.substring(atIndex, dotIndex).toLowerCase();
    }
}
