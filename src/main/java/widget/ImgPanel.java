package widget;

import javax.swing.*;
import java.awt.*;

public class ImgPanel extends JPanel {
    ImageIcon img;
    int offset_x,offset_y;

    public int aimx,aimy;

    public ImgPanel(){
        setBackground(null);
        setOpaque(false);
    }

    public void setImage(ImageIcon img){
        this.img=img;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if(img!=null)g.drawImage(img.getImage(),offset_x,offset_y,getWidth()-offset_x*2,getHeight()-offset_y*2,img.getImageObserver());
    }

    @Override
    public void setLocation(int x, int y) {
        super.setLocation(x, y);
        aimx=x;
        aimy=y;
    }

}
