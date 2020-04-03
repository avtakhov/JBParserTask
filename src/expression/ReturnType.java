package expression;

import java.util.Map;

public enum ReturnType {
    BOOL, INT;

    private static final Map<ReturnType, String> VALUES = Map.of(
            ReturnType.BOOL, "BOOL",
            ReturnType.INT, "INT"
    );

    @Override
    public String toString() {
        return VALUES.get(this);
    }
}
