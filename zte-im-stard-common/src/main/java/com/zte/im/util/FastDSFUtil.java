package com.zte.im.util;

import java.io.IOException;
import java.io.InputStream;
import java.net.InetSocketAddress;
import java.net.Socket;

import org.csource.common.MyException;
import org.csource.common.NameValuePair;
import org.csource.fastdfs.ClientGlobal;
import org.csource.fastdfs.ServerInfo;
import org.csource.fastdfs.StorageClient;
import org.csource.fastdfs.StorageServer;
import org.csource.fastdfs.TrackerClient;
import org.csource.fastdfs.TrackerGroup;
import org.csource.fastdfs.TrackerServer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FastDSFUtil {

	public static final Logger logger = LoggerFactory
			.getLogger(FastDSFUtil.class);

	public static String classPath;
	public static String configFilePath;
	static {
		// 连接超时的时限，单位为毫秒
		ClientGlobal.setG_connect_timeout(2000);
		// 网络超时的时限，单位为毫秒
		ClientGlobal.setG_network_timeout(30000);
		// 防盗链Token
		ClientGlobal.setG_anti_steal_token(false);
		// 字符集
		ClientGlobal.setG_charset("UTF-8");
		// 密钥
		ClientGlobal.setG_secret_key(null);
		// HTTP访问服务的端口号
		// ClientGlobal.setG_tracker_http_port();
		// Tracker服务器列表
		String[] servers = SystemConfig.tracker_servers.split(",");
		InetSocketAddress[] tracker_servers = new InetSocketAddress[servers.length];
		for (int i = 0; i < servers.length; i++) {
			String[] strs = servers[i].split(":");
			tracker_servers[i] = new InetSocketAddress(strs[0],
					Integer.valueOf(strs[1]));
		}
		ClientGlobal.setG_tracker_group(new TrackerGroup(tracker_servers));
	}

	/**
	 * 
	 * @param author
	 *            作者名字
	 * @param fis
	 *            文件流
	 * @param suffix
	 *            文件后缀名
	 * @return
	 */
	public String saveFile(String author, InputStream fis, String suffix) {
		String url = null;
		TrackerClient trackerClient = new TrackerClient();
		TrackerServer trackerServer = null;
		try {
			trackerServer = trackerClient.getConnection();
			StorageServer storageServer = null;
			StorageClient storageClient = new StorageClient(trackerServer,
					storageServer);
			NameValuePair[] meta_list = new NameValuePair[1];
			meta_list[0] = new NameValuePair("author", author);
			byte[] file_buff = null;
			int len = 0;
			if (fis != null) {
				len = fis.available();
				file_buff = new byte[len];
				fis.read(file_buff);
			}
			String group_name = null;
			StorageServer[] storageServers = trackerClient.getStoreStorages(
					trackerServer, group_name);
			if (storageServers == null) {
				logger.error("get store storage servers fail, error code: "
						+ storageClient.getErrorCode());
			}
			String[] results = storageClient.upload_file(file_buff, suffix,
					meta_list);
			if (results == null) {
				logger.error("upload file fail, error code: "
						+ storageClient.getErrorCode());
				return null;
			}
			group_name = results[0];
			String remote_filename = results[1];
			logger.info("group_name: " + group_name + ", remote_filename: "
					+ remote_filename);
			url = group_name + "/" + remote_filename;
			ServerInfo[] servers = trackerClient.getFetchStorages(
					trackerServer, group_name, remote_filename);
			if (servers == null) {
				logger.error("get storage servers fail, error code: "
						+ trackerClient.getErrorCode());
			}
		} catch (IOException e) {
			logger.error("", e);
		} catch (MyException e) {
			logger.error("", e);
		} finally {
			try {
				if (trackerServer != null) {
					Socket socket = trackerServer.getSocket();
					if (socket != null && socket.isConnected()) {
						socket.close();
					}
				}
				trackerServer.getSocket().close();
			} catch (IOException e) {
				logger.error("", e);
			}
		}
		return url;
	}

}
