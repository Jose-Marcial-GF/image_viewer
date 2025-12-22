package software.ulpgc.imageviewer.architecture.control;

import software.ulpgc.imageviewer.architecture.io.ImagePresenter;

public class ZoomInCommand implements Command {
    private final ImagePresenter imagePresenter;

    public ZoomInCommand(ImagePresenter imagePresenter) {
        this.imagePresenter = imagePresenter;
    }

    @Override
    public void execute() {
        imagePresenter.updateZoom(1);
        imagePresenter.show(imagePresenter.image());
    }


}
