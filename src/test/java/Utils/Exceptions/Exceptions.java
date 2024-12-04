package Utils.Exceptions;

public class Exceptions {

    public static class TestSetupException extends RuntimeException {
        public TestSetupException(String message, Throwable cause) {
            super(message, cause);
        }
    }

    public static class NavigationException extends RuntimeException {
        public NavigationException(String message, Throwable cause) {
            super(message, cause);
        }
    }
}
