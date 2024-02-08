package processor;

import annotation.Autowried;
import entry.Person;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.Objects;

public class AutowriedProcessor {
    public static void processAnnotations(Class<?> clazz) {
        Field[] declaredFields = clazz.getDeclaredFields();
        for (Field declaredField : declaredFields) {
            if (declaredField.isAnnotationPresent(Autowried.class)) {
                declaredField.setAccessible(true);
                Class<?> type = declaredField.getType();
                try {
                    Object o = type.getDeclaredConstructor().newInstance();
                    declaredField.set(Objects.requireNonNull(clazz.getConstructor().newInstance()), o);
                } catch (InstantiationException | IllegalAccessException | InvocationTargetException |
                         NoSuchMethodException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }
}
