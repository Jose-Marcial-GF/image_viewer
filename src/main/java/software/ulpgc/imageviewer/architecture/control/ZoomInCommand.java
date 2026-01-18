package software.ulpgc.imageviewer.architecture.control;

import software.ulpgc.imageviewer.architecture.io.ImagePresenter;

public record ZoomInCommand(ImagePresenter imagePresenter) implements Command {

    @Override
    public void execute() {
        imagePresenter.updateZoom(1);
        imagePresenter.show(imagePresenter.image());
    }


}
