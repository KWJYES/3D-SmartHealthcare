package com.example._3dsmarthealthcare;

import org.junit.jupiter.api.Test;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MyTest {
    @Test
    void test(){
        //编译正则表达式，这样子可以重用模式。
        Pattern p = Pattern.compile("^.+\\.(?i)(inn|pdf)$");
        // 用模式检查字符串
        Matcher m = p.matcher("dssa.inn");
        //检查匹配结果
        boolean b = m.matches();
        System.out.println(b);

    }
}
