package software.ulpgc.imageviewer.architecture.io;

import software.ulpgc.imageviewer.architecture.ui.ImageDisplay;
import software.ulpgc.imageviewer.architecture.ui.ImageDisplay.Paint;
import software.ulpgc.imageviewer.architecture.model.Image;



import static java.lang.Math.max;

public class ImagePresenter {
    private final ImageDisplay display;
    private final Rescheduler.Factory reschedulerFactory;
    private final Rescheduler presentationTimer;
    private Image image;
    private double zoom = 1.0;

    public ImageDisplay display() {
        return display;
    }

    public ImagePresenter(ImageDisplay display, Rescheduler.Factory factory) {
        this.display = display;
        this.reschedulerFactory = factory;
        this.display.on((ImageDisplay.Zoom) factor ->{
            updateZoom(factor);
            repaint();
        } );
        this.display.on((ImageDisplay.Shift) offset -> {
            resetZoom();
            repaint(offset);
        });

        this.display.on((ImageDisplay.Released) offset -> {
            resetZoom();
            animateRelease(offset);
        });
        this.presentationTimer = reschedulerFactory.create(5000, _ -> this.animateMovement(0, -display.width(), image.next()).start());
    }

    private void repaint() {
            display.paint(new Paint(image.bitmap(), 0, zoom));

    }


    private void repaint(int offset) {
        Paint mainPaint = new Paint(image.bitmap(), offset, zoom);
        Paint neighborPaint = new Paint(
                offset < 0 ? image.next().bitmap() : image.previous().bitmap(),
                offset < 0 ? display.width() + offset : offset - display.width(),
                zoom
        );

        display.paint(mainPaint, neighborPaint);
    }
    private void animateRelease(int initialOffset) {
        int targetOffset;
        Image targetImage;

        if (Math.abs(initialOffset)*4 > display.width()) {
            targetOffset = initialOffset < 0 ? -display.width() : display.width();
            targetImage = initialOffset < 0 ? image.next() : image.previous();
            Rescheduler timer = animateMovement(initialOffset, targetOffset, targetImage);
            timer.start();
            return;
        }
        Rescheduler timer = animateMovement(initialOffset, 0, image);
        timer.start();
    }

    public Rescheduler animateMovement(int initialOffset, int targetOffset, Image targetImage) {
        final int[] currentOffset = {initialOffset};

        return reschedulerFactory.create(10,(timer) ->  {
            int step = (targetOffset - currentOffset[0]);

            if (Math.abs(step) <= 2) {
                timer.stop();
                this.image = targetImage;
                repaint(0);
            } else {
                int move = step / 10;
                if (move == 0) {
                    move = step > 0 ? 1 : -1;
                }
                currentOffset[0] += move;
                repaint(currentOffset[0]);
            }

        });
    }



    public void updateZoom(int factor) {
        zoom = (factor > 0) ? zoom*1.1 : zoom/1.1;
        zoom = max(0.5, Math.min(zoom, 5.0));
    }

    private void resetZoom() {
        zoom = 1;
    }

    public void show(Image image) {
        this.image = image;
        this.display.paint(new Paint(image.bitmap(), 0, zoom));
    }

    public Image image() {
        return image;
    }

    public void togglePresentation() {
        if (presentationTimer.isRunning()) {
            presentationTimer.stop();
        } else {
            presentationTimer.start();
        }
    }
}
