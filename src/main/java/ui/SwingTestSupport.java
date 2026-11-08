
package ui;

import java.lang.reflect.InvocationTargetException;
import java.util.concurrent.Callable;
import java.util.concurrent.atomic.AtomicReference;
import javax.swing.SwingUtilities;

final class SwingTestSupport {

    private SwingTestSupport() {
    }

    static <T> T onEventThread(Callable<T> action) {
        if (SwingUtilities.isEventDispatchThread()) {
            return call(action);
        }

        AtomicReference<T> result = new AtomicReference<>();
        AtomicReference<Throwable> error = new AtomicReference<>();

        try {
            SwingUtilities.invokeAndWait(() -> {
                try {
                    result.set(action.call());
                } catch (Exception exception) {
                    error.set(exception);
                }
            });
        } catch (InterruptedException exception) {
            Thread.currentThread().interrupt();
            throw new AssertionError("Interrupted while executing Swing test", exception);
        } catch (InvocationTargetException exception) {
            throw new AssertionError("Swing test failed on the event thread", exception.getCause());
        }

        if (error.get() != null) {
            throw new AssertionError("Swing test action failed", error.get());
        }
        return result.get();
    }

    private static <T> T call(Callable<T> action) {
        try {
            return action.call();
        } catch (Exception exception) {
            throw new AssertionError("Swing test action failed", exception);
        }
    }
}
