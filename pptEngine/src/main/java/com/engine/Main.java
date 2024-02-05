package com.engine;

import org.apache.poi.xslf.usermodel.XMLSlideShow;
import org.apache.poi.xslf.usermodel.XSLFSlide;

import java.io.FileOutputStream;
import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        // 创建 PPT 对象
        XMLSlideShow ppt = new XMLSlideShow();

        // 添加一张幻灯片
        XSLFSlide slide = ppt.createSlide();

        // 在幻灯片中添加标题和文本框
        slide.createTextBox().setText("Hello, PowerPoint!");
        slide.createTextBox().setText("This is a sample presentation created with Apache POI.");

        // 保存PPT文件
        try (FileOutputStream out = new FileOutputStream("/workspace/output.pptx")) {
            ppt.write(out);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
