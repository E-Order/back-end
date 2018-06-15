package com.eorder.utils;

import com.eorder.enums.CodeEnum;

import javax.persistence.criteria.CriteriaBuilder;

public class EnumUtil {
    public static <T extends CodeEnum> T getByCode(Integer code, Class<T> enumclass) {
        for (T each: enumclass.getEnumConstants()) {
            if (code.equals(each.getCode())) {
                return each;
            }
        }
        return null;
    }
}
