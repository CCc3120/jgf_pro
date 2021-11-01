package com.bingo.main;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;

import javax.swing.*;

import com.bingo.util.SendUtil;

public class ButtonNotLikeMouseAdapter extends MouseAdapter {

    private JFrame jFrame;

    private JButton button;

    private ImageIcon background;

    public ButtonNotLikeMouseAdapter(JFrame jFrame, JButton button, ImageIcon background) {
        this.jFrame = jFrame;
        this.button = button;
        this.background = background;
    }

    /**
     * 已滑动次数
     */
    int count = 0;

    /**
     * 循环次数
     */
    int t_count = 1;

    @Override
    public void mouseEntered(MouseEvent event) {
        butMove();
    }
    
    @Override
    public void mousePressed(MouseEvent e) {
        butMove();
    }
    
    private void butMove() {
        try {
            Rectangle rectangle = button.getBounds();
            if (count < ParamConstant.s_count * t_count) {
                int pix;
                int piy;
                do {
                    // 循环生成x,y坐标，不能生成在原来的范围内
                    do {
                        // 减去按钮宽136
                        pix = ImageUtil.random.nextInt(background.getIconWidth() - 136);
                    } while (rectangle.x - ParamConstant.bt_width <= pix && rectangle.x + ParamConstant.bt_width >= pix);

                    do {
                        // 右下角定位需减89
                        piy = ImageUtil.random.nextInt(background.getIconHeight() - 89);
                    } while (rectangle.y - ParamConstant.bt_htight <= piy && rectangle.y + ParamConstant.bt_htight >= piy);

                    // 不能与喜欢按钮重叠
                } while (pix <= ParamConstant.bt_width && piy <= ParamConstant.bt_htight);

                rectangle.x = pix;
                rectangle.y = piy;
                button.setIcon(ImageUtil.getResize(ParamConstant.bt_width, ParamConstant.bt_htight,
                        ImageUtil.btps, false, false));
                button.setBounds(rectangle);
                ++count;
            } else {
                SendUtil.sendMessage("触发不喜欢惩罚，需再点击" + ParamConstant.c_count * t_count + "次");

                JOptionPane.showMessageDialog(jFrame, MessageDialogUtil.getMessage("还想点"), "￣へ￣",
                        JOptionPane.PLAIN_MESSAGE, ImageUtil.getResize(ParamConstant.di_size,
                                ParamConstant.di_size, ImageUtil.dips, false, false));

                JOptionPane.showMessageDialog(jFrame,
                        MessageDialogUtil.getMessage("那你再点" + ParamConstant.c_count * t_count + "次"), "￣へ￣"
                        , JOptionPane.PLAIN_MESSAGE, ImageUtil.getResize(ParamConstant.di_size,
                                ParamConstant.di_size, ImageUtil.dips, false, false));

                for (int i = 0; i < ParamConstant.c_count * t_count; i++) {
                    JOptionPane.showMessageDialog(jFrame, MessageDialogUtil.getMessage(i + 1 + "次"), "￣へ￣",
                            JOptionPane.PLAIN_MESSAGE, ImageUtil.getResize(ParamConstant.di_size,
                                    ParamConstant.di_size, ImageUtil.dips, false, false));
                }

                JOptionPane.showMessageDialog(jFrame, MessageDialogUtil.getMessage("这都不愿喜欢我"), "￣へ￣",
                        JOptionPane.PLAIN_MESSAGE, ImageUtil.getResize(ParamConstant.di_size,
                                ParamConstant.di_size, ImageUtil.dips, false, false));

                JOptionPane.showMessageDialog(jFrame, MessageDialogUtil.getMessage("你个大傻子，哼！"), "￣へ￣",
                        JOptionPane.PLAIN_MESSAGE, ImageUtil.getResize(ParamConstant.di_size,
                                ParamConstant.di_size, ImageUtil.dips, false, false));

                SendUtil.sendMessage("已点击" + ParamConstant.c_count * t_count + "次，不喜欢惩罚已结束");
                t_count++;
                count = 0;
            }
        } catch (HeadlessException | IOException e) {
            e.printStackTrace();
        }
    }
}
