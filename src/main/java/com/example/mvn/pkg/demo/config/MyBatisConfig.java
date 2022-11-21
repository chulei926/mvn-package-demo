package com.example.mvn.pkg.demo.config;

import org.apache.ibatis.session.ExecutorType;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.core.type.classreading.CachingMetadataReaderFactory;
import org.springframework.core.type.classreading.MetadataReader;
import org.springframework.core.type.classreading.MetadataReaderFactory;
import org.springframework.util.ClassUtils;

import javax.annotation.Resource;
import javax.sql.DataSource;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Configuration
@MapperScan("com.example.mvn.pkg.demo.**.mapper.**")
public class MyBatisConfig {

	private static final Logger logger = LoggerFactory.getLogger(MyBatisConfig.class);
	private static final String DEFAULT_RESOURCE_PATTERN = "**/*.class";
	@Resource
	private Environment env;
	@Resource
	private DataSource dataSource;

	@Value("${custom.mybatis.log.enable:false}")
	private Boolean enableLog;

	@Bean(name = "sqlSessionFactory")
	public SqlSessionFactoryBean sqlSessionFactory() throws Exception {
		// 创建 sqlSessionFactory
		SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
		// 配置全局配置文件
		// sqlSessionFactoryBean.setConfigLocation(new ClassPathResource(mybatisConfigFilePath));
		// 配置数据源
		sqlSessionFactoryBean.setDataSource(dataSource);
		// 配置 sql xml 文件
		sqlSessionFactoryBean.setMapperLocations(resolveMapperLocations());
		// 配置别名包
		sqlSessionFactoryBean.setTypeAliasesPackage(resolveTypeAliasesPackage());
		// 自定义配置
		org.apache.ibatis.session.Configuration myBatisConfig = new org.apache.ibatis.session.Configuration();
		//		myBatisConfig.setLogImpl(org.apache.ibatis.logging.stdout.StdOutImpl.class);
		if (Boolean.TRUE.equals(enableLog)) {
			myBatisConfig.setLogImpl(org.apache.ibatis.logging.stdout.StdOutImpl.class);
		}
		myBatisConfig.setCacheEnabled(true);
		myBatisConfig.setUseGeneratedKeys(true);
		myBatisConfig.setDefaultExecutorType(ExecutorType.REUSE);
		myBatisConfig.setMapUnderscoreToCamelCase(true);
		sqlSessionFactoryBean.setConfiguration(myBatisConfig);

		logger.info("mybatis sqlSessionFactory init success ......");
		return sqlSessionFactoryBean;
	}

	private org.springframework.core.io.Resource[] resolveMapperLocations() throws IOException {
		String basePath = env.getProperty("custom.mybatis.mapper_locations");
		PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
		final org.springframework.core.io.Resource[] resources = resolver.getResources(basePath);
		logger.info("mybatis mapperLocations parse completed ......");
		return resources;
	}

	private String resolveTypeAliasesPackage() {
		String basePath = env.getProperty("custom.mybatis.type_aliases_package");
		ResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
		MetadataReaderFactory metadataReaderFactory = new CachingMetadataReaderFactory(resolver);
		List<String> allResult = new ArrayList<>();
		try {
			for (String aliasesPackage : basePath.split(",")) {
				Set<String> result = new HashSet<>();
				String pkg = String.format("%s%s/%s",
						ResourcePatternResolver.CLASSPATH_ALL_URL_PREFIX, ClassUtils.convertClassNameToResourcePath(aliasesPackage.trim()), DEFAULT_RESOURCE_PATTERN);
				org.springframework.core.io.Resource[] resources = resolver.getResources(pkg);
				if (resources != null && resources.length > 0) {
					MetadataReader metadataReader = null;
					for (org.springframework.core.io.Resource resource : resources) {
						if (!resource.isReadable()) {
							continue;
						}
						metadataReader = metadataReaderFactory.getMetadataReader(resource);
						result.add(Class.forName(metadataReader.getClassMetadata().getClassName()).getPackage().getName());
					}
				}
				if (!result.isEmpty()) {
					allResult.addAll(result);
				}
			}
		} catch (Exception e) {
			throw new RuntimeException("mybatis typeAliasesPackage 路径扫描异常！" + basePath, e);
		}
		if (allResult.size() > 0) {
			basePath = String.join(",", allResult.toArray(new String[0]));
		} else {
			throw new RuntimeException("mybatis typeAliasesPackage 路径扫描错误:" + basePath + "未找到任何包");
		}
		logger.info("mybatis typeAliasesPackage parse completed ......");
		return basePath;
	}

}
