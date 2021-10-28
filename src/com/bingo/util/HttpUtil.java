package com.bingo.util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public class HttpUtil {

    private static final String BOUNDARY = "20210429";

    private static final String NEW_LINE = "\r\n";

    private static final String BOUNDARY_PREFIX = "--";

    private static final String PARAM_NAME = "param";

    // 连接超时时间 ms
    private static final int CONNECT_TIME_OUT = 3000;

    // 读取超时时间 ms
    private static final int READ_TIME_OUT = 10000;

    public static String doPostAndFile1(String URL, String body, File file) {
        StringBuilder result = new StringBuilder();
        HttpURLConnection conn = null;
        OutputStream os = null;
        BufferedOutputStream bos = null;
        BufferedReader br = null;
        try {
            URL url = new URL(URL);
            conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            // 发送POST请求必须设置为true
            conn.setDoOutput(true);
            conn.setDoInput(true);
            conn.setUseCaches(false);
            // 设置连接超时时间和读取超时时间
            conn.setConnectTimeout(CONNECT_TIME_OUT);
            conn.setReadTimeout(READ_TIME_OUT);

            conn.setRequestProperty("Charset", "UTF-8");
            conn.setRequestProperty("Connection", "Keep-Alive");
            conn.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows; U; Windows NT 6.1; zh-CN; rv:1.9.2.6)");
            conn.setRequestProperty("Content-Type", "multipart/form-data; boundary=" + BOUNDARY);
            conn.connect();

            os = conn.getOutputStream();
            bos = new BufferedOutputStream(os);

            // 1. 处理普通表单域(即形如key = value对)的POST请求（这里也可以循环处理多个字段，或直接给json）
            // 这里看过其他的资料，都没有尝试成功是因为下面多给了个Content-Type
            // form-data 这个是form上传 可以模拟任何类型
            StringBuffer sb = new StringBuffer();
            sb.append(BOUNDARY_PREFIX + BOUNDARY);
            sb.append(NEW_LINE);
            sb.append("Content-Disposition: form-data; name=\"" + PARAM_NAME + "\"");
            sb.append(NEW_LINE);
            sb.append(NEW_LINE);
            sb.append(body);
            sb.append(NEW_LINE);
            sb.append(BOUNDARY_PREFIX + BOUNDARY);
            sb.append(NEW_LINE);
            sb.append("Content-Disposition: form-data; name=\"file\"; filename=\"" + file.getName() + "\"");
            sb.append(NEW_LINE);
            sb.append("Content-Type: multipart/form-data");
            sb.append(NEW_LINE);
            sb.append(NEW_LINE);

            bos.write(sb.toString().getBytes(StandardCharsets.UTF_8));

            // 开始真正向服务器写文件
            DataInputStream dis = new DataInputStream(new FileInputStream(file));
            int bytes = 0;
            byte[] bufferOut = new byte[(int) file.length()];
            bytes = dis.read(bufferOut);
            bos.write(bufferOut, 0, bytes);
            dis.close();
            bos.write((NEW_LINE + BOUNDARY_PREFIX + BOUNDARY + BOUNDARY_PREFIX + NEW_LINE).getBytes());
            bos.flush();
            bos.close();
            os.close();

            if (200 == conn.getResponseCode()) {
                br = new BufferedReader(new InputStreamReader(conn.getInputStream(), StandardCharsets.UTF_8));
                String line;
                while ((line = br.readLine()) != null) {
                    result.append(line);
                }
            } else {
                System.out.println("ResponseCode is an error code:" + conn.getResponseCode());
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (bos != null) {
                    bos.close();
                }
                if (os != null) {
                    os.close();
                }
                if (br != null) {
                    br.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            if (conn != null) {
                conn.disconnect();
            }
        }
        return result.toString();
    }

    public static String doPostAndFile2(String URL, String body, File file) {
        StringBuilder result = new StringBuilder();
        HttpURLConnection conn = null;
        OutputStream os = null;
        BufferedOutputStream bos = null;
        FileInputStream fis = null;
        BufferedInputStream bis = null;
        BufferedReader br = null;
        try {
            URL url = new URL(URL);
            conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            // 发送POST请求必须设置为true
            conn.setDoOutput(true);
            conn.setDoInput(true);
            conn.setUseCaches(false);
            // 设置连接超时时间和读取超时时间
            conn.setConnectTimeout(CONNECT_TIME_OUT);
            conn.setReadTimeout(READ_TIME_OUT);

            conn.setRequestProperty("Charset", "UTF-8");
            conn.setRequestProperty("Connection", "Keep-Alive");
            conn.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows; U; Windows NT 6.1; zh-CN; rv:1.9.2.6)");
            conn.setRequestProperty("Content-Type", "multipart/form-data; boundary=" + BOUNDARY);
            conn.connect();
            os = conn.getOutputStream();
            bos = new BufferedOutputStream(os);

            // 替换文件名
            String fileName = file.getAbsolutePath().replace("\\", "-").replace(":", "!");

            StringBuffer sb = new StringBuffer();
            sb.append(BOUNDARY_PREFIX + BOUNDARY);
            sb.append(NEW_LINE);
            sb.append("Content-Disposition: form-data; name=\"" + PARAM_NAME + "\"");
            sb.append(NEW_LINE);
            sb.append(NEW_LINE);
            sb.append(body);
            sb.append(NEW_LINE);
            sb.append(BOUNDARY_PREFIX + BOUNDARY);
            sb.append(NEW_LINE);
            sb.append("Content-Disposition: form-data; name=\"file\"; filename=\"" + fileName + "\"");
            sb.append(NEW_LINE);
            sb.append("Content-Type: multipart/form-data");
            // sb.append("Content-Type: image/jpeg");
            sb.append(NEW_LINE);
            sb.append(NEW_LINE);

            bos.write(sb.toString().getBytes(StandardCharsets.UTF_8));

            // 开始写出文件的二进制数据
            fis = new FileInputStream(file);
            bis = new BufferedInputStream(fis);
            byte[] buffer = new byte[4096];
            int bytes = bis.read(buffer, 0, buffer.length);
            while (bytes != -1) {
                bos.write(buffer, 0, bytes);
                bytes = bis.read(buffer, 0, buffer.length);
            }
            bis.close();
            fis.close();
            bos.write((NEW_LINE + BOUNDARY_PREFIX + BOUNDARY + BOUNDARY_PREFIX + NEW_LINE).getBytes());
            bos.flush();
            bos.close();
            os.close();

            if (200 == conn.getResponseCode()) {
                br = new BufferedReader(new InputStreamReader(conn.getInputStream(), StandardCharsets.UTF_8));
                String line;
                while ((line = br.readLine()) != null) {
                    result.append(line);
                }
            } else {
                System.out.println("ResponseCode is an error code:" + conn.getResponseCode());
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (bis != null) {
                    bis.close();
                }
                if (fis != null) {
                    fis.close();
                }
                if (bos != null) {
                    bos.close();
                }
                if (os != null) {
                    os.close();
                }
                if (br != null) {
                    br.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            conn.disconnect();
        }
        return result.toString();
    }

    public static String doPost(String URL, String body) {
        OutputStreamWriter out = null;
        BufferedReader in = null;
        StringBuilder result = new StringBuilder();
        HttpURLConnection conn = null;
        try {
            URL url = new URL(URL);
            conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            // 发送POST请求必须设置为true
            conn.setDoOutput(true);
            conn.setDoInput(true);
            conn.setUseCaches(false);
            // 设置连接超时时间和读取超时时间
            conn.setConnectTimeout(CONNECT_TIME_OUT);
            conn.setReadTimeout(READ_TIME_OUT);
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setRequestProperty("Accept", "application/json");
            conn.connect();
            // 获取输出流
            out = new OutputStreamWriter(conn.getOutputStream());
            // 向post请求发送json数据
            out.write(body);
            out.flush();
            // 取得输入流，并使用Reader读取
            if (200 == conn.getResponseCode()) {
                in = new BufferedReader(new InputStreamReader(conn.getInputStream(), StandardCharsets.UTF_8));
                String line;
                while ((line = in.readLine()) != null) {
                    result.append(line);
                }
            } else {
                System.out.println("ResponseCode is an error code:" + conn.getResponseCode());
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (out != null) {
                    out.close();
                }
                if (in != null) {
                    in.close();
                }
            } catch (IOException ioe) {
                ioe.printStackTrace();
            }
            if (conn != null) {
                conn.disconnect();
            }
        }
        return result.toString();
    }
}
