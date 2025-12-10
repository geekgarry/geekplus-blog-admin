package com.geekplus.codegenerate;

import java.io.File;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * author     : geekplus
 * email      :
 * date       : 7/16/25 9:34 PM
 * description: //TODO
 */
interface A {
    int x=0;
}
class B {
    int x=1;
}
public class Test1 extends B implements A {
    public void pX(){
        System.out.println(super.x);
    }
    public static void main(String[] args) {
//        Pattern ARTICLE_RESOURCE_PATTERN = Pattern.compile("^/article(/\\\\w+)+(/\\\\w+)*/(.+)$");
//        String logicalPath="/article/2025/10/24/36367388892/geek.png";
//        System.out.println(logicalPath.substring(logicalPath.lastIndexOf(File.separator)+1));
//        Matcher articleMatcher = ARTICLE_RESOURCE_PATTERN.matcher(logicalPath);
//        if(articleMatcher.matches()) {
//            System.out.println(articleMatcher.group(1) + "/avatar." + articleMatcher.group(2));
//        }
        String url = "/profile/**";
        String regex = "^/article(/\\w+)+(/\\w+)*/(.+)$";
        String path = "/article/2025/10/24/geek.png";

        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(path);

        if (matcher.matches()) {
            System.out.println("匹配成功");
            System.out.println(matcher.group(3));
            System.out.println(matcher.group());
            System.out.println(matcher.groupCount());
            for (int i = 1; i <= matcher.groupCount(); i++) {
                System.out.println("Group " + i + ": " + matcher.group(i));
            }
        } else {
            System.out.println("匹配失败");
        }
        System.out.println(url.substring(url.replace("/**", "").length()));
        new Test1().pX();
    }
}

