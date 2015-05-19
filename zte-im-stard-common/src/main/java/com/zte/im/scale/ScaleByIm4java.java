package com.zte.im.scale;

import java.awt.image.BufferedImage;
import java.io.File;
import java.util.Properties;

import javax.imageio.ImageIO;

import org.im4java.core.ConvertCmd;
import org.im4java.core.IMOperation;

public class ScaleByIm4java {
	/**
	 * 图片压缩
	 * 
	 * @param ctx
	 */
	public void scale(double scale, String source_path, String target_path)
			throws Exception {
		String imageMagickPath = "";
		if (isWindowsOS()) {
			imageMagickPath = "C:\\Program Files\\ImageMagick-6.8.9-Q16";
		}
		IMOperation op = new IMOperation();// 创建IM操作对象
		op.addImage(source_path);

		// 原始图片的BufferedImage对象
		BufferedImage bi = ImageIO.read(new File(source_path));
		int sourceWidth = bi.getWidth();
		int sourceHeight = bi.getHeight();
		int targetWidth = (int) (sourceWidth * scale);
		int targetHeight = (int) (sourceHeight * scale);
		op.resize(targetWidth, targetHeight);
		op.addImage(target_path);
		/*
		 * 色彩处理 如果是单色，则设置为黑白
		 */

		// 创建ConvertCmd命令行对象
		// IM4JAVA是同时支持ImageMagick和GraphicsMagick的，这里是bool值，如果为true则使用GM，如果为false支持IM。
		ConvertCmd convert = new ConvertCmd(false);
		if (!"".equals(imageMagickPath))
			convert.setSearchPath(imageMagickPath);
		convert.run(op);
	}

	/**
	 * 快讯的图片压缩需求 宽度小于等于设备宽度就不压了
	 * 
	 * @param device_width
	 * @param source_width
	 * @return
	 */
	public double getScaleByWidth(double device_width, double source_width) {
		double scale;
		device_width = device_width - 15;// 留白15
		if (source_width <= device_width) {
			scale = 1.0;
		} else {
			scale = device_width / source_width;
		}
		return scale;
	}

	/**
	 * 描述：图片压缩算法的实现。 根据原图宽高和设备宽高的比例关系确定压缩率。
	 * 
	 * @param w_h
	 *            ，设备宽和高
	 * @param imageWidth
	 *            ，原图宽度
	 * @param imageHeight
	 *            ，原图高度
	 * 
	 * @return scale，压缩率
	 */
	public double getScale(double[] w_h, double imageWidth, double imageHeight) {
		double SCALE_NUM_H = 0.9;
		double SCALE_NUM_L = 0.8;
		double width = w_h[0];
		double scale;

		/*
		 * 如果原图片的宽高比例大于或等于设备的宽高比例 ， 则以宽度为准进行压缩。
		 */
		if (true) {
			if (imageWidth > width) // 原图片的宽度大于设备的宽度,则按照设备宽度压缩
			{
				scale = width / imageWidth;
			} else {
				if (imageWidth > width / 2) // 原图宽度大于设备宽度的一半，则按高压缩率0.8压缩
				{
					scale = SCALE_NUM_L;
				} else
				// 原图宽度小于设备宽度的一半，则按低压缩率0.9压缩
				{
					scale = SCALE_NUM_H;
				}
			}
			if (imageWidth * scale >= (width - 5))// 压缩后图宽大于设备宽，则按设备宽-15修正
			{
				scale = (width - 48) / imageWidth;
			}
		}
		if (scale > 1.0) {
			scale = 1.0;
		}
		return scale;
	}

	/**
	 * 
	 * TODO 判断操作系统类型
	 * 
	 * @return true(windows),false(其他的操作系统)
	 */
	private static boolean isWindowsOS() {
		Properties props = System.getProperties();
		String osName = props.getProperty("os.name");
		osName = osName.toLowerCase();
		if (osName.startsWith("windows")) {
			return true;
		} else {
			return false;
		}
	}

}
