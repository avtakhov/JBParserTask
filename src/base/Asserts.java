package base;

import java.util.Arrays;
import java.util.Objects;

public class Asserts {
    public static void assertEquals(final String message, final Object expected, final Object actual) {
        final String reason = String.format(
                "%s:%n     expected `%s`,%n       actual `%s`",
                message,
                toString(expected),
                toString(actual)
        );
        assertTrue(reason, Objects.deepEquals(expected, actual));
    }

    private static String toString(final Object value) {
        final String result = Arrays.deepToString(new Object[]{value});
        return result.substring(1, result.length() - 1);
    }

    public static void assertTrue(final String message, final boolean value) {
        if (!value) {
            throw new AssertionError(message);
        }
    }
}
