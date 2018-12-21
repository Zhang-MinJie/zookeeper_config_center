package cn.zhmj.zkexample.configuration.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;
/**
 * 
 * @author 章敏杰
 * @date 2018年3月23日
 * @version 0.0.1
 *
 */
@Configuration
public class InterceptorConfiguration extends WebMvcConfigurationSupport {
	@Autowired
	private WebInterceptor webInterceptor;
	
	public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(webInterceptor).addPathPatterns("/**");
    }
}
