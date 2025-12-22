package software.ulpgc.imageviewer.application.gui;

import software.ulpgc.imageviewer.architecture.model.Canvas;
import software.ulpgc.imageviewer.architecture.ui.ImageDisplay;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class SwingImageDisplay extends JPanel implements ImageDisplay {
    private Shift shift;
    private Released released;
    private Paint[] paints;
    private Zoom zoomFactor;

    public SwingImageDisplay() {
        MouseAdapter mouseAdapter = new MouseAdapter();
        this.addMouseListener(mouseAdapter);
        this.addMouseMotionListener(mouseAdapter);
        this.addMouseWheelListener(mouseAdapter);
    }

    @Override
    public void paint(Paint... paints) {
        this.paints = paints;
        this.repaint();
    }

    @Override
    public int width() {
        return this.getWidth();
    }

    private final Map<Integer, BufferedImage> images = new HashMap<>();
    private BufferedImage toBufferedImage(byte[] bitmap) {
        return images.computeIfAbsent(Arrays.hashCode(bitmap), _ -> read(bitmap));
    }

    private BufferedImage read(byte[] bitmap) {
        try (InputStream is = new ByteArrayInputStream(bitmap)) {
            return ImageIO.read(is);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void paint(Graphics g) {
        g.setColor(Color.BLACK);
        g.fillRect(0,0,this.getWidth(), this.getHeight());
        for (Paint paint : paints) {
            BufferedImage bitmap = toBufferedImage(paint.bitmap());
            Canvas canvas = Canvas.ofSize(this.getWidth(), this.getHeight())
                    .fit(bitmap.getWidth(), bitmap.getHeight());


            int w = (int) (canvas.width() * paint.zoom());
            int x = (this.getWidth() - w) / 2;
            int h = (int) (canvas.height() * paint.zoom());
            int y = (this.getHeight() - h) / 2;
            g.drawImage(bitmap, x+paint.offset(), y, w, h, null);
        }
    }

    private class MouseAdapter implements MouseListener, MouseMotionListener, MouseWheelListener {
        private int x;

        @Override
        public void mouseClicked(MouseEvent e) {

        }

        @Override
        public void mousePressed(MouseEvent e) {
            x =  e.getX();
        }

        @Override
        public void mouseReleased(MouseEvent e) {
            SwingImageDisplay.this.released.offset(e.getX() - x);
        }

        @Override
        public void mouseEntered(MouseEvent e) {

        }

        @Override
        public void mouseExited(MouseEvent e) {

        }

        @Override
        public void mouseDragged(MouseEvent e) {
            SwingImageDisplay.this.shift.offset(e.getX() - x);
        }

        @Override
        public void mouseMoved(MouseEvent e) {

        }

        @Override
        public void mouseWheelMoved(MouseWheelEvent e) {
            SwingImageDisplay.this.zoomFactor.offset(- e.getWheelRotation());
        }
    }

    @Override
    public void on(Shift shift) {
        this.shift = shift;
    }

    @Override
    public void on(Released released) {
        this.released = released;
    }

    @Override
    public void on(Zoom zoom) {
        this.zoomFactor = zoom;
    }


}






















