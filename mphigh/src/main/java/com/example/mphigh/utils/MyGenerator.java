package com.example.mphigh.utils;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.core.exceptions.MybatisPlusException;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.InjectionConfig;
import com.baomidou.mybatisplus.generator.config.*;
import com.baomidou.mybatisplus.generator.config.converts.MySqlTypeConvert;
import com.baomidou.mybatisplus.generator.config.po.TableFill;
import com.baomidou.mybatisplus.generator.config.po.TableInfo;
import com.baomidou.mybatisplus.generator.config.rules.DateType;
import com.baomidou.mybatisplus.generator.config.rules.DbColumnType;
import com.baomidou.mybatisplus.generator.config.rules.IColumnType;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;

import java.util.*;

public class MyGenerator {
    /*
    包名
     */
    private static final String PACKAGE_NAME = "com.example.mphigh";
    /**
     * 模块名称
     */
    private static final String MODULE_NAME = "module";
    /**
     * 代码⽣成者
     */
    private static final String AUTHOR = "jinxu18829190342@163.com";
    /**
     * JDBC相关配置
     */
    private static final String DRIVER = "org.mariadb.jdbc.Driver";
    private static final String URL = "jdbc:mariadb://193.112.121.129:3306/test?serverTimezone=GMT%2b8&useUnicode=true&characterEncoding=utf-8";
    private static final String USER_NAME = "root";
    private static final String PASSWORD = "zsroot";

    public static void main(String[] args) {
        // ⾃定义需要填充的字段
        List<TableFill> tableFillList = new ArrayList<TableFill>();
        //如 每张表都有⼀个创建时间、修改时间
        //⽽且这基本上就是通⽤的了，新增时，创建时间和修改时间同时修改
        //修改时，修改时间会修改，
        //虽然像Mysql数据库有⾃动更新⼏只，但像ORACLE的数据库就没有了，
        //使⽤公共字段填充功能，就可以实现，⾃动按场景更新了。
        //如下是配置
        TableFill sysCreateTime = new TableFill("create_time",
                FieldFill.INSERT);
        TableFill sysUpdateTime = new TableFill("update_time",
                FieldFill.UPDATE);
        TableFill sysCreateBy = new TableFill("create_by", FieldFill.INSERT);
        TableFill sysUpdateBy = new TableFill("update_by", FieldFill.UPDATE);
        tableFillList.add(sysCreateTime);
        tableFillList.add(sysUpdateTime);
        tableFillList.add(sysCreateBy);
        tableFillList.add(sysUpdateBy);

        String projectPath = System.getProperty("user.dir");
        /*
          全局配置
     */
         GlobalConfig config=new GlobalConfig();
        config.setActiveRecord(true)
                .setAuthor(AUTHOR)
                /* ⽣成路径 */
                .setOutputDir(projectPath + "/src/main/java/")
                // ⽂件覆盖
                .setFileOverride(true)
                // 主键策略
                .setIdType(IdType.ASSIGN_UUID)
                // 设置⽣成的service接⼝的名字的⾸字⺟是否为I,例如IEmployeeService
                .setServiceName("%sService")
                // ⾃定义⽂件命名，注意 %s 会⾃动填充表实体属性！
                //.setXmlName("%sMapper").setMapperName("%sDao")
                //⽣成基本的resultMap
                .setBaseResultMap(true)
                //⽣成基本的SQL⽚段
                .setBaseColumnList(true)
                //⽣成后打开⽂件夹
                .setOpen(false).setDateType(DateType.ONLY_DATE);

        // 2. 数据源配置
        DataSourceConfig dsConfig = new DataSourceConfig();
        // 设置数据库类型
        dsConfig.setDbType(DbType.MYSQL)
                .setDriverName(DRIVER)
                .setUrl(URL)
                .setUsername(USER_NAME)
                .setPassword(PASSWORD);
//                .setTypeConvert(new MySqlTypeConvert() {
//                    // ⾃定义数据库表字段类型转换【可选】
//                    @Override
//                    public IColumnType processTypeConvert(GlobalConfig globalConfig, String fieldType) {
//                        System.out.println("转换类型：" + fieldType);
//                        if (fieldType.toLowerCase().contains("tinyint")) {
//                            return DbColumnType.INTEGER;
//                        }
//                        return super.processTypeConvert(globalConfig, fieldType);
//                    }
//                });
        //3、策略配置
        StrategyConfig stConfig = new StrategyConfig();
        // 全局⼤写命名
        stConfig.setCapitalMode(true)
                // 指定表名 字段名是否使⽤下划线
                //.setDbColumnUnderline(true)
                // 数据库表映射到实体的命名策略
                .setNaming(NamingStrategy.underline_to_camel)
                // 数据库表字段映射到实体类的命名策略
                .setColumnNaming(NamingStrategy.underline_to_camel)
                // ⾃定义继承entity类，添加这⼀个会在⽣成实体类的时候继承entity
                .setSuperEntityClass(PACKAGE_NAME+".entity.BaseEntity")
                .setSuperEntityColumns("id", "create_by", "create_time",
                        "update_by","update_time", "remark", "version", "deleted" )
                // 实体是否为lombok模型
                .setEntityLombokModel(true)
                // ⽣成@RestController控制器
                .setRestControllerStyle(true)
                // 是否继承controller
                //.setSuperControllerClass("com.controller.BaseController")
                // 表前缀
                //.setTablePrefix("tbl_")
                // ⽣成的表
                .setInclude(scanner("表名，多个英⽂逗号分割").split(",")) // 控制台输⼊，后期添加可以⽤这个
                // 直接定义
                //.setInclude(new String[] {
                // "user",
                // "admin",
                // "course"
                //})
                //.setExclude(new String[]{"test"}) // 排除⽣成的表
                .setEntityBooleanColumnRemoveIsPrefix(false)
                //驼峰转连字符串
                .setControllerMappingHyphenStyle(true)
                // ⾃定义实体，公共字段
                .setTableFillList(tableFillList);
        // 4. 包名策略配置
        PackageConfig pkConfig = new PackageConfig();
        pkConfig.setParent(PACKAGE_NAME)
                //controller
                .setController("controller")
                //servcie
                .setService("service")
                //entity
                .setEntity("entity")
                //dao
                .setMapper("mapper")
                //mapper.xml
                .setXml("repository.mybatis");
        // 注⼊⾃定义配置，可以在 VM 中使⽤ cfg.abc 【可⽆】
        InjectionConfig cfg = new InjectionConfig() {
            @Override
            public void initMap() {
                Map<String, Object> map = new HashMap<>();
                map.put("abc", this.getConfig().getGlobalConfig().getAuthor()
                        + "-mp");
                this.setMap(map);
            }
        };
        // 如果模板引擎是 freemarker
        String templatePath = "/templates/mapper.xml.ftl";
        // ⾃定义输出⽂件⽬录
        List<FileOutConfig> focList = new ArrayList<>();
        // 调整xml⽣成⽬录演示
        focList.add(new FileOutConfig(templatePath) {
            @Override
            public String outputFile(TableInfo tableInfo) {
                // ⾃定义输出⽂件名 ， 如果你 Entity 设置了前后缀、此处注意 xml 的名称会跟着发⽣变化！！
                return projectPath + "/src/main/resources/mapper/" +
                        tableInfo.getEntityName() + "Mapper.xml";
            }
        });
        cfg.setFileOutConfigList(focList);
        // 配置模板
        // 关闭默认⽣成，如果设置空 OR Null 将不⽣成该模块。
        TemplateConfig tc = new TemplateConfig();
        tc.setXml(null);
        // 5. 整合配置
        // 代码⽣成器
        AutoGenerator ag = new AutoGenerator();
        ag.setGlobalConfig(config)
                .setDataSource(dsConfig)
                .setStrategy(stConfig)
                .setPackageInfo(pkConfig)
                .setCfg(cfg)
                .setTemplate(tc)
                .setTemplateEngine(new FreemarkerTemplateEngine());
        // 6. 执⾏
        ag.execute();
    }
    public static String scanner(String someThing) {
        Scanner scanner = new Scanner(System.in);
        StringBuilder help = new StringBuilder();
        help.append("请输⼊" + someThing + "：");
        System.out.println(help.toString());
        if (scanner.hasNext()) {
            String sc = scanner.next();
            if (!StringUtils.isBlank(sc)) {
                return sc;
            }
        }
        throw new MybatisPlusException("请输⼊正确的" + someThing + "！");
    }
}
