## swagger

* **地址示例** http://localhost:900/doc.html
* **springboot打开swagger地址404的解决办法** Application 实现 WebMvcConfigurer,并且重写以下方法:

``
@Override
  	public void addResourceHandlers(ResourceHandlerRegistry registry) {
  		registry.addResourceHandler("doc.html").addResourceLocations("classpath:/META-INF/resources/");
  		registry.addResourceHandler("/webjars/**").addResourceLocations("classpath:/META-INF/resources/webjars/");
  	}
``