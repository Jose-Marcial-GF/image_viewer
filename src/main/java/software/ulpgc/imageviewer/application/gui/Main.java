package software.ulpgc.imageviewer.application.gui;

import com.formdev.flatlaf.FlatDarkLaf;
import software.ulpgc.imageviewer.application.FileImageStore;
import software.ulpgc.imageviewer.architecture.control.NextCommand;
import software.ulpgc.imageviewer.architecture.control.PrevCommand;
import software.ulpgc.imageviewer.architecture.control.ZoomInCommand;
import software.ulpgc.imageviewer.architecture.control.ZoomOutCommand;
import software.ulpgc.imageviewer.architecture.io.ImagePresenter;
import software.ulpgc.imageviewer.architecture.io.ImageProvider;
import software.ulpgc.imageviewer.architecture.io.ImageStore;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

public class Main {
    private static File root;

    public static void main(String[] args) throws IOException {
        FlatDarkLaf.setup();
        root = new File("images");
        ImageStore store = new FileImageStore(root);
        ImageProvider imageProvider = ImageProvider.with(store.images());
        SwingImageDisplay imageDisplay = new SwingImageDisplay();
        ImagePresenter imagePresenter = new ImagePresenter(imageDisplay);
        imagePresenter.show(imageProvider.first(Main::readImage));
        Desktop.create(imageDisplay)
                .put("next", new NextCommand(imagePresenter))
                .put("prev", new PrevCommand(imagePresenter))
                .put("zoomIn", new ZoomInCommand(imagePresenter))
                .put("zoomOut", new ZoomOutCommand(imagePresenter))
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
