package com.bingo.main;

import java.awt.*;
import java.io.IOException;

import javax.swing.*;

public class MessageDialogUtil {

    public static JPanel getMessage(String message) {
        JPanel panel = new JPanel();
        panel.setBackground(new Color(102, 205, 170));
        panel.setSize(new Dimension(235, 52));
        panel.setLayout(null);

        JLabel label = new JLabel(message);
        label.setBounds(0, 0, 235, 48);
        label.setHorizontalAlignment(SwingConstants.CENTER);
        panel.add(label);
        return panel;
    }
    
    public static void main(String[] args) throws IOException, ClassNotFoundException,
            UnsupportedLookAndFeelException, InstantiationException, IllegalAccessException {
        UIManager.put("OptionPane.minimumSize", new Dimension(320, 108));
        String lookAndFeel = UIManager.getSystemLookAndFeelClassName();
        UIManager.setLookAndFeel(lookAndFeel);

        JOptionPane.showMessageDialog(null, MessageDialogUtil.getMessage("温馨提示，左上角有一个按钮哦！！！"),
                "(*^▽^*)", JOptionPane.INFORMATION_MESSAGE, ImageUtil.getResize(ParamConstant.di_size,
                        ParamConstant.di_size, ImageUtil.dips, false, false));
    }
}
