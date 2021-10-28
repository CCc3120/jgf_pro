package com.bingo.main;

import java.awt.*;
import java.io.IOException;

import javax.swing.*;

public class MessageDialogUtil {

    public static JPanel getMessage(String message) {
        JPanel panel = new JPanel();
        panel.setBackground(new Color(102, 205, 170));
        panel.setSize(new Dimension(178, 46));
        panel.setLayout(null);

        JLabel label = new JLabel(message);
        label.setBounds(0, 0, 178, 46);
        label.setHorizontalAlignment(SwingConstants.CENTER);
        panel.add(label);
        return panel;
    }

    public static void main(String[] args) throws IOException, ClassNotFoundException,
            UnsupportedLookAndFeelException, InstantiationException, IllegalAccessException {
        // UIManager.put("OptionPane.minimumSize", new Dimension(178, 50));
        String lookAndFeel = UIManager.getSystemLookAndFeelClassName();
        UIManager.setLookAndFeel(lookAndFeel);
        
        JOptionPane.showMessageDialog(null, MessageDialogUtil.getMessage("终于承认啦"), "♥♥♥",
                JOptionPane.PLAIN_MESSAGE, ImageUtil.getResize(ParamConstant.di_size,
                        ParamConstant.di_size, ImageUtil.lips, false, false));
    }
}
