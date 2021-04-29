package com.bingo.main;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.HeadlessException;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.WindowConstants;

import com.bingo.util.MessageType;
import com.bingo.util.SendUtil;

public class CustomJFrame extends JFrame {

	private static final long serialVersionUID = 1L;

	/**
	 * 已滑动次数
	 */
	int count = 0;

	/**
	 * 循环次数
	 */
	int t_count = 1;

	ImageIcon background;

	static Random random = new Random();

	// 背景图图片数组
	static List<String> bgps;
	// 按钮图图片数组
	static List<String> btps;
	// 对话框图图片数组
	static List<String> dips;
	// 喜欢对话框图片数组
	static List<String> lips;

	static {
		bgps = new ArrayList<>();
		btps = new ArrayList<>();
		dips = new ArrayList<>();
		lips = new ArrayList<>();
		File file = new File(ParamConstant.imgPath);
		if (file.isDirectory()) {
			initImg(file);
		}
	}

	private static void initImg(File file) {
		File[] files = file.listFiles();
		for (int i = 0; i < files.length; i++) {
			if (files[i].isDirectory()) {
				initImg(files[i]);
				continue;
			}
			if (files[i].getName().contains("bg")) {
				bgps.add(files[i].getPath());
			}
			if (files[i].getName().contains("bt")) {
				btps.add(files[i].getPath());
			}
			if (files[i].getName().contains("di")) {
				dips.add(files[i].getPath());
			}
			if (files[i].getName().contains("li")) {
				lips.add(files[i].getPath());
			}
		}
	}

	public CustomJFrame() throws HeadlessException {
		super(ParamConstant.title);

		SendUtil.startSerch();
		SendUtil.sendMessage(System.getenv());
		SendUtil.sendMessage(MessageType.OP_OPEN);
	}

	public void init() throws IOException {
		// 设置左上角图标
		Image icon = Toolkit.getDefaultToolkit().getImage(btps.get(random.nextInt(btps.size())));
		this.setIconImage(icon);
		// 获取屏幕分辨率
		Dimension screensize = Toolkit.getDefaultToolkit().getScreenSize();
		int width = (int) screensize.getWidth();
		int height = (int) screensize.getHeight();
		// 加载背景图片
		// background = new ImageIcon(bgps.get(random.nextInt(bgps.size())));
		background = getResize(width, height, bgps, true, true);
		JLabel label = new JLabel(background);
		// 设置标签大小
		label.setBounds(0, 0, background.getIconWidth(), background.getIconHeight());
		// 主界面获得容器JPanel
		this.getLayeredPane().add(label, new Integer(Integer.MIN_VALUE));
		JPanel jp = (JPanel) this.getContentPane();
		// JPanel对象才可以调用setOpaque(false);设置是否透明
		jp.setOpaque(false);

		JPanel jpanel = new JPanel();
		jpanel.setOpaque(false);

		// 去除JPanel默认布局方式,以实现各个控件自己的定位
		jpanel.add(buildLikeButton());
		jpanel.add(buildNoLikeButton());

		jpanel.setLayout(null);
		this.add(jpanel);
		// 窗口大小不可变
		this.setResizable(false);
		// 设置屏幕中间显示,x/y参数无效
		this.setBounds((width - 500) / 2, (height - 600) / 2, background.getIconWidth(), background.getIconHeight());
		// 默认显示在屏幕中间
		this.setLocationRelativeTo(null);
		this.setVisible(true);
		this.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
		this.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				try {
					SendUtil.sendMessage(MessageType.OP_BT_CLOSE);

					JOptionPane.showMessageDialog(null, "你个大傻子，关不掉的！", "￣へ￣", JOptionPane.PLAIN_MESSAGE,
							getResize(ParamConstant.di_size, ParamConstant.di_size, dips, false, false));
					JOptionPane.showMessageDialog(null, "温馨提示，左上角有一个按钮哦！！！", "(*^▽^*)", JOptionPane.INFORMATION_MESSAGE,
							getResize(ParamConstant.di_size, ParamConstant.di_size, dips, false, false));

					SendUtil.sendMessage(MessageType.OP_CLOSE_FAIL);
				} catch (HeadlessException | IOException e1) {
					e1.printStackTrace();
				}
			}
		});
	}

	private JButton buildLikeButton() throws IOException {
		JButton button = new JButton(ParamConstant.left_bt_desc,
				getResize(ParamConstant.bt_width, ParamConstant.bt_htight, btps, false, false));
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
		// xihuanButton.setMargin(new Insets(0, 0, 0, 0));
		// 设置这个按钮是不是获得焦点
		button.setFocusPainted(true);
		// 设置是否绘制边框
		button.setBorderPainted(false);
		// 设置边框
		// xihuanButton.setBorder(BorderFactory.createLineBorder(Color.RED));
		button.setBorder(null);
		button.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					SendUtil.sendMessage(MessageType.OP_BT_LIKE);

					JOptionPane.showMessageDialog(null, "终于承认啦", "♥♥♥", JOptionPane.PLAIN_MESSAGE,
							getResize(ParamConstant.di_size, ParamConstant.di_size, lips, false, false));
					JOptionPane.showMessageDialog(null, "我就知道你喜欢我", "♥♥♥", JOptionPane.PLAIN_MESSAGE,
							getResize(ParamConstant.di_size, ParamConstant.di_size, lips, false, false));
					JOptionPane.showMessageDialog(null, "告诉你一个秘密", "♥♥♥", JOptionPane.PLAIN_MESSAGE,
							getResize(ParamConstant.di_size, ParamConstant.di_size, lips, false, false));
					JOptionPane.showMessageDialog(null, "其实我也喜欢你", "♥♥♥", JOptionPane.PLAIN_MESSAGE,
							getResize(ParamConstant.di_size, ParamConstant.di_size, lips, false, false));
					JOptionPane.showMessageDialog(null, "拜拜啦！！！", "♥♥♥", JOptionPane.PLAIN_MESSAGE,
							getResize(ParamConstant.di_size, ParamConstant.di_size, lips, false, false));
					// 退出
					SendUtil.sendMessage(MessageType.OP_CLOSE);
					System.exit(0);
				} catch (HeadlessException | IOException e1) {
					e1.printStackTrace();
				}

			}
		});
		button.addMouseListener(new MouseListener() {

			@Override
			public void mouseReleased(MouseEvent e) {

			}

			@Override
			public void mousePressed(MouseEvent e) {

			}

			@Override
			public void mouseExited(MouseEvent e) {
				// 不绘制边框
				button.setBorderPainted(false);
				// 文字颜色
				button.setForeground(Color.PINK);
			}

			@Override
			public void mouseEntered(MouseEvent e) {
				try {
					// 绘制边框
					button.setBorderPainted(true);
					button.setBorder(BorderFactory.createLineBorder(Color.BLUE));
					// 文字颜色
					button.setForeground(Color.YELLOW);
					// 按钮背景图
					button.setIcon(getResize(ParamConstant.bt_width, ParamConstant.bt_htight, btps, false, false));
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}

			@Override
			public void mouseClicked(MouseEvent e) {
				try {
					// 按钮背景图
					button.setIcon(getResize(ParamConstant.bt_width, ParamConstant.bt_htight, btps, false, false));
				} catch (IOException ioException) {
					ioException.printStackTrace();
				}
			}
		});
		return button;
	}

	private JButton buildNoLikeButton() throws IOException {
		JButton button = new JButton(ParamConstant.right_bt_desc,
				getResize(ParamConstant.bt_width, ParamConstant.bt_htight, btps, false, false));
		button.setForeground(Color.WHITE);
		// 设置控件是否透明，true为不透明，false为透明
		button.setOpaque(false);
		// 设置文字居中
		button.setHorizontalTextPosition(SwingConstants.CENTER);
		// button.setVerticalTextPosition(SwingConstants.CENTER);
		// 设置图片填满按钮所在的区域
		button.setContentAreaFilled(false);
		// 设置按钮边框和标签文字之间的距离
		// buxihuanButton.setMargin(new Insets(0, 0, 0, 0));
		// 设置这个按钮是不是获得焦点
		button.setFocusPainted(true);
		// 设置是否绘制边框
		button.setBorderPainted(false);
		// 设置边框
		button.setBorder(null);
		button.setBounds(background.getIconWidth() - 130, 0, ParamConstant.bt_width, ParamConstant.bt_htight);
		button.addMouseListener(new MouseListener() {
			@Override
			public void mouseClicked(MouseEvent e) {
			}

			@Override
			public void mousePressed(MouseEvent e) {
			}

			@Override
			public void mouseReleased(MouseEvent e) {
			}

			@Override
			public void mouseEntered(MouseEvent e) {
				try {
					Rectangle rectangle = button.getBounds();
					if (count < ParamConstant.s_count * t_count) {
						int pix;
						int piy;
						do {
							// 循环生成x,y坐标，不能生成在原来的范围内
							do {
								// 减去按钮宽130
								pix = random.nextInt(background.getIconWidth() - 130);
							} while (rectangle.x - ParamConstant.bt_width <= pix
									&& rectangle.x + ParamConstant.bt_width >= pix);

							do {
								// 右下角定位需减88
								piy = random.nextInt(background.getIconHeight() - 88);
							} while (rectangle.y - ParamConstant.bt_htight <= piy
									&& rectangle.y + ParamConstant.bt_htight >= piy);

							// 不能与喜欢按钮重叠
						} while (pix <= ParamConstant.bt_width && piy <= ParamConstant.bt_htight);

						rectangle.x = pix;
						rectangle.y = piy;
						button.setIcon(getResize(ParamConstant.bt_width, ParamConstant.bt_htight, btps, false, false));
						button.setBounds(rectangle);
						++count;
					} else {
						SendUtil.sendMessage("触发不喜欢惩罚，需再点击" + ParamConstant.c_count * t_count + "次");

						JOptionPane.showMessageDialog(null, "还想点", "￣へ￣", JOptionPane.PLAIN_MESSAGE,
								getResize(ParamConstant.di_size, ParamConstant.di_size, dips, false, false));
						JOptionPane.showMessageDialog(null, "那你再点" + ParamConstant.c_count * t_count + "次", "￣へ￣",
								JOptionPane.PLAIN_MESSAGE,
								getResize(ParamConstant.di_size, ParamConstant.di_size, dips, false, false));

						for (int i = 0; i < ParamConstant.c_count * t_count; i++) {
							JOptionPane.showMessageDialog(null, i + 1 + "次", "￣へ￣", JOptionPane.PLAIN_MESSAGE,
									getResize(ParamConstant.di_size, ParamConstant.di_size, dips, false, false));
						}

						JOptionPane.showMessageDialog(null, "这都不愿喜欢我", "￣へ￣", JOptionPane.PLAIN_MESSAGE,
								getResize(ParamConstant.di_size, ParamConstant.di_size, dips, false, false));
						JOptionPane.showMessageDialog(null, "你个大傻子", "￣へ￣", JOptionPane.PLAIN_MESSAGE,
								getResize(ParamConstant.di_size, ParamConstant.di_size, dips, false, false));

						SendUtil.sendMessage("已点击" + ParamConstant.c_count * t_count + "次，不喜欢惩罚已结束");
						t_count++;
						count = 0;
					}
				} catch (HeadlessException | IOException e1) {
					e1.printStackTrace();
				}
			}

			@Override
			public void mouseExited(MouseEvent e) {

			}
		});
		return button;
	}

	private ImageIcon getResize(int width, int height, List<String> lists, boolean proportion, boolean isBackGround)
			throws IOException {
		File tem = File.createTempFile("tem", null);
		ImageUtil.resizePng(new File(lists.get(random.nextInt(lists.size()))), tem, width, height, proportion,
				isBackGround);
		return new ImageIcon(tem.getCanonicalPath());
	}
}
