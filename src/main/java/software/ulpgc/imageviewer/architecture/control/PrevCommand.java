package software.ulpgc.imageviewer.architecture.control;

import software.ulpgc.imageviewer.architecture.io.ImagePresenter;


public record PrevCommand(ImagePresenter imagePresenter) implements Command {

    @Override
    public void execute() {
        imagePresenter.animateMovement(0, imagePresenter.display().width(), imagePresenter.image().previous() ).start();
    }


}
