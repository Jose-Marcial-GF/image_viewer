package software.ulpgc.imageviewer.architecture.control;

import software.ulpgc.imageviewer.architecture.io.ImagePresenter;

import javax.swing.*;

public record PrevCommand(ImagePresenter imagePresenter) implements Command {

    @Override
    public void execute() {
        Timer timer = imagePresenter.animateMovement(0, imagePresenter.display().width(), imagePresenter.image().previous() );
        timer.start();
    }


}
