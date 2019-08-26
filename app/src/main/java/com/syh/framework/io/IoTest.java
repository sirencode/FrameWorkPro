package com.syh.framework.io;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Reader;

public class IoTest {
    public static void main(String[] args) {

    }

    private void readFileByFileStream(String path) {
        try {
            InputStream inputStream = new FileInputStream(new File(path));
            InputStream inputStream1 = new FileInputStream(path);
            int i = 0;
            while ((i = inputStream.read()) != -1) {
                System.out.println((char) i + "");
            }
            inputStream.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void readFileByBufferStream(String path) {
        try {
            InputStream inputStream = new FileInputStream(new File(path));
            BufferedInputStream bufferedInputStream = new BufferedInputStream(inputStream);
            int len = 0;
            byte[] bs = new byte[1024];
            while ((len = bufferedInputStream.read(bs)) != -1) {
                System.out.println(new String(bs, 0, len));
            }
            bufferedInputStream.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void writeFileByFileStream(String path) {
        try {
            OutputStream outputStream = new FileOutputStream(new File(path));
            FileOutputStream outputStream1 = new FileOutputStream(path);
            outputStream.write("qweww".getBytes());
            outputStream.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void writeFileByBufferStream(String path) {
        try {
            OutputStream outputStream = new FileOutputStream(new File(path));
            BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(outputStream);
            bufferedOutputStream.write("qweww".getBytes());
            bufferedOutputStream.flush();
            bufferedOutputStream.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void readFileByStreamReader(String path) {
        try {
            FileInputStream inputStream = new FileInputStream(path);
            InputStreamReader reader = new InputStreamReader(inputStream);
            int len;
            while ((len = reader.read()) != -1) {
                System.out.print((char) len);
            }
            reader.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void readFileByBufferReader(String path) {
        try {
            FileInputStream inputStream = new FileInputStream(path);
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
            String str;
            while ((str = reader.readLine()) != null) {
                System.out.println(str);// 爱生活，爱Android
            }
            reader.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void writeFileByStreamWriter(String path) {
        try {
            OutputStream outputStream = new FileOutputStream(new File(path));
            OutputStreamWriter writer = new OutputStreamWriter(outputStream);
            writer.write("hello,world");
            writer.flush();
            writer.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void writeFileByBufferWriter(String path) {
        try {
            OutputStream outputStream = new FileOutputStream(new File(path));
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(outputStream);
            BufferedWriter writer = new BufferedWriter(outputStreamWriter);
            writer.write("hello,world");
            writer.flush();
            writer.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
