package com.zte.util.system;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.imageio.ImageIO;

import org.apache.commons.fileupload.FileItem;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;

import com.zte.im.protocol.ResponseMain;
import com.zte.im.scale.ScaleByIm4java;
import com.zte.im.util.FastDSFUtil;
import com.zte.im.util.PathUtil;
import com.zte.im.util.SystemConfig;

public class FileSaver {

	static FastDSFUtil fastDSFUtil = new FastDSFUtil();

	private static Logger logger = LoggerFactory.getLogger(FileSaver.class);

	private static Set<String> imgFileFormats = new HashSet<String>();

	private static String _KEY = "uploadFile";

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

	/**
	 * 保存文件
	 * 
	 * @param stream
	 * @param path
	 * @param filename
	 * @throws IOException
	 */
	public static void SaveFileFromInputStream(InputStream stream,
			String filename) throws IOException {
		FileOutputStream fs = new FileOutputStream(filename);
		byte[] buffer = new byte[1024 * 1024];
		int byteread = 0;
		while ((byteread = stream.read(buffer)) != -1) {
			fs.write(buffer, 0, byteread);
			fs.flush();
		}
		fs.close();
		stream.close();
	}
	
	
	public static String save(String content, String fileName) {
		if (content != null) {
			String type = null;
			String name = null;
			if (null != fileName && !"".equals(fileName)) {
				if (fileName.indexOf(".") > -1) {
					int spilitIndex = fileName.lastIndexOf(".");
					name = fileName.substring(0, spilitIndex);
					type = fileName.substring(spilitIndex + 1);
				}
				fileName = name.hashCode() + "" + new Date().getTime() + "."
						+ type;
				if (type != null) {
					String source_path = PathUtil.getPath(0) + fileName;
					try {
						SaveFileFromInputStream(new ByteArrayInputStream(content.getBytes("utf-8")),
								source_path);
					} catch (IOException e) {
						logger.error("", e);
						return null;
					}
					String source_dsf_path = null;
					try {
						source_dsf_path = fastDSFUtil.saveFile(_KEY,
								new ByteArrayInputStream(content.getBytes("utf-8")), type);
					} catch (IOException e) {
						logger.error("", e);
					}
					if (source_dsf_path != null) {
						logger.info("save file OK:"
								+ SystemConfig.fdfs_http_host + source_dsf_path);
						return SystemConfig.fdfs_http_host + source_dsf_path;
					}
				}
			} else {
				logger.info("上传文件为空");
			}
		}
		return null;
	}
	

	public static String save(FileItem file) {
		if (file != null) {
			String type = null;
			String name = null;
			String fileName = file.getName();
			if (null != fileName && !"".equals(fileName)) {
				if (fileName.indexOf(".") > -1) {
					int spilitIndex = fileName.lastIndexOf(".");
					name = fileName.substring(0, spilitIndex);
					type = fileName.substring(spilitIndex + 1);
				}
				fileName = name.hashCode() + "" + new Date().getTime() + "."
						+ type;
				if (type != null) {
					String source_path = PathUtil.getPath(0) + file.getName();
					try {
						SaveFileFromInputStream(file.getInputStream(),
								source_path);
					} catch (IOException e) {
						logger.error("", e);
						return null;
					}
					String source_dsf_path = null;
					try {
						source_dsf_path = fastDSFUtil.saveFile(_KEY,
								file.getInputStream(), type);
					} catch (IOException e) {
						logger.error("", e);
					}
					if (source_dsf_path != null) {
						logger.info("save file OK:"
								+ SystemConfig.fdfs_http_host + source_dsf_path);
						return SystemConfig.fdfs_http_host + source_dsf_path;
					}
				}
			} else {
				logger.info("上传文件为空");
			}
		}
		return null;
	}

	public static String save(MultipartFile photo, ResponseMain main) {
		if (photo != null) {
			String type = null;
			String name = null;
			String fileName = photo.getOriginalFilename();
			if (null != fileName && !"".equals(fileName)) {
				if (fileName.indexOf(".") > -1) {
					int spilitIndex = fileName.lastIndexOf(".");
					name = fileName.substring(0, spilitIndex);
					type = fileName.substring(spilitIndex + 1);
				}
				fileName = name.hashCode() + "" + new Date().getTime() + "."
						+ type;
				if (type != null) {
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
						String mini_path = PathUtil.getPath(1) + "mini_"
								+ fileName;
						logger.info("source_path:" + source_path);
						logger.info("mini_path:" + mini_path);
						try {
							try {
								SaveFileFromInputStream(photo.getInputStream(),
										source_path);
							} catch (IOException e) {
								logger.info(e.getMessage());
								return null;
							}
							BufferedImage bi = ImageIO.read(new File(
									source_path));
							int source_width = bi.getWidth();
							double scale = (double) 80 / (double) source_width;
							ScaleByIm4java scaleByIm4java = new ScaleByIm4java();
							scaleByIm4java.scale(scale, source_path, mini_path);

							String source_dsf_path = fastDSFUtil.saveFile(_KEY,
									new FileInputStream(new File(source_path)),
									type);
							String mini_dsf_path = fastDSFUtil.saveFile(_KEY,
									new FileInputStream(new File(mini_path)),
									type);
							if (source_dsf_path != null
									&& mini_dsf_path != null) {
								main.setFileUrl(SystemConfig.fdfs_http_host
										+ mini_dsf_path);
								main.setSourceFileUrl(SystemConfig.fdfs_http_host
										+ source_dsf_path);
								// 此处只返回缩略图地址
								return SystemConfig.fdfs_http_host
										+ mini_dsf_path;
							}
							logger.info("source_dsf_path:" + source_dsf_path);
							logger.info("mini_dsf_path:" + mini_dsf_path);
							removeFile(source_path);
							removeFile(mini_path);
						} catch (Exception e) {
							logger.error("", e);
							logger.error("error when save file:"
									+ e.getMessage());
						}
					} else {
						if ("apk".equalsIgnoreCase(type)
								|| "ipa".equalsIgnoreCase(type)) {
							String source_path = PathUtil.getPath(0)
									+ photo.getOriginalFilename();
							try {
								SaveFileFromInputStream(photo.getInputStream(),
										source_path);
							} catch (IOException e) {
								logger.error("", e);
								return null;
							}
						}
						String source_dsf_path = null;
						try {
							source_dsf_path =
							// "";
							fastDSFUtil.saveFile(_KEY, photo.getInputStream(),
									type);
						} catch (IOException e) {
							logger.error("", e);
						}
						if (source_dsf_path != null) {
							logger.info("save file OK:"
									+ SystemConfig.fdfs_http_host
									+ source_dsf_path);
							return SystemConfig.fdfs_http_host
									+ source_dsf_path;
						}
					}
				}
			} else {
				logger.info("上传文件为空");
			}
		}
		return null;
	}

	private static void removeFile(String path) {
		File file = new File(path);
		file.delete();
	}
}
