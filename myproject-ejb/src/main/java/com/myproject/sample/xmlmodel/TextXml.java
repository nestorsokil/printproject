package com.myproject.sample.xmlmodel;

import com.myproject.sample.canvas.AbstractCanvas;
import org.faceless.pdf2.PDFPage;
import org.faceless.pdf2.PDFStyle;
import org.faceless.pdf2.StandardFont;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import java.awt.*;
import java.io.File;
import java.io.IOException;


@XmlAccessorType(XmlAccessType.FIELD)
public class TextXml extends AbstractXmlElement{

    @XmlAttribute(name = "value")
    protected String value;

    @XmlAttribute(name = "font-size")
    protected int fontSize;

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public int getFontSize() {
        return fontSize;
    }

    public void setFontSize(int fontSize) {
        this.fontSize = fontSize;
    }

    @Override public void draw(PDFPage page, String projectTempFolderPath) throws IOException{
        PDFStyle normal = new PDFStyle();
        normal.setFont(new StandardFont(StandardFont.TIMES), fontSize);
        normal.setFillColor(Color.black);
        normal.setTextLineSpacing(1.5f);
        page.setStyle(normal);
        page.drawText(value, x, y);
    }

    @Override public void draw(Graphics2D graphics, String projectTempFolderPath) throws IOException{
        graphics.setFont(new Font("TimesRoman", Font.PLAIN, fontSize));
        graphics.drawString(value, x, y);
    }
}
