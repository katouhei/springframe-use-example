package com.jx.nc;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Collections;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.generator.fill.Column;
import com.baomidou.mybatisplus.generator.fill.Property;
import lombok.Data;
import org.apache.ibatis.jdbc.ScriptRunner;
import org.junit.Test;

import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.InjectionConfig;
import com.baomidou.mybatisplus.generator.config.DataSourceConfig;
import com.baomidou.mybatisplus.generator.config.GlobalConfig;
import com.baomidou.mybatisplus.generator.config.PackageConfig;
import com.baomidou.mybatisplus.generator.config.StrategyConfig;
import com.baomidou.mybatisplus.generator.config.TemplateConfig;
import com.baomidou.mybatisplus.generator.config.TemplateType;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;

public class GeneratorTest {
    @Data
    static class BaseEntity implements Serializable {

        private String id;

        private Integer flag;
    }


    @Test
    public void oracleTest() throws SQLException {
        new AutoGenerator(oracleDataSourceConfig())
                .global(globalConfig())
                .packageInfo(packageOracleConfig())
                .strategy(strategyOracleConfig())
                .engine(new FreemarkerTemplateEngine())
                .template(templateConfig())
                .injection(injectionConfig())
                .execute();
    }

    @Test
    public void mysqlTest() throws SQLException {
        new AutoGenerator(mysqlDataSourceConfig())
                .global(globalConfig())
                .packageInfo(packageConfig())
                .strategy(strategyConfig())
                .engine(new FreemarkerTemplateEngine())
                .template(templateConfig())
                .injection(injectionConfig())
                .execute();
    }

    /**
     * Oracle???????????????
     */
    private DataSourceConfig oracleDataSourceConfig() throws SQLException {
        DataSourceConfig dataSourceConfig = new DataSourceConfig
                .Builder("jdbc:oracle:thin:@192.168.3.149:1521:xe", "xajy", "xajy_123654")
                .driver(oracle.jdbc.driver.OracleDriver.class).build();
//        runScript(dataSourceConfig);
        return dataSourceConfig;
    }

    private void runScript(DataSourceConfig dataSourceConfig) throws SQLException {
        try (Connection connection = dataSourceConfig.getConn()) {
            InputStream inputStream = GeneratorTest.class.getResourceAsStream("/sql/init.sql");
            ScriptRunner scriptRunner = new ScriptRunner(connection);
            scriptRunner.setAutoCommit(true);
            scriptRunner.runScript(new InputStreamReader(inputStream));
        }
    }

    /**
     * Mysql???????????????
     */
    private DataSourceConfig mysqlDataSourceConfig() throws SQLException {
        DataSourceConfig dataSourceConfig = new DataSourceConfig
                .Builder("jdbc:mysql://localhost:3306/mymasterdb?useUnicode=true&serverTimezone=GMT&useSSL=false&characterEncoding=utf8", "root", "123456")
                .driver(com.mysql.jdbc.Driver.class).build();
//        runScript(dataSourceConfig);
        return dataSourceConfig;
    }

    /**
     * ????????????
     */
    private GlobalConfig globalConfig() {
        final String outPutDir = System.getProperty("os.name").toLowerCase().contains("windows") ? "F://tmp" : "/tmp";
        return new GlobalConfig.Builder().outputDir(outPutDir).author("katouhei").openDir(false).fileOverride(true).build();
    }

    /**
     * ?????????
     */
    private PackageConfig packageConfig() {
        return new PackageConfig.Builder().moduleName("sys").parent("com.jx.nc").build();
    }

    /**
     * ????????????
     */
    private StrategyConfig strategyConfig() {
        return new StrategyConfig.Builder()
                .addInclude("sys_log", "sys_user") // ???????????????
                .addTablePrefix("Sys_") //???????????????
                .entityBuilder()  // ??????????????????
                .naming(NamingStrategy.underline_to_camel)
                .columnNaming(NamingStrategy.underline_to_camel)
                .lombok(false)
//                    .addSuperEntityColumns("id","create_time","update_time")
//                .superClass(BaseEntity.class)   //????????????????????????
//                .addTableFills(new Column("create_time", FieldFill.INSERT))     //??????????????????
//                .addTableFills(new Property("updateTime",FieldFill.UPDATE))    //??????????????????
                .controllerBuilder().hyphenStyle(true)  //?????????????????????
                .build();
    }

    /**
     * ?????????
     */
    private PackageConfig packageOracleConfig() {
        return new PackageConfig.Builder().moduleName("system").parent("com.jx.nc").build();
    }

    /**
     * ????????????
     */
    private StrategyConfig strategyOracleConfig() {
        return new StrategyConfig.Builder()
                .addInclude("sys_user".toUpperCase()) // ???????????????
                .addTablePrefix("Sys_") //???????????????
                .entityBuilder()  // ??????????????????
                .naming(NamingStrategy.underline_to_camel)
                .columnNaming(NamingStrategy.underline_to_camel)
                .lombok(true)
//                    .addSuperEntityColumns("id","create_time","update_time")
//                .superClass(BaseEntity.class)   //????????????????????????
                .addTableFills(new Column("create_time", FieldFill.INSERT))     //??????????????????
                .addTableFills(new Property("updateTime",FieldFill.UPDATE))    //??????????????????
                .controllerBuilder().hyphenStyle(true)  //?????????????????????
                .build();
    }

    /**
     * ?????????????????????
     */
    private TemplateConfig templateConfig() {
        return new TemplateConfig.Builder().all()   //????????????????????????
//                .entity("templates/MyEntityTemplate.java")  //???????????????????????????????????????,???????????????????????????
                .build();
//                .disable(TemplateType.XML); //??????xml??????
        // ?????????????????????????????????????????????
//        return new TemplateConfig.Builder().entity("templates/MyEntityTemplate.java").controller().service().mapper().build();
    }

    /**
     * ???????????????
     */
    private InjectionConfig injectionConfig() {
        return new InjectionConfig(Collections.singletonMap("abc", this.globalConfig().getAuthor() + "-mp"));
    }
}
