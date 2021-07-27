package widget;

import javax.swing.*;
import java.awt.*;

public class DPanel extends JPanel {
    Color col,col_choose;
    int offset_x,offset_y;
    private boolean choosed;

    double mdx,mdy,nowx,nowy;
    int movetime=0;
    public int aimx,aimy;

    public DPanel(){
        setBackground(null);
        setOpaque(false);
    }

    public void setColor(Color col){
        this.col=col;
    }
    public void setColor_choose(Color col){
        this.col_choose=col;
    }
    public void setOffset(int x,int y){
        offset_x=x;offset_y=y;
    }
    public void choose(boolean cho){
        choosed=cho;
    }
    public boolean ischoosed(){
        return choosed;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        ((Graphics2D)g).setStroke(new BasicStroke(2.5f));
        g.setColor(choosed?col_choose:col);
        g.drawOval(offset_x+2,offset_y+2,getWidth()-4,getHeight()-4);
        //if(img!=null)g.drawImage(choosed?img_choose.getImage():img.getImage(),offset_x,offset_y,getWidth(),getHeight(),img.getImageObserver());
    }

    @Override
    public void setLocation(int x, int y) {
        super.setLocation(x, y);
        aimx=x;
        aimy=y;
    }

    public Point getCenter(){
        return new Point(getX()+getWidth()/2, getY()+getHeight()/2);
    }

    public void poi(){
        if(movetime>0) {
            nowx+=mdx;
            nowy+=mdy;
            setLocation((int)nowx, (int)nowy);
            movetime -= 20;
        }
    }
}
