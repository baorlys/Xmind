package config;

public class ExceptionUtils {
    private ExceptionUtils() {
        throw new IllegalStateException("Utility class");
    }
    public static void sneakyThrow(Exception e) {
        ExceptionUtils.sneakyThrowInternal(e);
    }

    @SuppressWarnings("unchecked")
    private static <T extends Throwable> void sneakyThrowInternal(Throwable t) throws T {
        throw (T) t;
    }
}
