/*
 * Copyright 2015-2020 msun.com All right reserved.
 */
package com.msun.luckyBlog;

import com.lamfire.utils.NumberUtils;

/**
 * @author zxc Aug 15, 2016 5:27:51 PM
 */
public class Test {

    public static void main(String[] args) {
        System.out.println(NumberUtils.format(50000, 2));
        System.out.println(NumberUtils.format(0, 2));
    }
}
