package utils;

import gui.GUIMain;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class tool {
    public static ImageIcon loadImage(String path){
        return new ImageIcon(ClassLoader.getSystemResource(path));
    }
    public static ImageIcon loadImage(String path,int w,int h){
        ImageIcon img=new ImageIcon(ClassLoader.getSystemResource(path));
        img.setImage(img.getImage().getScaledInstance(w,h, Image.SCALE_DEFAULT));
        return img;
    }
    public static boolean isIntersect(double px1,double py1,double px2,double py2,double px3,double py3,double px4,double py4)//判断线段是否相交
    {
        boolean flag = false;
        double d = (px2-px1)*(py4-py3) - (py2-py1)*(px4-px3);
        if(d!=0)
        {
            double r = ((py1-py3)*(px4-px3)-(px1-px3)*(py4-py3))/d;
            double s = ((py1-py3)*(px2-px1)-(px1-px3)*(py2-py1))/d;
            if((r>=0) && (r <= 1) && (s >=0) && (s<=1))
            {
                flag = true;
            }
        }
        return flag;
    }

    public static boolean fuzzyQuery(String res,String aim){//模糊查询
        String aim_fu="";
        for(int i=0;i<aim.length();i++)aim_fu+=(aim.charAt(i)+"*");
        return regexMatch(res,aim_fu);
    }
    public static boolean regexMatch(String res,String aim){//正则表达式匹配
        Pattern p=Pattern.compile(cutlast(aim));
        Matcher m=p.matcher(res);
        return m.find();
    }
    public static String cutlast(String str){
        return str.substring(0,str.length()-1);
    }
    public static String copmleteString(String res,String comp,int count){
        for(int i=res.length();i<count;i++)res=comp+res;
        return res;
    }

    public static File chooseFile(){
        JFileChooser jfc=new JFileChooser();
        jfc.setFileSelectionMode(JFileChooser.FILES_ONLY);
        jfc.showDialog(new JLabel(), "选择文件");
        return jfc.getSelectedFile();
    }

    /// <summary>
    /// 将指定的自然数转换为26进制表示。映射关系：[1-26] ->[A-Z]。
    /// </summary>
    /// <param name="n">自然数（如果无效，则返回空字符串）。</param>
    /// <returns>26进制表示。</returns>
    public static String ToNumberSystem26(int n){
        String s = "";
        while (n > 0){
            int m = n % 26;
            if (m == 0) m = 26;
            s = (char)(m + 64) + s;
            n = (n - m) / 26;
        }
        return s;
    }

    /// <summary>
    /// 将指定的26进制表示转换为自然数。映射关系：[A-Z] ->[1-26]。
    /// </summary>
    /// <param name="s">26进制表示（如果无效，则返回0）。</param>
    /// <returns>自然数。</returns>
    public static int FromNumberSystem26(String s){
        int n = 0;
        for (int i = s.length() - 1, j = 1; i >= 0; i--, j *= 26){
            char c = Character.toUpperCase(s.charAt(i));
            if (c < 'A' || c > 'Z') return 0;
            n += ((int)c - 64) * j;
        }
        return n;
    }

    public static Map<String,Integer> frequencyOfListElements(List<String> items) {
        if (items == null || items.size() == 0) return null;
        Map<String, Integer> map = new HashMap<String, Integer>();
        for (String temp : items) {
            Integer count = map.get(temp);
            map.put(temp, (count == null) ? 1 : count + 1);
        }
        return map;
    }
}
