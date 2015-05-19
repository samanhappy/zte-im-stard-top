package com.zte.im.util;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.util.HashSet;
import java.util.Set;

import javax.imageio.ImageIO;

import org.apache.commons.fileupload.FileItem;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.zte.im.bean.ImFile;
import com.zte.im.bean.ImImage;
import com.zte.im.scale.ScaleByIm4java;

public class FileSaveHandler {

	private static Logger logger = LoggerFactory
			.getLogger(FileSaveHandler.class);

	private static final String _KEY = "JiangJun";

	private static FastDSFUtil fastDSFUtil = new FastDSFUtil();

	private static Set<String> imgFileFormats = new HashSet<String>();

	static {
		imgFileFormats.add("gif");
		imgFileFormats.add("bmp");
		imgFileFormats.add("dib");
		imgFileFormats.add("jfif");
		imgFileFormats.add("jpe");
		imgFileFormats.add("jpeg");
		imgFileFormats.add("jpg");
		imgFileFormats.add("png");
		imgFileFormats.add("tif");
		imgFileFormats.add("tiff");
		imgFileFormats.add("ico");
	}

	public ImFile save(FileItem item, String fileName, String type) {

		if (imgFileFormats.contains(type)) {
			/**
			 * 图片文件处理：
			 * 
			 * 1.先保存原图到本地；
			 * 
			 * 2.压缩原图到本地；
			 * 
			 * 3.原图和缩略图存储到fastdfs
			 * 
			 * 4.删除本地原图和缩略图
			 */
			String source_path = PathUtil.getPath(0) + fileName;
			String mini_path = PathUtil.getPath(1) + "mini_" + fileName;
			logger.info("source_path:" + source_path);
			logger.info("mini_path:" + mini_path);
			File file = new File(source_path);
			ImImage imImage = null;
			try {
				item.write(file);
				BufferedImage bi = ImageIO.read(new File(source_path));
				int source_width = bi.getWidth();
				double scale = (double) 80 / (double) source_width;
				ScaleByIm4java scaleByIm4java = new ScaleByIm4java();
				scaleByIm4java.scale(scale, source_path, mini_path);

				String source_dsf_path = fastDSFUtil.saveFile(_KEY,
						new FileInputStream(new File(source_path)), type);
				String mini_dsf_path = fastDSFUtil.saveFile(_KEY,
						new FileInputStream(new File(mini_path)), type);
				if (source_dsf_path != null && mini_dsf_path != null) {
					imImage = new ImImage();
					imImage.setImgUrl(source_dsf_path);
					imImage.setMiniImgUrl(mini_dsf_path);
					imImage.setHost(SystemConfig.fdfs_http_host);
				}
				logger.info("source_dsf_path:" + source_dsf_path);
				logger.info("mini_dsf_path:" + mini_dsf_path);
				removeFile(source_path);
				removeFile(mini_path);
			} catch (Exception e) {
				logger.error("", e);
				logger.error("error when save file:" + e.getMessage());
			}
			return imImage;
		} else {
			/**
			 * 其他文件处理：
			 * 
			 * 1.先保存原文件到本地；
			 * 
			 * 2.原文件存储到fastdfs
			 * 
			 * 3.删除本地原文件
			 */
			ImFile imFile = new ImFile();
			String source_path = PathUtil.getPath(0) + fileName;
			logger.info("source_path:" + source_path);
			File file = new File(source_path);
			try {
				item.write(file);
				String source_dsf_path = fastDSFUtil.saveFile(_KEY,
						new FileInputStream(new File(source_path)), type);
				if (source_dsf_path != null) {
					imFile.setFileUrl(source_dsf_path);
					imFile.setHost(SystemConfig.fdfs_http_host);
				}
				logger.info("source_dsf_path:" + source_dsf_path);
				removeFile(source_path);
			} catch (Exception e) {
				logger.error("", e);
				logger.error("error when save file:" + e.getMessage());
			}
			return imFile;
		}
	}

	private void removeFile(String path) {
		File file = new File(path);
		file.delete();
	}
}
