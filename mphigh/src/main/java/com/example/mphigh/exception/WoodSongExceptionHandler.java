package com.example.mphigh.exception;


import com.example.mphigh.result.CodeMsg;
import com.example.mphigh.result.Result;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.List;
import java.util.Set;


@ControllerAdvice
@ResponseBody
public class WoodSongExceptionHandler {

	private final static Logger logger = LoggerFactory.getLogger(WoodSongExceptionHandler.class);

	@ExceptionHandler(value=Exception.class)
	public Result<String> exceptionHandler(HttpServletRequest request, Exception e){
		e.printStackTrace();
		if(e instanceof WoodSongException) {

			WoodSongException ex = (WoodSongException)e;
			return Result.error(ex.getCode(),ex.getMessage());

		}else {
			logger.error("[服务端异常]{}", e);
			return Result.error(CodeMsg.SERVER_ERROR);
		}
	}

	/*  数据校验处理 */
	@ExceptionHandler({BindException.class, ConstraintViolationException.class})
	public Result<String> validatorExceptionHandler(Exception e) {
		String msg = e instanceof BindException ? msgConvertor(((BindException) e).getBindingResult())
				: msgConvertor(((ConstraintViolationException) e).getConstraintViolations());

		return Result.error(CodeMsg.BIND_ERROR.fillArgs(msg));
	}

	/**
	 * 校验消息转换拼接
	 *
	 * @param bindingResult
	 * @return
	 */
	public static String msgConvertor(BindingResult bindingResult) {
		List<FieldError> fieldErrors = bindingResult.getFieldErrors();
		StringBuilder sb = new StringBuilder();
		fieldErrors.forEach(fieldError -> sb.append(fieldError.getDefaultMessage()).append(","));

		return sb.deleteCharAt(sb.length() - 1).toString().toLowerCase();
	}

	private String msgConvertor(Set<ConstraintViolation<?>> constraintViolations) {
		StringBuilder sb = new StringBuilder();
		constraintViolations.forEach(violation -> sb.append(violation.getMessage()).append(","));

		return sb.deleteCharAt(sb.length() - 1).toString().toLowerCase();
	}
}
