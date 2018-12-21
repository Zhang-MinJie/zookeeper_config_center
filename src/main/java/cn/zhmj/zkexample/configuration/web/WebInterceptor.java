package cn.zhmj.zkexample.configuration.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import cn.zhmj.zkexample.exception.RequestLockException;
import cn.zhmj.zkexample.exception.RequestWaitException;
import cn.zhmj.zkexample.service.RedisService;

@Component
public class WebInterceptor implements HandlerInterceptor {
	
    private final static Logger logger = LoggerFactory.getLogger(WebInterceptor.class);
	
    @Autowired
    private RedisService redisService;
	
	@Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws RequestWaitException {
        logger.info(request.getRequestURI());
        request.getSession().setAttribute("requestTime", System.currentTimeMillis());
        if(redisService.setLock(request.getRequestURI(), 30000, request.getSession().getId()) == false) {
        	throw new RequestWaitException("请求排队中...");
        }
		return true;
    }

	@Override
	public void postHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
	}

	@Override
	public void afterCompletion(HttpServletRequest request,
			HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		if(redisService.releaseLock(request.getRequestURI(), request.getSession().getId()) == false) {
			// 解锁失败
			throw new RequestLockException("解锁失败");
		}
		long requestTime = (Long) request.getSession().getAttribute("requestTime");
		long responseTime = System.currentTimeMillis();
		logger.info("[请求uri] - {} | {}ms", request.getRequestURI(), responseTime - requestTime);
    }
}
