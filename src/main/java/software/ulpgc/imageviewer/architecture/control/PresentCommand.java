package software.ulpgc.imageviewer.architecture.control;

import software.ulpgc.imageviewer.architecture.io.ImagePresenter;

public record PresentCommand(ImagePresenter presenter) implements Command {
    @Override
    public void execute() {
        presenter.togglePresentation();
    }
}