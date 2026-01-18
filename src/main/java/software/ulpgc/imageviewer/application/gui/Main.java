package software.ulpgc.imageviewer.application.gui;

import com.formdev.flatlaf.FlatDarkLaf;
import software.ulpgc.imageviewer.application.FileImageStore;
import software.ulpgc.imageviewer.application.SwingRescheduler;
import software.ulpgc.imageviewer.architecture.control.*;
import software.ulpgc.imageviewer.architecture.io.ImagePresenter;
import software.ulpgc.imageviewer.architecture.io.ImageProvider;
import software.ulpgc.imageviewer.architecture.io.ImageStore;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

public class Main {
    private static File root;

    static void main(String[] args) {
        FlatDarkLaf.setup();
        root = new File("images");
        ImageStore store = new FileImageStore(root);
        ImageProvider imageProvider = ImageProvider.with(store.images());
        SwingImageDisplay imageDisplay = new SwingImageDisplay();
        ImagePresenter imagePresenter = new ImagePresenter(imageDisplay, SwingRescheduler::new);
        imagePresenter.show(imageProvider.first(Main::readImage));
        Desktop.create(imageDisplay)
                .put("next", new NextCommand(imagePresenter))
                .put("prev", new PrevCommand(imagePresenter))
                .put("zoomIn", new ZoomInCommand(imagePresenter))
                .put("zoomOut", new ZoomOutCommand(imagePresenter))
                .put("present", new PresentCommand(imagePresenter))
                .setVisible(true);
    }

    private static byte[] readImage(String id) {
        try {
            return Files.readAllBytes(new File(root, id).toPath());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


}
