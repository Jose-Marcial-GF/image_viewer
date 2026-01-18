package software.ulpgc.imageviewer.application;

import software.ulpgc.imageviewer.architecture.io.Rescheduler;

import javax.swing.*;
import java.util.function.Consumer;

public class SwingRescheduler implements Rescheduler {
    private final Timer timer;

    public SwingRescheduler(int period, Consumer<Rescheduler> action) {
        this.timer = new Timer(period, _ -> action.accept(this));
    }

    @Override
    public void start() {
        timer.start();
    }

    @Override
    public void stop() {
        timer.stop();
    }

    @Override
    public boolean isRunning() {
        return timer.isRunning();
    }
}