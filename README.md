## swagger

* **地址示例** http://localhost:900/doc.html
* **springboot打开swagger地址404的解决办法** Application 实现 WebMvcConfigurer,并且重写以下方法:

``` java
@Override
public void addResourceHandlers(ResourceHandlerRegistry registry) {
    registry.addResourceHandler("doc.html").addResourceLocations("classpath:/META-INF/resources/");
    registry.addResourceHandler("/webjars/**").addResourceLocations("classpath:/META-INF/resources/webjars/");
}
```

## git
* **添加.gitignore文件** 
1. IDEA环境下安装,settings -> plugin 查询.ignore  点击安装(需要重启)
2. 项目右键 -> New -> .ignore file -> .gitignore file(Git) 
