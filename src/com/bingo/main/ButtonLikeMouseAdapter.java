package com.bingo.main;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;

import javax.swing.*;

import com.bingo.util.MessageType;
import com.bingo.util.SendUtil;

public class ButtonLikeMouseAdapter extends MouseAdapter {

    private JFrame jFrame;

    private JButton button;

    public ButtonLikeMouseAdapter(JFrame jFrame, JButton button) {
        this.jFrame = jFrame;
        this.button = button;
    }


    @Override
    public void mouseClicked(MouseEvent event) {
        try {
            SendUtil.sendMessage(MessageType.OP_BT_LIKE);

            // 按钮背景图
            button.setIcon(ImageUtil.getResize(ParamConstant.bt_width, ParamConstant.bt_htight,
                    ImageUtil.btps, false, false));

            JOptionPane.showMessageDialog(jFrame, MessageDialogUtil.getMessage("终于承认啦"), "♥♥♥",
                    JOptionPane.PLAIN_MESSAGE, ImageUtil.getResize(ParamConstant.di_size,
                            ParamConstant.di_size, ImageUtil.lips, false, false));

            JOptionPane.showMessageDialog(jFrame, MessageDialogUtil.getMessage("我就知道你喜欢我"), "♥♥♥",
                    JOptionPane.PLAIN_MESSAGE, ImageUtil.getResize(ParamConstant.di_size,
                            ParamConstant.di_size, ImageUtil.lips, false, false));

            JOptionPane.showMessageDialog(jFrame, MessageDialogUtil.getMessage("告诉你一个秘密"), "♥♥♥",
                    JOptionPane.PLAIN_MESSAGE, ImageUtil.getResize(ParamConstant.di_size,
                            ParamConstant.di_size, ImageUtil.lips, false, false));

            JOptionPane.showMessageDialog(jFrame, MessageDialogUtil.getMessage("其实我也喜欢你"), "♥♥♥",
                    JOptionPane.PLAIN_MESSAGE, ImageUtil.getResize(ParamConstant.di_size,
                            ParamConstant.di_size, ImageUtil.lips, false, false));

            JOptionPane.showMessageDialog(jFrame, MessageDialogUtil.getMessage("拜拜啦！！！"), "♥♥♥",
                    JOptionPane.PLAIN_MESSAGE, ImageUtil.getResize(ParamConstant.di_size,
                            ParamConstant.di_size, ImageUtil.lips, false, false));

            // 退出
            SendUtil.sendMessage(MessageType.OP_CLOSE);
            System.exit(0);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void mouseEntered(MouseEvent event) {
        try {
            // 绘制边框
            button.setBorderPainted(true);
            button.setBorder(BorderFactory.createLineBorder(Color.BLUE));
            // 文字颜色
            button.setForeground(Color.YELLOW);
            // 按钮背景图
            button.setIcon(ImageUtil.getResize(ParamConstant.bt_width, ParamConstant.bt_htight,
                    ImageUtil.btps, false, false));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void mouseExited(MouseEvent event) {
        // 不绘制边框
        button.setBorderPainted(false);
        // 文字颜色
        button.setForeground(Color.PINK);
    }
}
