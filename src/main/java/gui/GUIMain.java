package gui;

import core.BFSSolver;
import core.DFSSolver;
import core.MatSolver;
import utils.Graph;
import utils.Vertex;
import utils.tool;
import widget.DPanel;
import widget.ImgPanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Vector;

public class GUIMain {
    public static Insets main_insets;
    public static JMenuBar menubar1;
    public static JFrame frame;//主窗体
    public static BasePanel base;//背景平面
    ImgPanel floatpanel=new ImgPanel();
    Container content;//主窗体内含平面

    public static int mode=0;//鼠标模式

    public static final int DELLINE=1;

    public void showMainFrame(){//显示主窗体
        JFrame.setDefaultLookAndFeelDecorated(true);//让界面更好看
        frame=new JFrame("From");
        content=frame.getContentPane();//获取内含平面
        frame.setSize(1000,800);//设置大小
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);//关闭主窗体时程序结束
        frame.setLocationRelativeTo(null);//窗体放在屏幕中间
        content.setLayout(null);//不用布局管理器
        main_insets=frame.getInsets();

        base=new BasePanel();//new一个背景平面
        base.setSize(1920,1080);//设置大小
        content.add(base);//加入主窗体

        initToolBar();//设置工具栏

        base.setLocation(0,30);
        frame.setVisible(true);
        render.start();
    }

    public void initToolBar(){//设置工具栏
        menubar1 = new JMenuBar();
        frame.setJMenuBar(menubar1);//给主窗体添加工具栏
        //文件菜单
        {
            JMenu menu_file = new JMenu("操作");
            menubar1.add(menu_file);
            JMenuItem item1 = new JMenuItem("BFS求解");
            item1.addActionListener(e -> {
                Map<String, Integer> counter=solve(BasePanel.all_nodes, 1);
                BasePanel.all_nodes.forEach((nv)->{
                    if(counter.containsKey(nv.name))
                        nv.inc_count=counter.get(nv.name);
                    else
                        nv.inc_count=0;
                });
            });
            JMenuItem item2 = new JMenuItem("DFS求解");
            item2.addActionListener(e -> {
                Map<String, Integer> counter=solve(BasePanel.all_nodes, 2);
                BasePanel.all_nodes.forEach((nv)->{
                    if(counter.containsKey(nv.name))
                        nv.inc_count=counter.get(nv.name);
                    else
                        nv.inc_count=0;
                });
            });
            JMenuItem item3 = new JMenuItem("方程求解");
            item3.addActionListener(e -> {
                Map<String, Integer> counter=solve(BasePanel.all_nodes, 3);
                BasePanel.all_nodes.forEach((nv)->{
                    if(counter.containsKey(nv.name))
                        nv.inc_count=counter.get(nv.name);
                    else
                        nv.inc_count=0;
                });
            });


            JMenuItem item4 = new JMenuItem("设置状态数");
            item4.addActionListener(e -> {
                showStateSetFrame();
            });
            menu_file.add(item1);
            menu_file.addSeparator();
            menu_file.add(item2);
            menu_file.addSeparator();
            menu_file.add(item3);
            menu_file.addSeparator();
            menu_file.add(item4);
            menu_file.addSeparator();
        }

        //工具条
        JToolBar bar = new JToolBar();
        JButton bu_addwork = new JButton();//新建添加工作按钮
        bu_addwork.setOpaque(false);//设置透明
        bu_addwork.setContentAreaFilled(false);
        bu_addwork.setMargin(new Insets(0, 0, 0, 0));//设置边距为0
        bu_addwork.setBorderPainted(false);//不画边框
        bu_addwork.setRolloverEnabled(true);  //允许翻转图标
        bu_addwork.setIcon(tool.loadImage("add.png",25,25));//设置平常图片
        bu_addwork.setRolloverIcon(tool.loadImage("add_over.png",25,25));//设置鼠标滑过时图片
        bu_addwork.setPressedIcon(tool.loadImage("add.png",25,25));//设置点击时图片

        bu_addwork.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                //鼠标单击
            }

            @Override
            public void mousePressed(MouseEvent e) {
                //鼠标按下
                floatpanel.setImage(tool.loadImage("add.png",25,25));//给浮动平面设置图片
                floatpanel.setVisible(true);//设置为可见
                floatpanel.setLocation(e.getXOnScreen()-frame.getX()-3,e.getYOnScreen()-frame.getY());//跟随鼠标移动
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                floatpanel.setVisible(false);//设置为不可见
                NodeView nv_temp=new NodeView(tool.ToNumberSystem26(BasePanel.all_nodes.isEmpty()?1:(tool.FromNumberSystem26(BasePanel.all_nodes.lastElement().name)+1)), 0);//新建一个节点
                nv_temp.setLocation(e.getXOnScreen()-frame.getX()-3-nv_temp.getWidth()/2,e.getYOnScreen()-(27+ GUIMain.menubar1.getHeight()+31)-frame.getY()-nv_temp.getHeight()/2);
                base.addWork(nv_temp);//加入平面
            }

            @Override
            public void mouseEntered(MouseEvent e) {

            }

            @Override
            public void mouseExited(MouseEvent e) {

            }
        });
        bu_addwork.addMouseMotionListener(new MouseMotionListener() {
            @Override
            public void mouseDragged(MouseEvent e) {
                //鼠标拖动
                floatpanel.setLocation(e.getXOnScreen()-frame.getX()-15-3,e.getYOnScreen()-frame.getY()-(27+ GUIMain.menubar1.getHeight()+15));//跟随鼠标
            }

            @Override
            public void mouseMoved(MouseEvent e) {

            }
        });

        JButton bu_delline = new JButton();
        bu_delline.setOpaque(false);
        bu_delline.setContentAreaFilled(false);
        bu_delline.setMargin(new Insets(0, 0, 0, 0));
        bu_delline.setBorderPainted(false);
        bu_delline.setRolloverEnabled(true);  //允许翻转图标
        bu_delline.setIcon(tool.loadImage("scissors.png",25,25));
        bu_delline.setRolloverIcon(tool.loadImage("scissors_over.png",25,25));
        bu_delline.setPressedIcon(tool.loadImage("scissors.png",25,25));
        bu_delline.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if(mode!=1){
                    mode=1;
                    //更改鼠标图片
                    Toolkit tk = Toolkit.getDefaultToolkit();
                    Image image = tool.loadImage("scissors.png",25,25).getImage();
                    Cursor cursor = tk.createCustomCursor(image, new Point(10, 10), "norm");
                    base.setCursor(cursor); //panel 也可以是其他组件
                }
                else{
                    mode=0;
                    base.setCursor(Cursor.getDefaultCursor());//还原鼠标图片
                }
            }

            @Override
            public void mousePressed(MouseEvent e) {

            }

            @Override
            public void mouseReleased(MouseEvent e) {

            }

            @Override
            public void mouseEntered(MouseEvent e) {

            }

            @Override
            public void mouseExited(MouseEvent e) {

            }
        });

        bar.add(bu_addwork);
        bar.add(bu_delline);
        bar.setSize(1920,30);
        content.add(bar);
        floatpanel.setSize(30,30);
        floatpanel.setVisible(false);
        content.add(floatpanel,0);
    }

    public void showStateSetFrame(){//显示模糊查询界面
        JFrame sframe=new JFrame("设置状态数");
        sframe.setSize(230,100);
        sframe.getContentPane().setLayout(null);
        sframe.setLocationRelativeTo(null);
        JTextField txf=new JTextField();
        sframe.getContentPane().add(txf);
        sframe.getContentPane().add(Box.createHorizontalStrut(10));
        JButton bu=new JButton("确定");
        bu.setSize(60,30);
        bu.setLocation(150,(100-30-26)/2);
        sframe.getContentPane().add(bu);
        txf.setSize(130,30);
        txf.setLocation(10,(100-30-26)/2);

        bu.addActionListener(e -> {
            BasePanel.all_nodes.forEach((nv)->{
                if(nv.ischoosed()){
                    nv.setState(Integer.parseInt(txf.getText()));
                }
            });
            sframe.dispose();
        });
        sframe.setVisible(true);
    }

    public Map<String, Integer> solve(Vector<NodeView> all_nodes, int type){
        HashMap<String, Integer> vtx=new HashMap<String, Integer>();
        HashMap<String, LinkedList<String>> edge=new HashMap<String, LinkedList<String>>();
        HashMap<String, Integer> state=new HashMap<String, Integer>();
        HashMap<String, Boolean> active=new HashMap<String, Boolean>();

        all_nodes.forEach((nv)->{
            vtx.put(nv.name, nv.getNum());
            LinkedList<String> vlist=new LinkedList<String>();
            nv.conn_nodes.forEach((cnv)->vlist.add(cnv.name));
            edge.put(nv.name, vlist);
            state.put(nv.name, nv.getState());
            active.put(nv.name, nv.active);
        });

        Graph gra=new Graph(new Vertex(vtx, state, active), edge);
        Vertex res=null;
        switch (type){
            case 1:
                res=new BFSSolver().solve(gra);
                break;
            case 2:
                res=new DFSSolver().solve(gra);
                break;
            case 3:
                res=new MatSolver().solve(gra);
                break;
        }

        //Map<String, Integer> counter=tool.frequencyOfListElements(res.data);
        return res.data;
    }

    //渲染线程
    Thread render=new Thread(){
        @Override
        public void run() {
            while (true){
                try {
                    content.repaint();
                }catch (Throwable e){
                    e.printStackTrace();
                }
                base.poi();
                try {
                    Thread.sleep(20);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    };
}
