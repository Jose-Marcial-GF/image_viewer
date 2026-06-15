package software.ulpgc.imageviewer.architecture.ui;

public interface ImageDisplay {

    void on(Shift shift);
    void on(Released released);
    void on(Zoom zoom);

    void paint(Paint... paints);

    int width();
    int height();

    interface Shift {
        void offset(int value);
    }

    interface Released {
        void offset(int value);
    }

    interface Zoom {
        void zoom(int factor, int mouseX, int mouseY);
    }


    record Paint(byte[] bitmap, int offset, double panX, double panY, double zoom) {}

}
