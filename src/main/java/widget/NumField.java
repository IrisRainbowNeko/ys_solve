package widget;

import javax.swing.*;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.PlainDocument;
import java.awt.*;

public class NumField extends JTextField
{

    public NumField(String cols)
    {
        super(cols);
        setMargin(new Insets(1, 1, 1, 1));
        setFont(new Font("黑体",Font.BOLD,20));
    }

    protected Document createDefaultModel()
    {
        return new NumDocument();
    }

    static class NumDocument extends PlainDocument
    {

        public void insertString(int offs, String str, AttributeSet a) throws BadLocationException
        {
            if (str == null)
            {
                return;
            }
            char[] upper = str.toCharArray();
            String temp="";
            for (char ch:upper)
            {
                if (Character.isDigit(ch))
                    temp+=ch;
            }
            str=temp;
            super.insertString(offs, str , a);
        }
    }
    public void setValue(int num){
        setText(num+"");
    }
    public void setValue(String str){
        if (str == null)
        {
            return;
        }
        char[] upper = str.toCharArray();
        String temp="";
        for (char ch:upper)
        {
            if (Character.isDigit(ch))
                temp+=ch;
        }
        setText(temp);
    }
    public int getValue(){
        return Integer.parseInt(getText());
    }
}
