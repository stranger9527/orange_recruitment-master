package com.hdu.orange_recruitment;

import com.baomidou.mybatisplus.generator.FastAutoGenerator;
import com.baomidou.mybatisplus.generator.config.OutputFile;
import com.baomidou.mybatisplus.generator.config.rules.DbColumnType;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.sql.Types;
import java.util.Collections;

@SpringBootTest
class MPGenerator {

    @Test
    void contextLoads() {

        FastAutoGenerator.create("jdbc:mysql://127.0.0.1:3306/orange_recruitment?serverTimezone=GMT%2B8", "root", "18358385132")
                .globalConfig(builder -> {
                    builder.author("alpha") // 设置作者
                            // 开启 swagger 模式 默认不开启
                            //.enableSwagger()
                            .outputDir(System.getProperty("user.dir")+"/src/main/java"); // 指定输出目录
                })
                .dataSourceConfig(builder -> builder.typeConvertHandler((globalConfig, typeRegistry, metaInfo) -> {
                    int typeCode = metaInfo.getJdbcType().TYPE_CODE;
                    if (typeCode == Types.SMALLINT) {
                        // 自定义类型转换
                        return DbColumnType.INTEGER;
                    }
                    return typeRegistry.getColumnType(metaInfo);

                }))
                .packageConfig(builder -> {
                    // 设置父包名
                    builder.parent("com.hdu.orange_recruitment")
                            // 设置父包模块名
                            //.moduleName("mapper")        System.getProperty("user.dir");
                            // 设置mapperXml生成路径
                            .pathInfo(Collections.singletonMap(OutputFile.xml, System.getProperty("user.dir")+"/src/main/resources/mapper"));
                })
                .strategyConfig(builder -> builder
                        .entityBuilder()
                        .enableLombok()
                )
                .strategyConfig(builder -> {
                    // 设置需要生成的表名
                    builder.addInclude("cv","enterprise","enterprise_staff","job","progress","user");
                    // 设置过滤表前缀
//                    builder.addTablePrefix("t_", "c_");
                })
                // 使用Freemarker引擎模板，默认的是Velocity引擎模板
                .templateEngine(new FreemarkerTemplateEngine())
                .execute();

    }
}

//
//import com.baomidou.mybatisplus.generator.AutoGenerator;
//import com.baomidou.mybatisplus.generator.config.*;
//import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
//import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;
//
//public class MPGenerator {
//    private static final String url = "jdbc:mysql://127.0.0.1:3306/orange_recruitment" +
//            "?useUnicode=true&characterEncoding=utf8" +
//            "&allowPublicKeyRetrieval=True&useSSL=false" +
//            "&serverTimezone=Asia/Shanghai";
//    private static final String userName = "root";
//    private static final String password = "18358385132";
//    private static final String tableName = "progress";
////    private static final String tablePrefix = "t_";
//
//    // 演示例子
//    public static void main(String[] args) {
//        // 代码生成器
//        AutoGenerator autoGenerator = new AutoGenerator();
//
//        // 全局配置
//        GlobalConfig globalConfig = new GlobalConfig();
//        String projectPath = System.getProperty("user.dir");
//        globalConfig.setOutputDir(projectPath + "/src/main/java");
//        globalConfig.setAuthor("alpha");
//        globalConfig.setFileOverride(false);  //默认就是false
//        globalConfig.setOpen(false);
//        // gc.setBaseResultMap(true); // mapper.xml 生成 ResultMap
//        // gc.setBaseColumnList(true); // mapper.xml 生成 ColumnList
//
//        // 自定义文件命名，注意 %s 会自动填充表实体属性！
//        globalConfig.setMapperName("%sMapper");
//        // gc.setXmlName("%sDao");
//        globalConfig.setServiceName("%sService");
//        globalConfig.setServiceImplName("%sServiceImpl");
//        globalConfig.setControllerName("%sController");
//        // gc.setSwagger2(true); 实体属性 Swagger2 注解
//        autoGenerator.setGlobalConfig(globalConfig);
//
//        // 数据源配置
//        DataSourceConfig dataSourceConfig = new DataSourceConfig();
//
//        dataSourceConfig.setUrl(url);
//        // dsc.setSchemaName("public");
//        dataSourceConfig.setDriverName("com.mysql.cj.jdbc.Driver");
//        dataSourceConfig.setUsername(userName);
//        dataSourceConfig.setPassword(password);
//        autoGenerator.setDataSource(dataSourceConfig);
//
//        // 包配置
//        PackageConfig packageConfig = new PackageConfig();
//        // pc.setParent("com.example.generate.out");
//        // pc.setModuleName("xxx");
////        packageConfig.setParent("com.example.generate." + tableName.substring(tablePrefix.length()));
//        packageConfig.setParent("com.hdu.generate" );
//        autoGenerator.setPackageInfo(packageConfig);
//
//        // 自定义配置
//        // InjectionConfig cfg = new InjectionConfig() {
//        //     @Override
//        //     public void initMap() {
//        //         // to do nothing
//        //     }
//        // };
//
//        // // 配置自定义输出模板
//        // TemplateConfig templateConfig = new TemplateConfig();
//        // //指定自定义模板路径，注意不要带上.ftl/.vm, 会根据使用的模板引擎自动识别
//        // templateConfig.setController("templates_mp/MyController.java");
//        // templateConfig.setService("templates_mp/MyService.java");
//        // templateConfig.setServiceImpl("templates_mp/MyServiceImpl.java");
//        // templateConfig.setMapper("templates_mp/MyMapper.java");
//        // templateConfig.setEntity("templates_mp/MyEntity.java");
//        // templateConfig.setXml(null);
//        // autoGenerator.setTemplate(templateConfig);
//
//        // 策略配置
//        StrategyConfig strategyConfig = new StrategyConfig();
//        strategyConfig.setNaming(NamingStrategy.underline_to_camel);
//        strategyConfig.setColumnNaming(NamingStrategy.underline_to_camel);
//        //strategy.setSuperEntityClass("你自己的父类实体,没有就不用设置!");
//        strategyConfig.setEntityLombokModel(true);
//        strategyConfig.setRestControllerStyle(true);
//
//        // 公共父类
//        // strategy.setSuperControllerClass("你自己的父类控制器,没有就不用设置!");
//        // 写于父类中的公共字段
//        // strategy.setSuperEntityColumns("id");
//
//        // 表名
//        strategyConfig.setInclude(tableName);
////        strategyConfig.setTablePrefix(tablePrefix);
//        autoGenerator.setStrategy(strategyConfig);
//        strategyConfig.setControllerMappingHyphenStyle(true);
//        autoGenerator.setTemplateEngine(new FreemarkerTemplateEngine());
//        autoGenerator.execute();
//    }
//}
