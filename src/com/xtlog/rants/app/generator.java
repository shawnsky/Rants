package com.xtlog.rants.app;

import com.xtlog.rants.pojo.Rant;
import org.mybatis.generator.api.MyBatisGenerator;
import org.mybatis.generator.config.Configuration;
import org.mybatis.generator.config.xml.ConfigurationParser;
import org.mybatis.generator.internal.DefaultShellCallback;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by admin on 2017/4/15.
 */
public class generator {
    public static void main(String[] args) throws Exception {
        try {
            List<String> warnings = new ArrayList<String>();
            boolean overwrite = true;
            //指定 逆向工程配置文件
//            File configFile = new File("config/generator.xml");
//            ConfigurationParser cp = new ConfigurationParser(warnings);
//            Configuration config = cp.parseConfiguration(configFile);
//            DefaultShellCallback callback = new DefaultShellCallback(overwrite);
//            MyBatisGenerator myBatisGenerator = new MyBatisGenerator(config,
//                    callback, warnings);
//            myBatisGenerator.generate(null);
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

}
