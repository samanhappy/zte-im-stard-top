package com.zte.client.impl;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dd.plist.PropertyListParser;
import com.zte.client.IClientHandle;
import com.zte.databinder.ClientBinder;
import com.zte.im.util.FastDSFUtil;
import com.zte.im.util.SystemConfig;

public class ClientHandleImpl implements IClientHandle {

	public static final Logger logger = LoggerFactory
			.getLogger(ClientHandleImpl.class);

	private static ClientHandleImpl impl;

	private ClientHandleImpl() {
		super();
	}

	public static ClientHandleImpl getInstance() {
		if (impl == null)
			impl = new ClientHandleImpl();
		return impl;
	}

	@Override
	public ClientBinder handleClientPackage(String path, String tmpPath,
			String apktoolPath) {
		ClientBinder cb = null;
		if (path.toLowerCase().endsWith("apk")) {
			try {
				ExecCommands.getInstance().process(path, tmpPath, apktoolPath);
				cb = fetchApkClient(tmpPath);
			} catch (IOException e) {
				logger.error("", e);
			}
		} else if (path.toLowerCase().endsWith("ipa")) {
			cb = fetchIpaClient(path, tmpPath);
		}
		new File(path).delete();
		return cb;
	}

	public synchronized void unzip(String zipFilename, String outputDirectory)
			throws IOException {
		File outFile = new File(outputDirectory);
		if (!outFile.exists()) {
			outFile.mkdirs();
		}
		ZipFile zipFile = new ZipFile(zipFilename);
		Enumeration<?> en = zipFile.entries();
		ZipEntry zipEntry = null;
		while (en.hasMoreElements()) {
			zipEntry = (ZipEntry) en.nextElement();
			if (zipEntry.isDirectory()) {
				String dirName = zipEntry.getName();
				dirName = dirName.substring(0, dirName.length() - 1);
				File f = new File(outFile.getPath() + File.separator + dirName);
				f.mkdirs();
			} else {
				String strFilePath = outFile.getPath() + File.separator
						+ zipEntry.getName();
				File f = new File(strFilePath);
				if (!f.exists()) {
					String[] arrFolderName = zipEntry.getName().split("/");
					String strRealFolder = "";
					for (int i = 0; i < (arrFolderName.length - 1); i++) {
						strRealFolder += arrFolderName[i] + File.separator;
					}
					strRealFolder = outFile.getPath() + File.separator
							+ strRealFolder;
					File tempDir = new File(strRealFolder);
					tempDir.mkdirs();
				}
				f.createNewFile();
				InputStream in = zipFile.getInputStream(zipEntry);
				FileOutputStream out = new FileOutputStream(f);
				try {
					int c;
					byte[] by = new byte[1024];
					while ((c = in.read(by)) != -1) {
						out.write(by, 0, c);
					}
					out.flush();
				} catch (IOException e) {
					throw e;
				} finally {
					out.close();
					in.close();
				}
			}
		}
	}

	private ClientBinder fetchIpaClient(String path, String tmpPath) {
		ClientBinder cb = new ClientBinder();
		try {
			unzip(path, tmpPath);
		} catch (IOException e) {
			logger.error("", e);
		}
		List<String> list = new ArrayList<String>();
		ClientHandleImpl.getInstance().listFiles(new File(tmpPath),
				"Info.plist", list, false);
		if (list.size() <= 0)
			return cb;
		File f = new File(list.get(0));
		File f2 = new File(tmpPath + File.separator + "tmp.plist");
		try {
			PropertyListParser.convertToXml(f, f2);
		} catch (Exception e) {
			logger.error("", e);
		}
		String xmlPath = tmpPath + File.separator + "tmp.plist";
		InputStream in = null;
		try {
			in = new FileInputStream(xmlPath);
		} catch (FileNotFoundException e1) {
			logger.error("", e1);
		}
		Map<String, String> map = new HashMap<String, String>();
		if (in != null) {
			try {
				SAXReader reader = new SAXReader();
				Document doc = reader.read(in);
				@SuppressWarnings("unchecked")
				List<Element> composites = ((Element) doc.getRootElement()
						.elements().get(0)).elements();
				listElement(composites, map);
				cb.setCname(map.get(IPA_NAME));
				cb.setVersion(map.get(IPA_VERSION));

			} catch (Exception e) {
				logger.error("", e);
			} finally {
				if (in != null) {
					try {
						in.close();
					} catch (IOException e) {
						logger.error("", e);
					}
				}
			}
			List<String> iconList = new ArrayList<String>();
			ClientHandleImpl.getInstance().listFiles(new File(tmpPath),
					map.get(IPA_ICON), iconList, true);
			File iconf = new File(iconList.get(0));
			new com.justsy.png.ConvertHandler(iconf);
			String newicon = iconf.getAbsolutePath().substring(0,
					iconf.getAbsolutePath().lastIndexOf("."))
					+ "-new.png";
			String url = getIconUrl(new File(newicon));
			cb.setCiconUrl(url);
			deleteDir(new File(tmpPath));
		}
		return cb;
	}

	private void listFiles(File file, String name, List<String> list,
			boolean isLike) {
		if (file.isDirectory()) {
			String[] children = file.list();
			for (int i = 0; i < children.length; i++) {
				if (new File(file, children[i]).isDirectory())
					listFiles(new File(file, children[i]), name, list, isLike);
				if (children[i].equals(name)) {
					if (new File(file, children[i]).getParent()
							.endsWith(".app"))
						list.add(new File(file, children[i]).getPath());
				}
				if (isLike && children[i].startsWith(name)) {
					list.add(new File(file, children[i]).getPath());
				}
			}
		}
	}

	private ClientBinder fetchApkClient(String tmpPath) {
		ClientBinder cb = new ClientBinder();
		String xmlPath = tmpPath + File.separator + "AndroidManifest.xml";
		InputStream in = null;
		try {
			in = new FileInputStream(xmlPath);
		} catch (FileNotFoundException e1) {
			logger.error("", e1);
		}
		if (in != null) {
			try {
				SAXReader reader = new SAXReader();
				Document doc = reader.read(in);
				Element root = doc.getRootElement();
				cb.setVersion(root.attributeValue("versionName"));
				cb.setVersionCode(root.attributeValue("versionCode"));
				@SuppressWarnings("unchecked")
				List<Element> composites = root.elements();
				for (Element e : composites) {
					if (e.getName().equals("application"))
						cb.setCname(getApkName(tmpPath,
								e.attributeValue("label")));
					if (e.getName().equals("application"))
						cb.setCiconUrl(getApkIcon(tmpPath,
								e.attributeValue("icon")));

				}
			} catch (Exception e) {
				logger.error("", e);
			} finally {
				if (in != null) {
					try {
						in.close();
					} catch (IOException e) {
						logger.error("", e);
					}
				}
			}
		}
		return cb;
	}

	private String getApkIcon(String tmpPath, String attributeValue) {
		String key = attributeValue.split("/")[1];
		String[] childs = new File(tmpPath + File.separator + "res").list();
		String path = null;
		for (int i = 0; i < childs.length; i++) {
			if (new File(tmpPath + File.separator + "res" + File.separator
					+ childs[i]).isDirectory()) {
				String p = tmpPath + File.separator + "res" + File.separator
						+ childs[i] + File.separator + key + ".png";
				if (new File(p).exists()) {
					path = p;
				}
			}

		}
		String url = getIconUrl(new File(path));
		deleteDir(new File(tmpPath));
		return url;
	}

	private static boolean deleteDir(File dir) {
		if (dir.isDirectory()) {
			String[] children = dir.list();
			for (int i = 0; i < children.length; i++) {
				boolean success = deleteDir(new File(dir, children[i]));
				if (!success) {
					return false;
				}
			}
		}
		// 目录此时为空，可以删除
		return dir.delete();
	}

	/**
	 * 
	 * @Title: copyFile
	 * @Description:
	 * @param sourceFile
	 * @param targetFile
	 * @throws IOException
	 * @return void
	 */
	public void copyFile(File sourceFile, File targetFile) throws IOException {
		BufferedInputStream inBuff = null;
		BufferedOutputStream outBuff = null;
		try {
			// 新建文件输入流并对它进行缓冲
			inBuff = new BufferedInputStream(new FileInputStream(sourceFile));

			// 新建文件输出流并对它进行缓冲
			outBuff = new BufferedOutputStream(new FileOutputStream(targetFile));

			// 缓冲数组
			byte[] b = new byte[1024 * 5];
			int len;
			while ((len = inBuff.read(b)) != -1) {
				outBuff.write(b, 0, len);
			}
			// 刷新此缓冲的输出流
			outBuff.flush();
		} finally {
			// 关闭流
			if (inBuff != null)
				inBuff.close();
			if (outBuff != null)
				outBuff.close();
		}
	}

	public String getApkName(String tmpPath, String attributeValue) {
		String path = tmpPath + File.separator + "res" + File.separator
				+ "values" + File.separator + "strings.xml";
		String key = attributeValue.split("/")[1];
		InputStream in = null;
		String apkName = null;
		try {
			in = new FileInputStream(path);
		} catch (FileNotFoundException e1) {
			logger.error("", e1);
		}
		if (in != null) {
			try {
				SAXReader reader = new SAXReader();
				Document doc = reader.read(in);
				Element root = doc.getRootElement();
				@SuppressWarnings("unchecked")
				List<Element> composites = root.elements();
				for (Element e : composites) {
					if (e.attributeValue("name").equals(key))
						apkName = (String) e.getData();

				}
			} catch (Exception e) {
				logger.error("", e);
			} finally {
				if (in != null) {
					try {
						in.close();
					} catch (IOException e) {
						logger.error("", e);
					}
				}
			}
		}
		return apkName;
	}

	public static void main(String[] args) {
		String tmpPath = "c:\\1\\a";
		String apktoolPath = "C:\\apk2java\\apk2android";
		ClientBinder cb = ClientHandleImpl.getInstance().handleClientPackage(
				"c:\\1\\ImClient_BaoLi_Base.apk", tmpPath, apktoolPath);
		logger.info(cb.getCname());
		logger.info(cb.getVersion());
		logger.info(cb.getCiconUrl());
	}

	@SuppressWarnings("unchecked")
	public static void listElement(List<Element> composites,
			Map<String, String> map) {
		for (int i = 0; i < composites.size(); i++) {
			Element e = composites.get(i);
			Element en = null;
			if (i < composites.size() - 1)
				en = composites.get(i + 1);
			if (e.elements().size() == 0
					&& en != null
					&& (en.getName().equalsIgnoreCase("array") || en.elements()
							.size() == 0)) {
				if (e.getName().equalsIgnoreCase("key")
						&& e.getData().toString().equalsIgnoreCase(IPA_NAME))
					map.put(IPA_NAME, en.getData().toString());
				if (e.getName().equalsIgnoreCase("key")
						&& e.getData().toString().equalsIgnoreCase(IPA_VERSION))
					map.put(IPA_VERSION, en.getData().toString());
				if (e.getName().equalsIgnoreCase("key")
						&& e.getData().toString().equalsIgnoreCase(IPA_ICON)) {
					if (en.getName().equals("array")) {
						List<Element> iList = en.elements();
						map.put(IPA_ICON, iList.get(0).getData().toString());
					} else if (en.getName().equalsIgnoreCase("string")) {
						map.put(IPA_ICON, en.getData().toString());
					}
				}
			} else {
				listElement(e.elements(), map);
			}
		}
	}

	private String getIconUrl(File file) {
		String source_dsf_path = "";
		FileInputStream fis = null;
		try {
			fis = new FileInputStream(file);
			source_dsf_path = new FastDSFUtil().saveFile("uploadFile", fis,
					"png");
		} catch (Exception e) {
			logger.error("", e);
		} finally {
			if (fis != null)
				try {
					fis.close();
				} catch (IOException e) {
					logger.error("", e);
				}
		}
		return SystemConfig.fdfs_http_host + source_dsf_path;
	}

	private static final String IPA_NAME = "CFBundleName";
	private static final String IPA_VERSION = "CFBundleShortVersionString";
	private static final String IPA_ICON = "CFBundleIconFiles";

}
