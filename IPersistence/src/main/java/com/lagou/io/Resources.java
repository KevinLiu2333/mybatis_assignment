package com.lagou.io;

import java.io.InputStream;

/**
 * Created with IntelliJ IDEA.
 * User: kevliu3
 * Date: 2020/8/23
 * Time: 2:55 PM
 *
 * @author kevliu3
 */
public class Resources {

    //根据配置文件的路径,将配置文件加载成字节输入流,存储在内存中
    public static InputStream getResourcesAsStream(String path) {
        InputStream resourceAsStream = Resources.class.getClassLoader().getResourceAsStream(path);
        return resourceAsStream;
    }
}
