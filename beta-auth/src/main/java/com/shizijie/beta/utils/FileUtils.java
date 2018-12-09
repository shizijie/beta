package com.shizijie.beta.utils;

import com.shizijie.beta.utils.encoding.EncodingDetect;
import org.springframework.util.StringUtils;

import java.io.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author shizijie
 * @version 2018-12-09 下午8:11
 */
public class FileUtils {
    public static List<String> readFile(String path) throws IOException {
        File file=new File(path);
        FileInputStream is=new FileInputStream(file);
        InputStreamReader in=new InputStreamReader(is, EncodingDetect.getJavaEncode(path));
        BufferedReader br=new BufferedReader(in);
        String line;
        List<String> list=new ArrayList<>();
        while((line=br.readLine())!=null){
            if(StringUtils.hasText(line)){
                list.add(line);
            }
        }
        if(br!=null){
            br.close();
        }
        if(in!=null){
            in.close();
        }
        if(is!=null){
            is.close();
        }
        return list;
    }

    public static void main(String[] args) throws IOException {
        List<String> list=readFile("/Users/shizijie/Desktop/配置文件/test/1");
        System.out.println(list.size());
        Set<String> set=new HashSet<>(list);
        System.out.println(set.size());
    }

}
