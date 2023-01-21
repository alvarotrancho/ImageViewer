package imageviewer.apps.mock;

import imageviewer.model.Image;
import imageviewer.view.ImageDisplay;

public class MockImageDisplay implements ImageDisplay{
    
    private Image image;

    @Override
    public void display(Image image) {
        this.image = image;
        System.out.println(image.getName());
    }

    @Override
    public Image currentImage() {
        return image;
    }
}
