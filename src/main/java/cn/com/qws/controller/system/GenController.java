package cn.com.qws.controller.system;

import cn.com.qws.conf.beetlsql.MapperCodeGen;
import cn.com.qws.conf.beetlsql.MapperCodeGen;
import org.beetl.sql.core.*;
import org.beetl.sql.core.db.DBStyle;
import org.beetl.sql.core.db.MySqlStyle;
import org.beetl.sql.core.db.TableDesc;
import org.beetl.sql.core.kit.StringKit;
import org.beetl.sql.ext.gen.GenConfig;
import org.beetl.sql.ext.gen.MDCodeGen;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

/**
 * @Description 根据表生成实体、sql文件、dao
 * @Author qinweisi
 * @Date 2019/7/19 09:19
 **/
@Controller
public class GenController {

    // ========数据库配置=========
    @Value(value = "${spring.datasource.driver-class-name}")
    private String driver;
    @Value(value = "${spring.datasource.url}")
    private String url;
    @Value(value = "${spring.datasource.username}")
    private String userName;
    @Value(value = "${spring.datasource.password}")
    private String password;

    // ========项目路径=========
    private static String srcPath = "E:/qws/IdeaProjects/yunpingtai/src/main/java";
    // ========模板的路径, 示例是spring boot的[src/main/resources/beetlsqlTemplate 文件夹]=========
    private static String templatePath = "/beetlsqlTemplate";
    // ========md生成路径 要提前创建=========/sys-base/users/main/resources/sql
    private static String mdPath = "E:/qws/IdeaProjects/yunpingtai/src/main/resources/sql";
    // ========生成实体类所在的包=========
    private static String pojoPkg = "cn.com.qws.entity";
    // ========生成mapper类所在的包=========
    private static String mapperPkg = "cn.com.qws.dao";
    // ========生成service类所在的包=========
    private static String servicePkg = "cn.com.qws.service";

    @RequestMapping("/gen")
    @ResponseBody
    public String genAll() throws Exception {
        //准备工作
        ConnectionSource source = ConnectionSourceHelper.getSimple(driver, url, userName, password);
        DBStyle mysql = new MySqlStyle();
        SQLLoader loader = new ClasspathLoader(mdPath);
        UnderlinedNameConversion nc = new UnderlinedNameConversion();
        SQLManager sqlManager = new SQLManager(mysql, loader, source, nc, null);
        GenConfig config = new GenConfig();
        config.setDisplay(false);
        config.setPreferBigDecimal(true);
        System.out.println("======生成代码======");
        //数据库所有表
        Set<String> tables = new HashSet<>();
        // 获取所有的表  有需要时打开
//        tables = sqlManager.getMetaDataManager().allTable();
        // 具体到某个表
        tables.add("t_users");
        for (String table : tables) {
            System.out.printf("%-20s %s\n", table, "生成完毕");
            // 生成实体
            sqlManager.genPojoCode(table, pojoPkg, getPath("", "/src/main/java"), config);
            // 生成md
            genMd(sqlManager, config, table);
            // 生成dao
            genMapper(sqlManager, config, table);
            // 生成service
            genService(sqlManager, config, table);
        }
        System.out.println("=====生成完毕=====");
        return "=====生成完毕=====";
    }


    /**
     * 生成md文件
     */
    public static void genMd(SQLManager sqlManager, GenConfig config, String table) throws IOException {
        String fileName = StringKit.toLowerCaseFirstOne(sqlManager.getNc().getClassName(table));
        if (config.getIgnorePrefix() != null && !config.getIgnorePrefix().trim().equals("")) {
            fileName = fileName.replaceFirst(StringKit.toLowerCaseFirstOne(config.getIgnorePrefix()), "");
            fileName = StringKit.toLowerCaseFirstOne(fileName);
        }
        String target = mdPath + "/" + fileName + ".md";
        TableDesc desc = sqlManager.getMetaDataManager().getTable(table);
        FileWriter writer = new FileWriter(new File(target));
        MDCodeGen mdCodeGen = new MDCodeGen();
        mdCodeGen.setMapperTemplate(config.getTemplate(templatePath + "/md.btl"));
        mdCodeGen.genCode(sqlManager.getBeetl(), desc, sqlManager.getNc(), "a", writer);
        writer.close();
    }

    /**
     * 生成mapper
     */
    public static void genMapper(SQLManager sqlManager, GenConfig config, String table) {
        MapperCodeGen mapperCodeGen = new MapperCodeGen("" + mapperPkg);
        mapperCodeGen.setMapperTemplate(config.getTemplate(templatePath + "/mapper.btl"));
        mapperCodeGen.genCode(pojoPkg, sqlManager.getNc().getClassName(table), sqlManager.getMetaDataManager().getTable(table), null, false, srcPath);
    }

    /**
     * 生成service
     */
    public static void genService(SQLManager sqlManager, GenConfig config, String table) {
        MapperCodeGen mapperCodeGen = new MapperCodeGen("" + servicePkg);
        mapperCodeGen.setMapperTemplate(config.getTemplate(templatePath + "/service.btl"));
        mapperCodeGen.genServiceCode(pojoPkg, sqlManager.getNc().getClassName(table), sqlManager.getMetaDataManager().getTable(table), null, false, srcPath);
    }

    /**
     * 生成的路径
     *
     * @param relativeToSrc module下的文件夹路径 如:/users/src/main/java/com/qmhd/users
     * @param moduleName    module的名称如sys-base
     * @return
     */
    private static String getPath(String moduleName, String relativeToSrc) {
        String userDir = System.getProperty("user.dir");
        if (userDir == null) {
            throw new NullPointerException("用户目录未找到");
        } else {
            File src = new File(userDir, moduleName);
            File resSrc = new File(src.toString(), relativeToSrc);
            System.out.println("" + resSrc);
            String srcPath;
            if (resSrc.exists()) {
                srcPath = resSrc.toString();
            } else {
                srcPath = src.toString();
            }
            return srcPath;
        }
    }

}
