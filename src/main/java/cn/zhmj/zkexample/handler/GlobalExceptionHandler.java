package cn.zhmj.zkexample.handler;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.zhmj.zkexample.exception.RequestWaitException;
import cn.zhmj.zkexample.vo.ErrorVo;
import redis.clients.jedis.exceptions.JedisNoReachableClusterNodeException;

@ControllerAdvice
public class GlobalExceptionHandler {

    private final static Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    @ResponseBody
    public ErrorVo argValidErrorHandler(HttpServletRequest req, MethodArgumentNotValidException e) {
        logger.error("[系统过滤] - {}:{}", ResultCode.PARAMS_ERROR_2.getMessage(), req.getParameterMap(), e);
        ErrorVo vo = new ErrorVo();
        vo.setResultCode(ResultCode.PARAMS_ERROR_2.getCode());
        vo.setErrorMsg(ResultCode.PARAMS_ERROR_2.getMessage());
        return vo;
    }

    @ExceptionHandler(value = HttpMessageNotReadableException.class)
    @ResponseBody
    public ErrorVo jsonHttpMessageNotReadableErrorHandler(HttpServletRequest req, Exception e) {
        logger.error("[系统异常] - {}:", ResultCode.PARAMS_ERROR_1.getMessage(), e);
        ErrorVo vo = new ErrorVo();
        vo.setResultCode(ResultCode.PARAMS_ERROR_1.getCode());
        vo.setErrorMsg(ResultCode.PARAMS_ERROR_1.getMessage());
        return vo;
    }

    @ExceptionHandler(value = HttpRequestMethodNotSupportedException.class)
    @ResponseBody
    public ErrorVo httpRequestMethodNotSupportedExceptionErrorHandler(HttpServletRequest req, Exception e) {
        logger.error("[系统异常] - {}:", ResultCode.REQUEST_METHOD_ERROR.getMessage(), e);
        ErrorVo vo = new ErrorVo();
        vo.setResultCode(ResultCode.REQUEST_METHOD_ERROR.getCode());
        vo.setErrorMsg(ResultCode.REQUEST_METHOD_ERROR.getMessage());
        return vo;
    }

    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    public ErrorVo ErrorHandler(HttpServletRequest req, Exception e) {
        logger.error("[系统异常] - {}:", ResultCode.ERROR.getMessage(), e);
        ErrorVo vo = new ErrorVo();
        vo.setResultCode(ResultCode.ERROR.getCode());
        vo.setErrorMsg(ResultCode.ERROR.getMessage());
        return vo;
    }

    @ExceptionHandler(value = RequestWaitException.class)
    @ResponseBody
    public ErrorVo requestWaitExceptionErrorHandler(HttpServletRequest req, RequestWaitException e) {
        logger.error("[请求等待] - {}:", ResultCode.REQUEST_WAIT.getMessage(), e);
        ErrorVo vo = new ErrorVo();
        vo.setResultCode(ResultCode.REQUEST_WAIT.getCode());
        vo.setErrorMsg(ResultCode.REQUEST_WAIT.getMessage());
        return vo;
    }
    
    @ExceptionHandler(value = JedisNoReachableClusterNodeException.class)
    @ResponseBody
    public ErrorVo jedisNoReachableClusterNodeExceptionErrorHandler(HttpServletRequest req, JedisNoReachableClusterNodeException e) {
        logger.error("[系统异常] - {}:", ResultCode.REDIS_ERROR.getMessage(), e);
        ErrorVo vo = new ErrorVo();
        vo.setResultCode(ResultCode.REDIS_ERROR.getCode());
        vo.setErrorMsg(ResultCode.REDIS_ERROR.getMessage());
        return vo;
    }

//    @ExceptionHandler(value = BussinessException.class)
//    @ResponseBody
//    public ErrorVo jsonBussinessErrorHandler(HttpServletRequest req, Exception e) throws Exception {
//        logger.warn("[warn][业务异常]params:" + req.getParameterMap() + ",systemError:" + e.getMessage());
//        ErrorVo vo = new ErrorVo();
//        vo.setReturnCode(ErrorVo.RETURN_CODE_400);
//        vo.setReturnMsg(e.getMessage());
//        return vo;
//    }

}
