package imageviewer.control;

public class ExitImageCommand implements Command {

    @Override
    public void execute() {
        System.exit(0);
    }
    
}
