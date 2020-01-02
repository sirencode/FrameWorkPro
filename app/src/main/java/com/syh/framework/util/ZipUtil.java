package com.syh.framework.util;

import android.text.TextUtils;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.Locale;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipInputStream;

/**
 * Created by shenyonghe on 2019-12-31.
 */
public class ZipUtil {

    private static final String TAG = ZipUtil.class.getSimpleName();
    private static final int BUFF_SIZE = 1024;

    /**
     * @param srcPath
     *            待解压的zip文件
     * @param dstPath
     *            zip解压后待存放的目录
     * @return 只要解压过程中发生错误，就立即停止并返回(等价于windows上直接进行解压)
     */
    public static boolean unzipFile(String srcPath, String dstPath) {
        if(TextUtils.isEmpty(srcPath) || TextUtils.isEmpty(dstPath)) {
            return false;
        }
        File srcFile = new File(srcPath);
        if(!srcFile.exists() || !srcFile.getName().toLowerCase(Locale.getDefault()).endsWith("zip")) {
            return false;
        }
        File dstFile = new File(dstPath);
        if(!dstFile.exists() || !dstFile.isDirectory()) {
            dstFile.mkdirs();
        }
        try {
            ZipInputStream zis = new ZipInputStream(new FileInputStream(srcFile));
            BufferedInputStream bis = new BufferedInputStream(zis);
            ZipEntry zipEntry = null;
            byte[] buffer = new byte[BUFF_SIZE];
            if(!dstPath.endsWith(File.separator)) {
                dstPath += File.separator;
            }
            while((zipEntry = zis.getNextEntry()) != null) {
                String fileName = dstPath + zipEntry.getName();
                File file = new File(fileName);
                File parentDir = file.getParentFile();
                if(!parentDir.exists()) {
                    parentDir.mkdirs();
                }
                FileOutputStream fos = new FileOutputStream(file);
                while(bis.read(buffer) != -1) {
                    fos.write(buffer);
                }
                fos.close();
            }
            bis.close();
            zis.close();
            return true;
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return false;
    }

    public static void unzip(String zipFileName, String outputDirectory)
            throws IOException {
        ZipFile zipFile = null;
        try {
            zipFile = new ZipFile(zipFileName);
            Enumeration e = zipFile.entries();
            ZipEntry zipEntry = null;
            File dest = new File(outputDirectory);
            dest.mkdirs();
            while (e.hasMoreElements()) {
                zipEntry = (ZipEntry) e.nextElement();
                String entryName = zipEntry.getName();
                InputStream in = null;
                FileOutputStream out = null;
                try {
                    if (zipEntry.isDirectory()) {
                        String name = zipEntry.getName();
                        name = name.substring(0, name.length() - 1);
                        File f = new File(outputDirectory + File.separator
                                + name);
                        f.mkdirs();
                    } else {
                        int index = entryName.lastIndexOf("\\");
                        if (index != -1) {
                            File df = new File(outputDirectory + File.separator
                                    + entryName.substring(0, index));
                            df.mkdirs();
                        }
                        index = entryName.lastIndexOf("/");
                        if (index != -1) {
                            File df = new File(outputDirectory + File.separator
                                    + entryName.substring(0, index));
                            df.mkdirs();
                        }
                        File f = new File(outputDirectory + File.separator
                                + zipEntry.getName());
                        // f.createNewFile();
                        in = zipFile.getInputStream(zipEntry);
                        out = new FileOutputStream(f);
                        int c;
                        byte[] by = new byte[1024];
                        while ((c = in.read(by)) != -1) {
                            out.write(by, 0, c);
                        }
                        out.flush();
                    }
                } catch (IOException ex) {
                    ex.printStackTrace();
                    throw new IOException("解压失败：" + ex.toString());
                } finally {
                    if (in != null) {
                        try {
                            in.close();
                        } catch (IOException ex) {
                        }
                    }
                    if (out != null) {
                        try {
                            out.close();
                        } catch (IOException ex) {
                        }
                    }
                }
            }
        } catch (IOException ex) {
            ex.printStackTrace();
            throw new IOException("解压失败：" + ex.toString());
        } finally {
            if (zipFile != null) {
                try {
                    zipFile.close();
                } catch (IOException ex) {
                }
            }
        }
    }

}
