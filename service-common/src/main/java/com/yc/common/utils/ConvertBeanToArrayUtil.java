package com.yc.common.utils;
import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ConvertBeanToArrayUtil {

    public List<Object[]> convertListArray(List<Object> list)
            throws IntrospectionException, IllegalAccessException,
            InvocationTargetException {
        List<Object[]> list_obj = new ArrayList<Object[]>();
        for (int i = 0; i < list.size(); i++) {
            Object[] tmp = this.convertArray(list.get(i));
            list_obj.add(tmp);
        }
        return list_obj;
    }

    @SuppressWarnings("rawtypes")
    public static Object[] convertArray(Object bean) throws IntrospectionException,
            IllegalAccessException, InvocationTargetException {
        Map map = convertBean(bean);
        Object[] obj_array = map.values().toArray();
        return obj_array;
    }

    @SuppressWarnings({ "rawtypes", "unchecked" })
    public static Map convertBean(Object bean) throws IntrospectionException,
            IllegalAccessException, InvocationTargetException {
        Class type = bean.getClass();
        Map returnMap = new HashMap();
        BeanInfo beanInfo = Introspector.getBeanInfo(type);

        PropertyDescriptor[] propertyDescriptors = beanInfo
                .getPropertyDescriptors();
        for (int i = 0; i < propertyDescriptors.length; i++) {
            PropertyDescriptor descriptor = propertyDescriptors[i];
            String propertyName = descriptor.getName();
            if (!propertyName.equals("class")) {
                Method readMethod = descriptor.getReadMethod();
                Object result = readMethod.invoke(bean, new Object[0]);
                if (result != null) {
                    returnMap.put(propertyName, result);
                } else {
                    returnMap.put(propertyName, "");
                }
            }
        }
        return returnMap;
    }
}