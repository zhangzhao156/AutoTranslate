import com.alibaba.fastjson.JSON;
import com.baidu.translate.demo.Result;
import com.baidu.translate.demo.TransApi;
import com.baidu.translate.demo.Translate;
import com.baidu.translate.demo.AppLog;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;

import java.io.*;
import java.util.Iterator;

public class Main {

    // 在平台申请的APP_ID 详见 http://api.fanyi.baidu.com/api/trans/product/desktop?req=developer
    private static final String APP_ID = "20161108000031526";
    private static final String SECURITY_KEY = "wCrg73KEy38bqx9bjxjz";
    private static final String baseDir = "/Users/ABC/Documents/fanyi";
    private static TransApi api = new TransApi(APP_ID, SECURITY_KEY);

    public static void main(String[] args) {
        File root = new File(baseDir);
        String[] names = root.list();
        for (String item : names) {
            File child = new File(root, item);
            if (!child.isDirectory()) continue;
            String[] xmls = child.list();
            for (String xml : xmls) {
                File xmlFile = new File(child, xml);
                SAXReader reader = new SAXReader();
                try {
                    Document document = reader.read(xmlFile);
                    Element rootElement = document.getRootElement();
                    translate(xmlFile, rootElement, getTranslate(item));
                    AppLog.d("文件" + child.getPath() + "翻译完成");
                    savedocument(document, xmlFile);
                } catch (DocumentException e) {
                    e.printStackTrace();
                }
            }
        }

//        String result = api.getTransResult("你好", "zh", "kor");
//        if (result != null) {
//            Result trans = new Result(JSON.parseObject(result));
//            if (!trans.trans_result.isEmpty()) {
//                Translate translate = trans.trans_result.get(0);
//                AppLog.d(translate.src + "-->" + translate.dst);
//            }
//        }
    }

    //翻译xml整个文件
    public static void translate(File file, Element rootElement, String to) {
        Iterator<Element> iterator = rootElement.elementIterator();
        //遍历节点
        while (iterator.hasNext()) {
            Element element = iterator.next();
            String text = element.getText();
            //翻译
            String result = api.getTransResult(text, "zh", to);
            if (result != null) {
                Result trans = new Result(JSON.parseObject(result));
                if (!trans.trans_result.isEmpty()) {
                    Translate translate = trans.trans_result.get(0);
                    AppLog.d(translate.src + "-->" + translate.dst);
                    element.setText(translate.dst);
                }
            }
        }
    }

    //将翻译完成的文本保存回原文件,全量覆盖
    private static void savedocument(Document document, File xmlFile) {
        // 紧凑的格式
        // OutputFormat format = OutputFormat.createCompactFormat();
        // 排版缩进的格式
        OutputFormat format = OutputFormat.createPrettyPrint();
        // 设置编码
        format.setEncoding("UTF-8");
        // 创建XMLWriter对象,指定了写出文件及编码格式
        // XMLWriter writer = new XMLWriter(new FileWriter(new
        // File("src//a.xml")),format);
        XMLWriter writer = null;
        try {
            writer = new XMLWriter(new OutputStreamWriter(
                    new FileOutputStream(xmlFile), "UTF-8"), format);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } finally {
            if (writer != null) {
                try {
                    // 写入
                    writer.write(document);
                    // 立即写入
                    writer.flush();
                    // 关闭操作
                    writer.close();
                } catch (Exception e) {

                }
            }
        }
    }

    //根据文件路径判断翻译语言类型
    public static String getTranslate(String name) {
        if (name.contains("-ar-")) {
            return "ara";
        } else if (name.contains("-fr-")) {
            return "fra";
        } else if (name.contains("-de-")) {
            return "de";
        } else if (name.contains("-es-")) {
            return "spa";
        } else if (name.contains("-it-")) {
            return "it";
        } else if (name.contains("-ja-")) {
            return "jp";//日语
        } else if (name.contains("-ko-")) {
            return "kor";//韩语
        } else if (name.contains("-ru-")) {
            return "ru";//俄语
        }
        return "en";
    }

}
