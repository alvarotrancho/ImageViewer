package imageviewer.apps.swing;

import imageviewer.model.Image;
import imageviewer.view.ImageDisplay;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.JPanel;

public class ImagePanel extends JPanel implements ImageDisplay{
    private BufferedImage bitmap;
    private Image image;
    
    @Override
    public void paint(Graphics g){
        g.setColor(Color.white);
        g.fillRect(0, 0, getWidth(), getHeight());
        if (bitmap == null) return;
        Scale scale = new Scale(bitmap.getWidth(), bitmap.getHeight(), getWidth(), getHeight());
        g.drawImage(bitmap, scale.x(), scale.y(), scale.width(), scale.heigth(), null);
    }

    @Override
    public void display(Image image) {
        this.image = image;
        loadBitmap();
        repaint();
    }

    @Override
    public Image currentImage() {
        return image;
    }

    private void loadBitmap() {
        try {
            bitmap = ImageIO.read(new File(image.getName()));
        } catch (IOException ex) {
        }
    }

    private static class Scale {
        private final int iw;
        private final int ih;
        private final int pw;
        private final int ph;

        private Scale(int iw, int ih, int pw, int ph) {
            this.iw = iw;
            this.ih = ih;
            this.pw = pw;
            this.ph = ph;
        }
        
        int x(){
            return (pw - width()) / 2;
        }

        int y(){
            return (ph - heigth()) / 2; 
        }
        
        int width(){
           return adjustWidth() ? pw : (int) (iw * (double) ph / ih);
        }
        
        int heigth(){
            return adjustWidth() ? (int) (ih * (double) pw / iw) : ph;
        }
                
        private boolean adjustWidth(){
            return iw * ph > pw * ih;
        }
    }
}

/*
iw: Image Width
ih: Image Height
ir: Image Ratio = Image Width / Image Height
pw: Panel Width
ph: Panel Heigth
pr: Panel Ratio = Panel Width / Panel Heigth

800 x 600 (1.3333) -> 400 x 300 (1.3333) -> 400x300
800 x 600 (1.3333) -> 1600 x 1200(1.3333) -> 1600x1200
800 x 600 (1.3333) -> 400 x 600 (0.6666) -> 400x600 = 400 / 800
800 x 600 (1.3333) -> 800 x 300 (2.6666) -> 800x300 / 600 x 300

ir > pr -> pw x ih = pw / iw
ir < pr -> iw x ph / ih x ph

image (800x600) -> panel (400x55) -> scale(800 x 55/ 600 x 55)
*/
