package com.bingo.cal;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.*;

public class CalculatorJFrame extends JFrame {

    JTextField textField = new JTextField();

    JButton btn1 = new JButton("1");
    JButton btn2 = new JButton("2");
    JButton btn3 = new JButton("3");
    JButton btn4 = new JButton("+");
    JButton btn5 = new JButton("4");
    JButton btn6 = new JButton("5");
    JButton btn7 = new JButton("6");
    JButton btn8 = new JButton("-");
    JButton btn9 = new JButton("7");
    JButton btna = new JButton("8");
    JButton btnb = new JButton("9");
    JButton btnc = new JButton("*");
    JButton btnd = new JButton(".");
    JButton btne = new JButton("0");
    JButton btnf = new JButton("=");
    JButton btng = new JButton("/");

    JPanel jp1 = new JPanel();
    JPanel jp2 = new JPanel();

    double num1, num2, num3;
    boolean end, add, mul, sub, div;

    List<JButton> list = new ArrayList() {
        {
            add(btn1);
            add(btn2);
            add(btn3);
            add(btn4);
            add(btn5);
            add(btn6);
            add(btn7);
            add(btn8);
            add(btn9);
            add(btna);
            add(btnb);
            add(btnc);
            add(btnd);
            add(btne);
            add(btnf);
            add(btng);
        }
    };

    public CalculatorJFrame() throws HeadlessException {
        super("计算器");
    }

    public void init() throws ClassNotFoundException, UnsupportedLookAndFeelException,
            InstantiationException, IllegalAccessException {
        // 设置当前系统默认风格
        UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());

        // 窗口大小不可变
        this.setResizable(false);
        this.setVisible(true);
        // 设置屏幕中间显示,x/y参数无效
        this.setBounds(100, 100, 250, 250);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // textField.setBounds(0, 0, 200, 50);
        // textField.setSize(200,50);
        textField.setEditable(false);
        textField.setHorizontalAlignment(JTextField.RIGHT);
        textField.setColumns(18);
        jp1.add(textField);
        jp1.setSize(200, 50);
        jp1.setBounds(0, 0, 200, 50);
        this.add(jp1, BorderLayout.NORTH);

        // 网格布局
        GridLayout gridLayout = new GridLayout(4, 0);
        gridLayout.setVgap(10);
        gridLayout.setHgap(10);
        jp2.setLayout(gridLayout);
        for (JButton button : list) {
            jp2.add(button);
        }
        this.add(jp2, BorderLayout.CENTER);

        JLabel label1 = new JLabel();
        label1.setPreferredSize(new Dimension(10, 0));
        this.add(label1, BorderLayout.WEST);
        JLabel label2 = new JLabel();
        label2.setPreferredSize(new Dimension(10, 0));
        this.add(label2, BorderLayout.EAST);
        JLabel label3 = new JLabel();
        label3.setPreferredSize(new Dimension(0, 10));
        this.add(label3, BorderLayout.SOUTH);

        for (JButton jButton : list) {
            jButton.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent event) {
                    event(event);
                }
            });
        }

        textField.setText("0");
    }

    public void event(MouseEvent event) {
        if (event.getSource() == btn1) {
            num(1);
        }
        if (event.getSource() == btn2) {
            num(2);
        }
        if (event.getSource() == btn3) {
            num(3);
        }
        if (event.getSource() == btn4) {
            sign(1);
        }
        if (event.getSource() == btn5) {
            num(4);
        }
        if (event.getSource() == btn6) {
            num(5);
        }
        if (event.getSource() == btn7) {
            num(6);
        }
        if (event.getSource() == btn8) {
            sign(2);
        }
        if (event.getSource() == btn9) {
            num(7);
        }
        if (event.getSource() == btna) {
            num(8);
        }
        if (event.getSource() == btnb) {
            num(9);
        }
        if (event.getSource() == btnc) {
            sign(3);
        }
        if (event.getSource() == btnd) {
            point();
        }
        if (event.getSource() == btne) {
            num(0);
        }
        if (event.getSource() == btnf) {
            equal();
        }
        if (event.getSource() == btng) {
            sign(4);
        }
    }

    public void num(int i) {
        String s = String.valueOf(i);
        if (end) {
            textField.setText("0");
            end = false;
        }
        if (textField.getText().equals("0")) {
            textField.setText(s);
        } else {
            textField.setText(textField.getText() + s);
        }
    }

    public void sign(int i) {
        if (i == 1) {
            add = true;
            mul = false;
            sub = false;
            div = false;
        } else if (i == 2) {
            add = false;
            mul = true;
            sub = false;
            div = false;
        } else if (i == 3) {
            add = false;
            mul = false;
            sub = true;
            div = false;
        } else if (i == 4) {
            add = false;
            mul = false;
            sub = false;
            div = true;
        }
        end = true;
        num1 = Double.parseDouble(textField.getText());
    }

    public void point() {
        String s;
        if (textField.getText().indexOf(".") < 0) {
            s = textField.getText() + ".";
            textField.setText(s);
        }
    }

    public void equal() {
        num2 = Double.parseDouble(textField.getText());
        if (add) {
            num3 = num1 + num2;
        }
        if (mul) {
            num3 = num1 - num2;
        }
        if (sub) {
            num3 = num1 * num2;
        }
        if (div) {
            num3 = num1 / num2;
        }

        String s = String.valueOf(num3);
        if (s.indexOf(".") != -1) {
            String a = s.substring(s.indexOf(".") + 1);
            if (Integer.parseInt(a) == 0) {
                s = s.substring(0, s.indexOf("."));
            }
        }
        textField.setText(s);
        end = true;
    }
}
