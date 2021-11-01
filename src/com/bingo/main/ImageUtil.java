package com.bingo.main;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.swing.*;

public class ImageUtil {
	
	public static Random random = new Random();

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
	
	public static String getAnyOneImg(List<String> list){
		if (list == null || list.isEmpty()) {
		 	return "";
		}
		return list.get(random.nextInt(list.size()));
	}

	public static ImageIcon getResize(int width, int height, List<String> imgs, boolean proportion,
			boolean isBackGround) throws IOException {
		File tem = File.createTempFile("tem", null);
		ImageUtil.resizePng(new File(getAnyOneImg(imgs)), tem, width, height, proportion, isBackGround);
		return new ImageIcon(tem.getCanonicalPath());
	}
	
	
	/**
	 * 裁剪PNG图片工具类
	 *
	 * @param fromFile     源文件
	 * @param toFile       裁剪后的文件
	 * @param outputWidth  裁剪宽度
	 * @param outputHeight 裁剪高度
	 * @param proportion   是否是等比缩放
	 * @param isBackGround 是否背景图
	 */
	public static void resizePng(File fromFile, File toFile, int outputWidth, int outputHeight, boolean proportion,
			boolean isBackGround) {
		try {
			BufferedImage bi2 = ImageIO.read(fromFile);
			int newWidth;
			int newHeight;
			if (isBackGround) {
				int min_width = ParamConstant.bt_width * 2 + 30;
				double mid_width = ((double) outputWidth) * 0.75;
				double mid_height = ((double) outputHeight) * 0.75;
				double rate1 = ((double) bi2.getWidth()) / mid_width;
				double rate2 = ((double) bi2.getHeight()) / mid_height;
				// 根据缩放比率大的进行缩放控制 > 小 < 大
				double rate = rate1 > rate2 ? rate1 : rate2;
				newWidth = (int) ((bi2.getWidth()) / rate);
				newHeight = (int) ((bi2.getHeight()) / rate);
				if (newWidth < min_width) {
					newWidth = min_width;
					newHeight = bi2.getHeight() * newWidth / bi2.getWidth();
				}
				if (newHeight > outputHeight * 2) {
					newHeight = (outputHeight - 50) * 2;
				}
			} else {
				// 判断是否是等比缩放
				if (proportion) {
					double rate1 = ((double) bi2.getWidth()) / outputWidth;
					double rate2 = ((double) bi2.getHeight()) / outputHeight;
					// 根据缩放比率大的进行缩放控制 > 小 < 大
					double rate = rate1 > rate2 ? rate1 : rate2;
					newWidth = (int) ((bi2.getWidth()) / rate);
					newHeight = (int) ((bi2.getHeight()) / rate);
				} else {
					newWidth = outputWidth; // 输出的图片宽度
					newHeight = outputHeight; // 输出的图片高度
				}
			}
			BufferedImage to = new BufferedImage(newWidth, newHeight, BufferedImage.TYPE_INT_RGB);
			Graphics2D g2d = to.createGraphics();
			to = g2d.getDeviceConfiguration().createCompatibleImage(newWidth, newHeight, Transparency.TRANSLUCENT);
			g2d.dispose();
			g2d = to.createGraphics();
			@SuppressWarnings("static-access")
			Image from = bi2.getScaledInstance(newWidth, newHeight, bi2.SCALE_AREA_AVERAGING);
			g2d.drawImage(from, 0, 0, null);
			g2d.dispose();
			ImageIO.write(to, "png", toFile);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
