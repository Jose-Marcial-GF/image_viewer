package software.ulpgc.imageviewer.architecture.io;

import software.ulpgc.imageviewer.architecture.ui.ImageDisplay;
import software.ulpgc.imageviewer.architecture.ui.ImageDisplay.Paint;
import software.ulpgc.imageviewer.architecture.model.Image;

public class ImagePresenter {
    private final ImageDisplay display;
    private Image image;
    private double zoom = 1.0;

    public ImagePresenter(ImageDisplay display) {
        this.display = display;
        this.display.on((ImageDisplay.Zoom) factor ->{
            updateZoom(factor);
            show(image);
        } );
        this.display.on((ImageDisplay.Shift) offset -> {
            resetZoom();
            display.paint(
                    new Paint(image.bitmap(), offset, zoom),
                    new Paint(
                            offset < 0 ? image.next().bitmap() : image.previous().bitmap(),
                            offset < 0 ? display.width() + offset : offset - display.width(),
                            zoom)
            );
        });
        this.display.on((ImageDisplay.Released) offset -> {
            resetZoom();
            show(newImage(offset));
        });
    }

    public void updateZoom(int factor) {
        zoom *= Math.abs(factor + 0.1);
        zoom = Math.max(0.5, Math.min(zoom, 5.0));
    }

    private void resetZoom() {
        zoom = 1;
    }

    private Image newImage(int offset) {
        if (Math.abs(offset) * 3 < display.width()) return image;
        return offset < 0 ? image.next() : image.previous();
    }

    public void show(Image image) {
        this.image = image;
        this.display.paint(new Paint(image.bitmap(), 0, zoom));
    }

    public Image image() {
        return image;
    }

}
