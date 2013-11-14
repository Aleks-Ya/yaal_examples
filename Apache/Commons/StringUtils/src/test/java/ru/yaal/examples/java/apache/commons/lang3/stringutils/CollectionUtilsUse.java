package ru.yaal.examples.java.apache.commons.lang3.stringutils;

import org.apache.commons.collections.CollectionUtils;

import java.util.ArrayList;

public class CollectionUtilsUse {
    public static void main(String[] args) {
        CollectionUtils.isNotEmpty(new ArrayList(0));
    }
}
