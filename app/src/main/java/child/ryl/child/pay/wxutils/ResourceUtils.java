package child.ryl.child.pay.wxutils;


import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 *
 */
public class ResourceUtils {
    /**
     * 随机生成字符串
     *
     * @param length 字符串的长度
     * @return 随机字符串
     */
    public static String createRandomString(int length) {
        String source = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
        Random random = new Random();
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < length; i++) {
            int position = random.nextInt(source.length());
            builder.append(source.charAt(position));
        }
        return builder.toString();
    }

    public static String generateOutTradeNo(int n) {
        StringBuilder builder = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < n; i++) {
            builder.append(random.nextInt(10));
        }
        return builder.toString();
    }

    /**
     * @param xml
     * @return Map
     * @description 将xml字符串转换成map
     */
    public static Map<String, String> readStringXmlOut(String xml) {
        Map<String, String> map = new HashMap<String, String>();
        Document doc = null;
        try {
            doc = DocumentHelper.parseText(xml);// 将字符串转为XML
            Element rootElt = doc.getRootElement();// 获取根节点
            List<Element> list = rootElt.elements();//获取根节点下所有节点
            for (Element element : list) { //遍历节点
                map.put(element.getName(), element.getText());//节点的name为map的key，text为map的value
            }
        } catch (DocumentException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return map;
    }
}