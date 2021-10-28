package com.bingo.main;

import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;

import javax.swing.*;

import com.bingo.util.MessageType;
import com.bingo.util.SendUtil;

public class CustomWindowAdapter extends WindowAdapter {

    private JFrame jFrame;

    public CustomWindowAdapter(JFrame jFrame) {
        this.jFrame = jFrame;
    }

    @Override
    public void windowClosing(WindowEvent event) {
        try {
            SendUtil.sendMessage(MessageType.OP_BT_CLOSE);

            JOptionPane.showMessageDialog(jFrame, MessageDialogUtil.getMessage("你个大傻子，关不掉的！"), "￣へ￣",
                    JOptionPane.PLAIN_MESSAGE, ImageUtil.getResize(ParamConstant.di_size,
                            ParamConstant.di_size, ImageUtil.dips, false, false));

            JOptionPane.showMessageDialog(jFrame, MessageDialogUtil.getMessage("温馨提示，左上角有一个按钮哦！！！"), 
                    "(*^▽^*)", JOptionPane.INFORMATION_MESSAGE, ImageUtil.getResize(ParamConstant.di_size,
                            ParamConstant.di_size, ImageUtil.dips, false, false));

            SendUtil.sendMessage(MessageType.OP_CLOSE_FAIL);
        } catch (HeadlessException | IOException e) {
            e.printStackTrace();
        }
    }
}
