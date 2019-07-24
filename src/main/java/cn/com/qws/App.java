package cn.com.qws;

import cn.com.qws.service.system.CustomUserDetailsService;
import cn.com.qws.service.system.CustomUserDetailsService;
import com.alibaba.druid.pool.DruidDataSource;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.beetl.core.resource.WebAppResourceLoader;
import org.beetl.ext.spring.BeetlGroupUtilConfiguration;
import org.beetl.ext.spring.BeetlSpringViewResolver;
import org.beetl.sql.core.ClasspathLoader;
import org.beetl.sql.core.Interceptor;
import org.beetl.sql.core.UnderlinedNameConversion;
import org.beetl.sql.core.db.MySqlStyle;
import org.beetl.sql.ext.DebugInterceptor;
import org.beetl.sql.ext.spring4.BeetlSqlDataSource;
import org.beetl.sql.ext.spring4.BeetlSqlScannerConfigurer;
import org.beetl.sql.ext.spring4.SqlManagerFactoryBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.core.env.Environment;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternUtils;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import javax.sql.DataSource;
import java.io.IOException;

@SpringBootApplication
@EnableSwagger2
@EnableScheduling
@EnableAsync
public class App implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(App.class, args);
	}

	private static Logger logger = LoggerFactory.getLogger(App.class);

	@Bean
	public BCryptPasswordEncoder bCryptPasswordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public RedisTemplate<String, String> redisTemplate(RedisConnectionFactory redisFactory) {
		StringRedisTemplate template = new StringRedisTemplate(redisFactory);
		Jackson2JsonRedisSerializer jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer(Object.class);

		ObjectMapper om = new ObjectMapper();
		om.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
		om.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
		jackson2JsonRedisSerializer.setObjectMapper(om);

		template.setValueSerializer(jackson2JsonRedisSerializer);
		template.afterPropertiesSet();
		return template;
	}

	@Override
	public void run(String... args) throws Exception {

	}

	// 配置文件
	@Bean(initMethod = "init", name = "beetlConfig")
	public BeetlGroupUtilConfiguration getBeetlGroupUtilConfiguration() {
		BeetlGroupUtilConfiguration beetlGroupUtilConfiguration = new BeetlGroupUtilConfiguration();
		ResourcePatternResolver patternResolver = ResourcePatternUtils.getResourcePatternResolver(new DefaultResourceLoader());
		try {
			// WebAppResourceLoader 配置root路径是关键
			WebAppResourceLoader webAppResourceLoader = new WebAppResourceLoader(patternResolver.getResource("classpath:/templates").getFile().getPath());
			beetlGroupUtilConfiguration.setResourceLoader(webAppResourceLoader);
		} catch (IOException e) {
			e.printStackTrace();
		}
		//读取配置文件信息
		return beetlGroupUtilConfiguration;

	}

	@Bean(name = "beetlViewResolver")
	public BeetlSpringViewResolver getBeetlSpringViewResolver(@Qualifier("beetlConfig") BeetlGroupUtilConfiguration beetlGroupUtilConfiguration) {
		BeetlSpringViewResolver beetlSpringViewResolver = new BeetlSpringViewResolver();
		beetlSpringViewResolver.setContentType("text/html;charset=UTF-8");
		beetlSpringViewResolver.setOrder(0);
		beetlSpringViewResolver.setConfig(beetlGroupUtilConfiguration);
		return beetlSpringViewResolver;
	}

	// 配置包扫描
	@Bean(name = "beetlSqlScannerConfigurer")
	public BeetlSqlScannerConfigurer getBeetlSqlScannerConfigurer() {
		BeetlSqlScannerConfigurer conf = new BeetlSqlScannerConfigurer();
		conf.setBasePackage("cn.com.qws.dao");
		conf.setDaoSuffix("Dao");
		conf.setSqlManagerFactoryBeanName("sqlManagerFactoryBean");
		return conf;
	}

	@Bean(name = "sqlManagerFactoryBean")
	@Primary
	public SqlManagerFactoryBean getSqlManagerFactoryBean(@Qualifier("datasource") DataSource datasource) {
		SqlManagerFactoryBean factory = new SqlManagerFactoryBean();

		BeetlSqlDataSource source = new BeetlSqlDataSource();
		source.setMasterSource(datasource);
		factory.setCs(source);
		factory.setDbStyle(new MySqlStyle());
		factory.setInterceptors(new Interceptor[]{new DebugInterceptor()});
		factory.setNc(new UnderlinedNameConversion());//开启驼峰
		factory.setSqlLoader(new ClasspathLoader("/sql"));//sql文件路径
		return factory;
	}


	// 配置数据库
	@Bean(name = "datasource")
	public DataSource getDataSource(Environment env) {
		DruidDataSource datasource = new DruidDataSource();
		String driver = env.getProperty("driver-class-name");
		datasource.setDriverClassName(driver);
		String url = env.getProperty("spring.datasource.url");
		datasource.setUrl(url);
		String userName = env.getProperty("spring.datasource.username");
		datasource.setUsername(userName);
		String password = env.getProperty("spring.datasource.password");
		datasource.setPassword(password);
		return datasource;
	}

	// 开启事务
	@Bean(name = "txManager")
	public DataSourceTransactionManager getDataSourceTransactionManager(@Qualifier("datasource") DataSource datasource) {
		DataSourceTransactionManager dsm = new DataSourceTransactionManager();
		dsm.setDataSource(datasource);
		return dsm;
	}

	@Bean
	public UserDetailsService myUserService(){
		return new CustomUserDetailsService();
	}

}
