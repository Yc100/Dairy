package com.yc.common.utils;

import com.google.zxing.*;
import com.google.zxing.common.BitMatrix;
import org.springframework.beans.factory.annotation.Value;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Map;

/**
 * 二维码生成工具类
 */
public class QrCodeUtils {

    private static final String CHARSET = "utf-8";
    public static final String filePostfix="png";
    // 二维码尺寸
    private static final int QRCODE_SIZE = 300;

    private static final int BLACK = 0xff000000;
    private static final int WHITE = 0xFFFFFFFF;

    /**
     * 仅生成二维码，返回二维码图片网络地址，可直接扫码访问
     * @param content：二维码内容
     * @return
     */
    public static String createQRCode(String uploadPath, String content){

        String fileName = content + "." +filePostfix;
        String physicalPath = uploadPath + "QRCode" + "/" + fileName;//保存在本地的地址
        File file = new File(physicalPath);
        if (!file.getParentFile().exists()) {
            file.getParentFile().mkdirs();
        }
        encode(content, file, filePostfix, BarcodeFormat.QR_CODE, QRCODE_SIZE, QRCODE_SIZE, null);
        return "/files/QRCode/" + fileName;
    }


    /**
     *  生成QRCode二维码<br>
     *  在编码时需要将com.google.zxing.qrcode.encoder.Encoder.java中的<br>
     *  static final String DEFAULT_BYTE_MODE_ENCODING = "ISO8859-1";<br>
     *  修改为UTF-8，否则中文编译后解析不了<br>
     * @param contents 二维码的内容
     * @param file 二维码保存的路径，如：C://test_QR_CODE.png
     * @param filePostfix 生成二维码图片的格式：png,jpeg,gif等格式
     * @param format qrcode码的生成格式
     * @param width 图片宽度
     * @param height 图片高度
     * @param hints
     */
    public static void encode(String contents, File file,String filePostfix, BarcodeFormat format, int width, int height, Map<EncodeHintType, ?> hints) {
        try {
            BitMatrix bitMatrix = new MultiFormatWriter().encode(contents, format, width, height);
            writeToFile(bitMatrix, filePostfix, file);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 生成二维码图片<br>
     *
     * @param matrix
     * @param format
     *            图片格式
     * @param file
     *            生成二维码图片位置
     * @throws IOException
     */
    public static void writeToFile(BitMatrix matrix, String format, File file) throws IOException {
        BufferedImage image = toBufferedImage(matrix);
        ImageIO.write(image, format, file);
    }

    /**
     * 生成二维码内容<br>
     *
     * @param matrix
     * @return
     */
    public static BufferedImage toBufferedImage(BitMatrix matrix) {
        int width = matrix.getWidth();
        int height = matrix.getHeight();
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                image.setRGB(x, y, matrix.get(x, y) == true ? BLACK : WHITE);
            }
        }
        return image;
    }
}