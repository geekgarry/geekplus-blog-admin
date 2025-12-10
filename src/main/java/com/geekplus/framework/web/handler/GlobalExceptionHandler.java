package com.geekplus.framework.web.handler;

import com.geekplus.common.domain.Result;
import com.geekplus.common.enums.ApiExceptionEnum;
import com.geekplus.framework.web.exception.BusinessException;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authz.UnauthenticatedException;
import org.apache.shiro.authz.UnauthorizedException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolationException;
import java.util.ArrayList;
import java.util.List;

/**
 * author     : geekplus
 * date       : 2022/5/1 12:35 上午
 * description: 统一异常处理器，返回统一的异常信息
 */

@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {

    /**
     * 违反约束异常
     */
    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseBody
    protected Result handleConstraintViolationException(ConstraintViolationException e, HttpServletRequest request) {
        log.info("handleConstraintViolationException start, uri:{}, caused by: ", request.getRequestURI(), e);
        //List<ParameterNameProvider> parameterInvalidItemList =new ParameterNameProvider();
        return Result.error(ApiExceptionEnum.PARAM_ERROR);
    }

    /**
     * 处理验证参数封装错误时异常
     */
    @ExceptionHandler(HttpMessageNotReadableException.class)
    @ResponseBody
    protected Result handleConstraintViolationException(HttpMessageNotReadableException e, HttpServletRequest request) {
        log.info("handleConstraintViolationException start, uri:{}, caused by: ", request.getRequestURI(), e);
        return Result.error(ApiExceptionEnum.PARAM_ERROR);
    }

    /**
     * 处理参数绑定时异常（反400错误码）
     */
    @ExceptionHandler(BindException.class)
    @ResponseBody
    protected Result handleBindException(BindException e, HttpServletRequest request) {
        log.info("handleBindException start, uri:{}, caused by: ", request.getRequestURI(), e);
        //List<ParameterInvalidItem> parameterInvalidItemList = ParameterInvalidItemHelper.convertBindingResultToMapParameterInvalidItemList(e.getBindingResult());
        return Result.error(ApiExceptionEnum.PARAM_ERROR);
    }

    /**
     * 处理使用@Validated注解时，参数验证错误异常（反400错误码）
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseBody
    protected Result handleMethodArgumentNotValidException(MethodArgumentNotValidException e, HttpServletRequest request) {
        log.info("handleMethodArgumentNotValidException start, uri:{}, caused by: ", request.getRequestURI(), e);
        //List<ParameterInvalidItem> parameterInvalidItemList = ParameterInvalidItemHelper.convertBindingResultToMapParameterInvalidItemList(e.getBindingResult());
        return Result.error(ApiExceptionEnum.PARAM_ERROR);
    }

    //参数类型错误
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    @ResponseBody
    public Object handleMethodArgumentTypeMismatchException(MethodArgumentTypeMismatchException e) {
        log.error("MethodArgumentTypeMismatchException: ", e);
        return new BusinessException(ApiExceptionEnum.PARAM_ERROR, "参数"+e.getName() + ":" + "字段类型错误");
    }

    //参数缺少
    @ExceptionHandler(MissingServletRequestParameterException.class)
    @ResponseBody
    public Object handleMissingServletRequestParameterException(MissingServletRequestParameterException e) {
        log.error("MissingServletRequestParameterException: ", e);
        return new BusinessException(ApiExceptionEnum.PARAM_ERROR, e.getMessage());
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    @ResponseBody
    public Object handleHttpException(ServletException e) {
        log.error("ServletException: ", e);
        return new BusinessException(ApiExceptionEnum.FAIL, e.getMessage());
    }

    //捕获CommonException异常
    @ExceptionHandler(value = BusinessException.class)
    @ResponseBody
    public ResponseEntity<Result> apiExceptionHandler(BusinessException e, HttpServletRequest request){
        log.info("handleApiException start, uri:{}, exception:{}, caused by: {}", request.getRequestURI(), e.getClass(), e.getCode()+","+e.getMsg());
        //log.info("返回信息："+e.getCode());
        //获得异常消息
        Result result=Result.error(e.getMsg());
        //设置错误消息页面返回
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    /**
     * 处理运行时系统异常（反500错误码）
     */
//    @ExceptionHandler(RuntimeException.class)
//    @ResponseBody
//    protected Result handleRuntimeException(RuntimeException e, HttpServletRequest request) {
//        log.error("handleRuntimeException start, uri:{}, caused by:{}", request.getRequestURI(), e.getMessage());
//        return Result.error(ApiExceptionEnum.SERVICE_ERROR);
//    }

    /**
     * 无权访问
     *
     * @param request
     * @param e
     * @return
     */
    @ExceptionHandler(UnauthorizedException.class)
    @ResponseBody
    protected Result unauthorizedHandler(HttpServletRequest request, UnauthorizedException e) {
        log.error("unauthorizedHandler start, uri:{}, caused by: ", request.getRequestURI(), e);
        return Result.error(ApiExceptionEnum.PERMISSION);
    }

    @ExceptionHandler(IncorrectCredentialsException.class)
    @ResponseBody
    protected Result handleIncorrectCredentialsException(IncorrectCredentialsException e, HttpServletRequest request) {
        return Result.error(ApiExceptionEnum.LOGIN_USERNAME_ERROR);
    }

    /**
     * Description: 会话超时，后台登出，提示用户重新登录
     * @Version1.0 2021年3月12日 上午10:38:58 by wggg (zksszmz@yeah.net)
     * @param e
     * @param request
     * @return
     */
    @ExceptionHandler(UnauthenticatedException.class)
    @ResponseBody
    public Result handleUnauthenticatedException(UnauthenticatedException e,
                                                             HttpServletRequest request) {
        log.error("unauthorizedHandler start, uri:{}, caused by: ", request.getRequestURI(), e);
        return Result.error(ApiExceptionEnum.LOGIN_MUST);
    }

    private BusinessException handleBindingResult(BindingResult result) {
        //把异常处理为对外暴露的提示
        List<String> list = new ArrayList<>();
        if (result.hasErrors()) {
            List<ObjectError> allErrors = result.getAllErrors();
            for (ObjectError objectError : allErrors) {
                String message = objectError.getDefaultMessage();
                list.add(message);
            }
        }
        if (list.size() == 0) {
            return new BusinessException(ApiExceptionEnum.ILLEGAL_CALLBACK_REQUEST_ERROR);
        }
        return new BusinessException(ApiExceptionEnum.ILLEGAL_CALLBACK_REQUEST_ERROR, list.toString());
    }
}
