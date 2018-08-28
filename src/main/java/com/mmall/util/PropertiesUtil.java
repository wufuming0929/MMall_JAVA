package com.mmall.util;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Properties;

/**
 * Created by weimin
 */
public class PropertiesUtil {

    private static Logger logger = LoggerFactory.getLogger(PropertiesUtil.class);

    private static Properties props;

    static {
        String fileName = "mmall.properties";
        props = new Properties();
        try {
            props.load(new InputStreamReader(PropertiesUtil.class.getClassLoader().getResourceAsStream(fileName),"UTF-8"));
        } catch (IOException e) {
            logger.error("配置文件读取异常",e);
        }
    }

    public static String getProperty(String key){
        String value = props.getProperty(key.trim());
        if(StringUtils.isBlank(value)){
            return null;
        }
        return value.trim();
    }

    public static String getProperty(String key,String defaultValue){

        String value = props.getProperty(key.trim());
        if(StringUtils.isBlank(value)){
            value = defaultValue;
        }
        return value.trim();
    }

    public static void main(String[] arges) throws Exception{
       //FTP上传步骤演示
        String ip = "192.168.163.130";//ftp的ip
        int port=21;//ftp的端口
        String username = "ftpuser";//ftp的用户名
        String password = "123456";//ftp的密码
        String remotePath = "william/mmall/img/";//存放文件的ftp目录,如果不设置会默认传到根目录
        File file = new File("D:\\download\\book.png");//待传输的文件
        FTPClient ftpClient = new FTPClient();//ftp客户端
        //1.建立连接
        ftpClient.connect(ip,port);
        //2.登陆
        ftpClient.login(username, password);
        //3.切换目录，不设置则默认把文件传到根目录
        boolean workingDirectory = ftpClient.changeWorkingDirectory(remotePath);
        //4.设置缓存大小
        ftpClient.setBufferSize(1024);
        //5.设置二进制文件类型
        ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
        //6.设置传输模式
        ftpClient.enterLocalPassiveMode();
        FileInputStream fis = new FileInputStream(file);
        //7.传输文件---第一个参数为文件名，第二个参数为该文件的输出流
        ftpClient.storeFile(file.getName(), new FileInputStream(file));
        //8.关闭资源
        fis.close();
        ftpClient.disconnect();
        System.out.println("ftp传输成功!");



















    }



}
