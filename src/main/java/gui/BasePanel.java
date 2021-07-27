package gui;

import utils.tool;
import widget.Arrow;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.Area;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;
import java.util.*;

public class BasePanel extends JPanel {
    public static Vector<NodeView> all_nodes=new Vector<NodeView>();
    public static NodeView click_work,passby_wrok;
    public static Point now_pos;

    int startX,startY,endX,endY;
    boolean moving;

    public BasePanel(){
        setLayout(null);

        KeyboardFocusManager manager = KeyboardFocusManager.getCurrentKeyboardFocusManager();
        //然后得到当前键盘事件的管理器
        manager.addKeyEventPostProcessor(getMyKeyEventHandler());
        //然后为管理器添加一个新的键盘事件监听者。

        addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {

            }

            @Override
            public void mousePressed(MouseEvent e) {
                    endX = startX = e.getXOnScreen() - 4 - GUIMain.frame.getX();
                    endY = startY = e.getYOnScreen() - (27 + GUIMain.menubar1.getHeight() + 31) - GUIMain.frame.getY();
                    moving = true;
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                if(GUIMain.mode==GUIMain.DELLINE){
                    moving=false;
                    removeArrows();
                }else{
                    moving=false;
                    Rectangle rect=new Rectangle(Math.min(startX,endX),Math.min(startY,endY),Math.abs(endX-startX), Math.abs(endY-startY));
                    for (NodeView nv:all_nodes){
                        if(rect.contains(nv.getBounds()))nv.choose(!nv.ischoosed());
                    }
                }
            }

            @Override
            public void mouseEntered(MouseEvent e) {

            }

            @Override
            public void mouseExited(MouseEvent e) {

            }
        });
        addMouseMotionListener(new MouseMotionListener() {
            @Override
            public void mouseDragged(MouseEvent e) {
                    endX = e.getXOnScreen() - 4 - GUIMain.frame.getX();
                    endY = e.getYOnScreen() - (27 + GUIMain.menubar1.getHeight() + 31) - GUIMain.frame.getY();
            }

            @Override
            public void mouseMoved(MouseEvent e) {

            }
        });
    }
    public KeyEventPostProcessor getMyKeyEventHandler() {
        return new KeyEventPostProcessor()//返回一个实现KeyEventPostProcessor接口的匿名内部类。
        {
            public boolean postProcessKeyEvent(KeyEvent e)//实现postProcessKeyEvent方法
            {
                if(e.getKeyCode()==KeyEvent.VK_DELETE){
                        for (int i=0;i<all_nodes.size();i++){
                            NodeView wv=all_nodes.get(i);
                            if(wv.ischoosed()){
                                removeWork(wv);
                                i--;
                            }
                        }
                        return true;
                }
                if(e.isControlDown()&&e.getKeyCode()==KeyEvent.VK_S){
                    saveWorks();
                }

                return false;
            }
        };
    }
    public void saveWorks(){

    }

    public void addWork(NodeView wv){
        all_nodes.add(wv);
        add(wv);
    }
    public void removeWork(NodeView nv){
        nv.conn_nodes.forEach((x)->x.remove(nv));
        all_nodes.remove(nv);
        remove(nv);
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        if(moving){
            if(GUIMain.mode==GUIMain.DELLINE) {
                g.setColor(Color.RED);
                g.drawLine(startX, startY, endX, endY);
            }else{
                Graphics2D g2d=(Graphics2D)g;
                /*g2d.setColor(new Color(0xae,0xe4,0xff,0xff));
                g2d.setStroke(new BasicStroke());
                g2d.drawRect(startX, startY, endX-startX, endY-startY);*/
                g2d.setColor(new Color(0x66,0xcc,0xff,0x90));
                g2d.fillRect(startX, startY, endX-startX, endY-startY);
            }
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2d=(Graphics2D)g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        g2d.setRenderingHint(RenderingHints.KEY_STROKE_CONTROL, RenderingHints.VALUE_STROKE_PURE);

        super.paintComponent(g2d);

        g2d.setStroke(new BasicStroke(2.5f));
        if(click_work!=null&&now_pos!=null){
            Point click_pos=click_work.getCenter();
            //Arrow.drawAL(click_pos.x,click_pos.y,now_pos.x,now_pos.y,g2d);
            g2d.drawLine(click_pos.x,click_pos.y,now_pos.x,now_pos.y);
        }
        drawArrows(g2d);//绘制图节点间的箭头

        g2d.setFont(new Font("黑体",Font.BOLD,20));

        g2d.setColor(Color.orange);
        all_nodes.forEach((nv)->{
            if(nv.inc_count>0)
                g2d.drawString("+"+nv.inc_count,nv.getX()+nv.getWidth()-3, nv.getY()+5);
        });
        g2d.setColor(Color.black);
    }

    public void clearChoose(){
        for(NodeView wv:all_nodes)wv.choose(false);
    }

    public void drawArrows(Graphics2D g2d){
        for(NodeView nv:all_nodes){
            Point thispos=nv.getCenter();
            for (int i = 0; i < nv.conn_nodes.size(); i++) {
                NodeView x=nv.conn_nodes.get(i);
                Point ptemp=x.getCenter();
                g2d.setColor(Color.black);
                //Arrow.drawAL(thispos.x,thispos.y,ptemp.x-10,ptemp.y,g2d);

                Area outside = new Area(new Rectangle2D.Double(0, 0, getWidth(), getHeight()));
                outside.subtract(new Area(new Ellipse2D.Double(x.getX(),x.getY(),x.getWidth(),x.getHeight())));
                outside.subtract(new Area(new Ellipse2D.Double(nv.getX(),nv.getY(),nv.getWidth(),nv.getHeight())));
                g2d.setClip(outside);

                g2d.drawLine(thispos.x,thispos.y,ptemp.x,ptemp.y);

                g2d.setClip(null);
            }
        }
    }
    public void removeArrows(){
        for(NodeView nv:all_nodes){
            Point thispos=nv.getCenter();
            for (int i = 0; i < nv.conn_nodes.size(); i++) {
                NodeView x=nv.conn_nodes.get(i);
                Point ptemp=x.getCenter();
                if(tool.isIntersect(thispos.x,thispos.y,ptemp.x-10,ptemp.y,startX,startY,endX,endY)){
                    nv.remove(x);
                    i--;
                }
            }
        }
    }

    public void poi(){
        all_nodes.forEach(NodeView::poi);
    }
}
