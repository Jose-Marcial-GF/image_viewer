package software.ulpgc.imageviewer.architecture.io;

import java.util.function.Consumer;

public interface Rescheduler {
    void start();
    void stop();
    boolean isRunning();

    interface Factory {
        Rescheduler create(int period, Consumer<Rescheduler> action);
    }
}