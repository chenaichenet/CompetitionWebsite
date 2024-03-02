/**
 * FileName: Pdf
 * Author:   嘉平十七
 * Date:     2021/5/3 15:28
 * Description:
 */
package com.hunau.competition.utils;

import com.hunau.competition.domain.Information;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;
import org.springframework.boot.system.ApplicationHome;
import org.springframework.util.ClassUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class Pdf {
    //定义全局的字体静态变量
    private static Font titleFont;
    private static Font headFont;
    private static Font keyFont;
    private static Font textFont;
    //最带宽度
    private static final int maxWidth = 520;
    //水印
    private static String waterCont = "大学生竞赛网";
    BaseFont font;
    {
        try {
            font = BaseFont.createFont("C:\\Windows\\Fonts\\simkai.ttf", BaseFont.IDENTITY_H, BaseFont.NOT_EMBEDDED);
        } catch (DocumentException | IOException e) {
            e.printStackTrace();
        }
    }
    Font waterFont = new Font(font, 30, Font.BOLD, new GrayColor(0.95f));

    static {
        try {
            // 不同字体（这里定义为同一种字体：包含不同字号，不同style）
            BaseFont baseFont = BaseFont.createFont("C:\\Windows\\Fonts\\simkai.ttf",BaseFont.IDENTITY_H,BaseFont.NOT_EMBEDDED);
            titleFont = new Font(baseFont,16,Font.BOLD);
            headFont = new Font(baseFont,14,Font.BOLD);
            keyFont = new Font(baseFont,10,Font.BOLD);
            textFont = new Font(baseFont,10,Font.NORMAL);
        } catch (DocumentException | IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 生成PDF文件
     * @param document
     * @throws Exception
     */
    public void generatePDF(Document document, String title) throws Exception{
        //段落
        Paragraph paragraph = new Paragraph(title,titleFont);
        paragraph.setAlignment(1); //设置文字居中（0，靠左；1，居中；2，靠右）
        paragraph.setIndentationLeft(12); //设置左缩进
        paragraph.setIndentationRight(12); //设置右缩进
        paragraph.setFirstLineIndent(24); //设置首行缩进
        paragraph.setLeading(20f); //行间距
        paragraph.setSpacingBefore(5f); //设置段落上空白
        paragraph.setSpacingAfter(10f); //设置段落下空白

        PdfPTable table = createTable(new float[]{40,120,120,120});
        table.addCell(createCell("序号",keyFont,Element.ALIGN_CENTER));
        table.addCell(createCell("姓名",keyFont,Element.ALIGN_CENTER));
        table.addCell(createCell("学校",keyFont,Element.ALIGN_CENTER));
        table.addCell(createCell("作品名称",keyFont,Element.ALIGN_CENTER));

        document.add(paragraph);
        document.add(table);
    }

    /**
     * 创建指定列宽、列数的表格
     * @param widths
     * @return
     */
    public PdfPTable createTable(float[] widths) {
        PdfPTable table = new PdfPTable(widths);
        try {
            table.setTotalWidth(maxWidth);
            table.setLockedWidth(true);
            table.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.getDefaultCell().setBorder(1);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return table;
    }

    public PdfPCell createCell(String value, Font font) {
        PdfPCell cell = new PdfPCell();
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell.setPhrase(new Phrase(value, font));
        return cell;
    }

    /**
     * 创建单元格（指定字体、水平..）
     * @param value
     * @param font
     * @param align
     * @return
     */
    public PdfPCell createCell(String value, Font font, int align) {
        PdfPCell cell = new PdfPCell();
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        cell.setHorizontalAlignment(align);
        cell.setPhrase(new Phrase(value, font));
        return cell;
    }

    /**
     * 设置水印
     * @param writer 水印
     * @param document document对象
     */
    public void waterMark(PdfWriter writer, Document document){
        for (int i=0; i<5; i++){
            for (int j=0; j<5; j++){
                ColumnText.showTextAligned(writer.getDirectContentUnder(),
                        Element.ALIGN_CENTER,
                        new Phrase(waterCont == null ? "HELLO WORLD" : waterCont, waterFont),
                        (50.5f+i*350),
                        (40.0f+j*150),
                        writer.getPageNumber() % 2 == 1 ? 45 : -45);
            }
        }
    }



    public static void main(String[] args) {
        //项目当前路径
//        String filePath = ClassUtils.getDefaultClassLoader().getResource("").getPath() + "static/";
//        String filePath = ClassUtils.getDefaultClassLoader().getResource("").getPath();
//        String filePath = "/usr/local/bison/product-service/static";
        ApplicationHome home = new ApplicationHome(Pdf.class);
        String filePath = home.getSource().getParentFile().toString() + "/static/";
        System.out.println("filePath" + "==================" + filePath);
        System.err.println("file:"+System.getProperty("user.dir")+"\\src\\main\\resources\\static\\upload\\imgs\\");
    }
}