package com.yc.common.utils;

import java.util.*;

public class XmlCommonUtil{

    public static byte[] callMapToXML(Map map) {
        System.out.println("将Map转成Xml, Map：" + map.toString());
        StringBuffer sb = new StringBuffer();
        sb.append("<request>");
        mapToXMLTest2(map, sb);
        sb.append("</request>");
        System.out.println("将Map转成Xml, Xml：" + sb.toString());
        try {
            return sb.toString().getBytes("UTF-8");
        } catch (Exception e) {
            System.err.println(e);
        }
        return null;
    }

    private static void mapToXMLTest2(Map map, StringBuffer sb) {
        Set set = map.keySet();
        for (Iterator it = set.iterator(); it.hasNext();) {
            String key = (String) it.next();
            Object value = map.get(key);
            if (null == value)
                value = "";
            if (value.getClass().getName().equals("java.util.ArrayList")) {
                ArrayList list = (ArrayList) map.get(key);
                sb.append("<" + key + ">");
                for (int i = 0; i < list.size(); i++) {
                    HashMap hm = (HashMap) list.get(i);
                    mapToXMLTest2(hm, sb);
                }
                sb.append("</" + key + ">");

            } else {
                if (value instanceof HashMap) {
                    sb.append("<" + key + ">");
                    mapToXMLTest2((HashMap) value, sb);
                    sb.append("</" + key + ">");
                } else {
                    sb.append("<" + key + ">" + value + "</" + key + ">");
                }
            }
        }
    }
}
