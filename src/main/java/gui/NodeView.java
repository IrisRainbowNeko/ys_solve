package gui;

import widget.DPanel;
import widget.NumField;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.event.*;
import java.util.Vector;

public class NodeView extends DPanel {
    int item_size=60;

    float X,Y,last_x,last_y;
    public Vector<NodeView> conn_nodes=new Vector<NodeView>();

    String name="";
    int num=0, state=3, inc_count=0;
    boolean active=true;

    NumField tx_num, tx_state;
    JLabel la_name;

    int base_w,base_h,scale_count;
    double scale_rate=1,base_x,base_y;

    boolean move_flag=true;

    public NodeView(){
        init();
    }
    public NodeView(String name, int num){
        this.name=name;
        this.num=num;
        init();
    }
    public NodeView(NodeView wv){
        this.name=wv.name;
        this.num=wv.num;
        init();
    }

    public void init(){
        setLayout(null);
        setOffset(0,0);
        initEdit();
        initMouse();
        initButton();
        setSize(item_size,item_size);
        setColor(Color.black);
        setColor_choose(Color.cyan);
        base_w=getWidth();
        base_h=getHeight();
        base_x=getX();
        base_y=getY();

        la_name=new JLabel(name);
        la_name.setSize(la_name.getPreferredSize());
        la_name.setLocation(item_size/2-la_name.getWidth()/2, (int)(item_size*0.25f-la_name.getHeight()/2));
        add(la_name);
    }

    public void initEdit(){
        float tx_size=item_size*0.4f;
        //添加可编辑文本框
        tx_num=new NumField(num+"");
        tx_num.setEditable(true);
        tx_num.setBounds((int)(item_size*0.32f-tx_size/2+1), (int)(item_size*0.65f-tx_size/2), (int)tx_size, (int)tx_size);
        tx_num.setHorizontalAlignment(JTextField.CENTER);
        add(tx_num);

        tx_state=new NumField(state+"");
        tx_state.setEditable(false);
        tx_state.setBounds((int)(item_size*0.68f-tx_size/2+1), (int)(item_size*0.65f-tx_size/2), (int)tx_size, (int)tx_size);
        tx_state.setHorizontalAlignment(JTextField.CENTER);
        add(tx_state);
    }

    public void initMouse(){
        //添加鼠标事件
        addMouseListener(new MouseListener() {

            public void mouseReleased(MouseEvent e) {
                //鼠标松开

            }
            public void mousePressed(MouseEvent e) {
                //鼠标按下
                if(e.getButton()==1) {
                    X = e.getXOnScreen();
                    Y = e.getYOnScreen();
                    last_x = getX();
                    last_y = getY();
                    move_flag=true;
                }
            }
            public void mouseExited(MouseEvent e) {}
            public void mouseEntered(MouseEvent e) {}
            public void mouseClicked(MouseEvent e) {
                if(e.getButton()==1)
                    choose(!ischoosed());
                else if(e.getButton()==3) {
                    active = !active;
                    if(active){
                        setColor(Color.black);
                        setColor_choose(Color.cyan);
                    } else {
                        setColor(Color.red);
                        setColor_choose(Color.magenta);
                    }
                }
            }
        });
        addMouseMotionListener(new MouseMotionListener() {
            @Override
            public void mouseDragged(MouseEvent e) {
                if(move_flag)
                    setLocation((int)(last_x+e.getXOnScreen()-X),(int)(last_y+e.getYOnScreen()-Y));
            }

            @Override
            public void mouseMoved(MouseEvent e) {

            }
        });
    }

    public void initButton(){
        addMouseListener(new MouseListener() {

            public void mouseReleased(MouseEvent e) {
                if(!move_flag) {
                    if (BasePanel.passby_wrok != null) {
                        BasePanel.click_work.lineto(BasePanel.passby_wrok);
                    }
                    BasePanel.passby_wrok = null;
                    BasePanel.click_work = null;
                    BasePanel.now_pos = null;
                }
            }
            public void mousePressed(MouseEvent e) {
                if(e.getButton()==3) {
                    BasePanel.click_work = NodeView.this;
                    move_flag=false;
                }
            }
            public void mouseExited(MouseEvent e) {
                BasePanel.passby_wrok=null;
            }
            public void mouseEntered(MouseEvent e) {
                BasePanel.passby_wrok=NodeView.this;
            }
            public void mouseClicked(MouseEvent e) {
                //鼠标按下
            }
        });

        addMouseMotionListener(new MouseMotionListener() {
            @Override
            public void mouseDragged(MouseEvent e) {
                if(!move_flag)
                    BasePanel.now_pos=new Point(e.getXOnScreen()-4-GUIMain.frame.getX(),e.getYOnScreen()- (27+GUIMain.menubar1.getHeight()+31)-GUIMain.frame.getY());
            }

            @Override
            public void mouseMoved(MouseEvent e) {

            }
        });
    }

    public void setState(int state){
        this.state=state;
        tx_state.setText(state+"");
    }

    public int getState(){
        state=Integer.parseInt(tx_state.getText());
        return state;
    }

    public void setNum(int num){
        this.num=num;
        tx_num.setText(num+"");
    }

    public int getNum(){
        num=Integer.parseInt(tx_num.getText());
        return num;
    }



    @Override
    public void paint(Graphics g) {
        if(scale_count>0)inScaleAnim((Graphics2D)g);
        super.paint(g);
    }

    @Override
    public void poi() {
        super.poi();
        if(scale_count>0) {
            scale_rate = 1 + 0.5 * ((scale_count / 3) * Math.sin(scale_count)) / 30;
            scale_count--;
            //double sw=base_w*scale_rate,sh=base_h*scale_rate;
            //System.out.println(sw+","+sh);
            //setLocation((int)(base_x-(sw-base_w)/2),(int)(base_y-(sh-base_h)/2));
            //setSize((int)sw,(int)sh);
        }

    }

    public void inScaleAnim(Graphics2D g2d){
        double sw=base_w*scale_rate,sh=base_h*scale_rate;
        g2d.scale(scale_rate,scale_rate);
        g2d.translate((base_x-(sw-base_w)/2),(base_y-(sh-base_h)/2));
    }
    public void showScaleAnim(){
        scale_count=20;
    }

    @Override
    public void choose(boolean cho) {
        if(cho!=ischoosed())showScaleAnim();
        super.choose(cho);

    }

    public void lineto(NodeView node){
        conn_nodes.add(node);
        node.conn_nodes.add(this);
    }
    public void remove(NodeView node){
        conn_nodes.remove(node);
    }

}
