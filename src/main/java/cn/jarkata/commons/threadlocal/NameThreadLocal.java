package cn.jarkata.commons.threadlocal;

/**
 * Named ThreadLocal
 *
 * @param <T> type in thread local
 */
public class NameThreadLocal<T> extends ThreadLocal<T> {

    private final String name;

    public NameThreadLocal(String name) {
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
