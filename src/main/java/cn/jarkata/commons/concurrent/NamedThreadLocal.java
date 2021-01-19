package cn.jarkata.commons.concurrent;

/**
 * Named ThreadLocal
 *
 * @param <T> type in thread local
 */
public class NamedThreadLocal<T> extends ThreadLocal<T> {

    private final String name;

    public NamedThreadLocal(String name) {
        if (name == null || "".equals(name)) {
            throw new IllegalArgumentException("Name must not be empty");
        }
        this.name = name;
    }

    @Override
    public String toString() {
        return this.name;
    }
}
