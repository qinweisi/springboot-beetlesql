package cn.com.qws.conf.beetlsql;

import org.beetl.core.Template;
import org.beetl.sql.core.db.TableDesc;
import org.beetl.sql.ext.gen.CodeGen;
import org.beetl.sql.ext.gen.GenConfig;
import org.beetl.sql.ext.gen.SourceGen;

import java.io.IOException;

public class MapperCodeGen implements CodeGen {

    String pkg;
    private String mapperTemplate;

    public MapperCodeGen() {
        this.pkg = null;
        this.mapperTemplate = "";
        this.mapperTemplate = (new GenConfig()).getTemplate("/org/beetl/sql/ext/gen/mapper.btl");
    }

    public MapperCodeGen(String pkg) {
        this();
        this.pkg = pkg;
    }

    public String getMapperTemplate() {
        return this.mapperTemplate;
    }

    public void setMapperTemplate(String mapperTemplate) {
        this.mapperTemplate = mapperTemplate;
    }

    @Override
    public void genCode(String entityPkg, String entityClass, TableDesc tableDesc, GenConfig config, boolean isDisplay) {
        if (this.pkg == null) {
            this.pkg = entityPkg;
        }

        Template template = SourceGen.getGt().getTemplate(this.mapperTemplate);
        String mapperClass = entityClass + "Dao";
        template.binding("className", mapperClass);
        template.binding("package", this.pkg);
        template.binding("entityClass", entityClass);
        String mapperHead = "import " + entityPkg + ".*;" + SourceGen.CR;
        template.binding("imports", mapperHead);
        String mapperCode = template.render();
        if (isDisplay) {
            System.out.println(mapperCode);
        } else {
            try {
                SourceGen.saveSourceFile("E:/qws/IdeaProjects/yunpingtai/src/main/java", this.pkg, mapperClass, mapperCode);
            } catch (IOException var11) {
                throw new RuntimeException("mapper代码生成失败", var11);
            }
        }

    }


    public void genCode(String entityPkg, String entityClass, TableDesc tableDesc, GenConfig config, boolean isDisplay, String path) {
        if (this.pkg == null) {
            this.pkg = entityPkg;
        }

        Template template = SourceGen.getGt().getTemplate(this.mapperTemplate);
        String mapperClass = entityClass + "Dao";
        template.binding("className", mapperClass);
        template.binding("package", this.pkg);
        template.binding("entityClass", entityClass);
        String mapperHead = "import " + entityPkg + ".*;" + SourceGen.CR;
        template.binding("imports", mapperHead);
        String mapperCode = template.render();
        if (isDisplay) {
            System.out.println(mapperCode);
        } else {
            try {
                SourceGen.saveSourceFile(path, this.pkg, mapperClass, mapperCode);
            } catch (IOException var11) {
                throw new RuntimeException("mapper代码生成失败", var11);
            }
        }

    }

    public void genServiceCode(String entityPkg, String entityClass, TableDesc tableDesc, GenConfig config, boolean isDisplay, String path) {
        if (this.pkg == null) {
            this.pkg = entityPkg;
        }

        Template template = SourceGen.getGt().getTemplate(this.mapperTemplate);
        String mapperClass = entityClass + "Service";
        template.binding("className", mapperClass);
        template.binding("package", this.pkg);
        template.binding("entityClass", entityClass);
        String mapperHead = "import " + entityPkg + ".*;" + SourceGen.CR;
        template.binding("imports", mapperHead);
        String mapperCode = template.render();
        if (isDisplay) {
            System.out.println(mapperCode);
        } else {
            try {
                SourceGen.saveSourceFile(path, this.pkg, mapperClass, mapperCode);
            } catch (IOException var11) {
                throw new RuntimeException("service代码生成失败", var11);
            }
        }

    }
}
