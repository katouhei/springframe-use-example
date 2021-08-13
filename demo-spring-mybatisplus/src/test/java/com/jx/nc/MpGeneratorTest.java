package com.jx.nc;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.InjectionConfig;
import com.baomidou.mybatisplus.generator.config.DataSourceConfig;
import com.baomidou.mybatisplus.generator.config.GlobalConfig;
import com.baomidou.mybatisplus.generator.config.PackageConfig;
import com.baomidou.mybatisplus.generator.config.StrategyConfig;
import com.baomidou.mybatisplus.generator.config.TemplateConfig;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;

public class MpGeneratorTest {

    @Test
    public void generateCode() {
        generate("systest", "sys_log");
    }

    private void generate(String moduleName, String... tableNamesInclude){
        AutoGenerator mpg = new AutoGenerator();

        // 全局配置
        GlobalConfig gc = new GlobalConfig();
//        String projectPath = System.getProperty("user.dir");
        gc.setOutputDir("f:/tmp");
        gc.setAuthor("katouhei");
        gc.setOpen(false);
        //默认不覆盖，如果文件存在，将不会再生成，配置true就是覆盖
        gc.setFileOverride(true);
        mpg.setGlobalConfig(gc);
        GlobalConfig.Builder gb = new GlobalConfig.Builder().author("").outputDir("").openDir(false).fileOverride(true);

        // 数据源配置
        DataSourceConfig dsc = new DataSourceConfig();
        dsc.setUrl("jdbc:mysql://localhost:3306/myslavedb?useUnicode=true&serverTimezone=GMT&useSSL=false&characterEncoding=utf8");
        // dsc.setSchemaName("public");
        dsc.setDriverName("com.mysql.jdbc.Driver");
        dsc.setUsername("root");
        dsc.setPassword("123456");
        mpg.setDataSource(dsc);

        DataSourceConfig dataSourceConfig = new DataSourceConfig
                .Builder("jdbc:mysql://localhost:3306/myslavedb?useUnicode=true&serverTimezone=GMT&useSSL=false&characterEncoding=utf8", "root", "123456")
                .driver(com.mysql.jdbc.Driver.class).build();

        // 包配置
        PackageConfig pc = new PackageConfig();
        pc.setModuleName(moduleName);
        pc.setParent("com.jx.nc");
        mpg.setPackageInfo(pc);

        // 策略配置
        StrategyConfig strategy = new StrategyConfig();
        strategy.setNaming(NamingStrategy.underline_to_camel);
        strategy.setColumnNaming(NamingStrategy.underline_to_camel);
//        strategy.setSuperEntityClass("com.baomidou.mybatisplus.samples.generator.common.BaseEntity");
        strategy.setEntityLombokModel(true);
//        strategy.setSuperControllerClass("com.baomidou.mybatisplus.samples.generator.common.BaseController");
        strategy.setInclude(tableNamesInclude);
//        strategy.setSuperEntityColumns("id");
        strategy.setControllerMappingHyphenStyle(true);
        strategy.setTablePrefix(pc.getModuleName() + "_");
//        strategy.entityTableFieldAnnotationEnable(true);
        mpg.setStrategy(strategy);
        // 选择 freemarker 引擎需要指定如下加，注意 pom 依赖必须有！
        mpg.setTemplateEngine(new FreemarkerTemplateEngine());

//        configCustomizedCodeTemplate(mpg);
//        configInjection(mpg);

        mpg.execute();
    }

    /**
     * 自定义模板
     * @param mpg
     */
    private void configCustomizedCodeTemplate(AutoGenerator mpg){
        //配置 自定义模板
        TemplateConfig templateConfig = new TemplateConfig()
                .setEntity("templates/MyEntityTemplate.java")//指定Entity生成使用自定义模板
                .setXml(null);//不生成xml
        mpg.setTemplate(templateConfig);
    }

    /**
     * 配置自定义参数/属性
     *
     * @param mpg
     */
    private void configInjection(AutoGenerator mpg){
        // 自定义配置
        InjectionConfig cfg = new InjectionConfig() {
            @Override
            public void initMap() {
                Map<String, Object> map = new HashMap<>();
                map.put("abc", this.getConfig().getGlobalConfig().getAuthor() + "-mp");
                this.setMap(map);
                /*
                自定义属性注入: 模板配置：abc=${cfg.abc}
                 */
            }
        };
//        List<FileOutConfig> focList = new ArrayList<>();
//        focList.add(new FileOutConfig("/templates/MyEntityTemplate.java.ftl") {
//            @Override
//            public File outputFile(TableInfo tableInfo) {
//                // 指定模板生，自定义生成文件到哪个地方
//                return new File("D:/abc");
//            }
//        });
//        cfg.setFileOutConfigList(focList);
        mpg.setCfg(cfg);
    }
}
