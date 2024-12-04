package Utils;


import java.util.regex.Pattern;
import org.apache.commons.lang3.StringUtils;
 // Exclude password from toString for security
public class RegistrationData {
    private static final int MIN_PASSWORD_LENGTH = 8;
    private static final int MAX_NAME_LENGTH = 50;
    private static final Pattern EMAIL_PATTERN =
            Pattern.compile("^[A-Za-z0-9+_.-]+@(.+)$");

    private final String firstName;
    private final String lastName;
    private final String email;
    private final String password;

    public RegistrationData(Builder builder) {
        validateFields(builder);
        this.firstName = builder.firstName;
        this.lastName = builder.lastName;
        this.email = builder.email;
        this.password = builder.password;
    }

    private static void validateFields(Builder builder) {
        StringBuilder errors = new StringBuilder();

        if (StringUtils.isBlank(builder.firstName)) {
            errors.append("First name cannot be empty. ");
        } else if (builder.firstName.length() > MAX_NAME_LENGTH) {
            errors.append("First name is too long. ");
        }

        if (StringUtils.isBlank(builder.lastName)) {
            errors.append("Last name cannot be empty. ");
        } else if (builder.lastName.length() > MAX_NAME_LENGTH) {
            errors.append("Last name is too long. ");
        }

        if (StringUtils.isBlank(builder.email)) {
            errors.append("Email cannot be empty. ");
        } else if (!EMAIL_PATTERN.matcher(builder.email).matches()) {
            errors.append("Invalid email format. ");
        }

        if (StringUtils.isBlank(builder.password)) {
            errors.append("Password cannot be empty. ");
        } else if (builder.password.length() < MIN_PASSWORD_LENGTH) {
            errors.append("Password must be at least 8 characters long. ");
        }

        if (errors.length() > 0) {
            throw new IllegalArgumentException(errors.toString().trim());
        }
    }

    public static class Builder {
        private String firstName;
        private String lastName;
        private String email;
        private String password;

        public Builder firstName(String firstName) {
            this.firstName = StringUtils.trim(firstName);
            return this;
        }

        public Builder lastName(String lastName) {
            this.lastName = StringUtils.trim(lastName);
            return this;
        }

        public Builder email(String email) {
            this.email = StringUtils.trim(email);
            return this;
        }

        public Builder password(String password) {
            this.password = password; // Don't trim password as it might contain valid spaces
            return this;
        }

        public RegistrationData build() {
            return new RegistrationData(this);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof RegistrationData)) return false;
        RegistrationData that = (RegistrationData) o;
        return StringUtils.equals(firstName, that.firstName) &&
                StringUtils.equals(lastName, that.lastName) &&
                StringUtils.equals(email, that.email) &&
                StringUtils.equals(password, that.password);
    }

    @Override
    public int hashCode() {
        return java.util.Objects.hash(firstName, lastName, email, password);
    }
}
