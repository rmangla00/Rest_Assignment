package com.yash.aspect;

import java.util.logging.Logger;
import java.util.stream.Stream;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LoggingAspect {

	private Logger logger;

	@Pointcut("within(com.yash.controller.EmployeeController)")
	public void allEmployeeControllerMethods() {
	}

	@Pointcut("within(com.yash.service.EmployeeService)")
	public void allEmployeeServiceMethods() {
	}

	@Before("allEmployeeControllerMethods() or allEmployeeServiceMethods()")
	public void beforeLoggingAdvice(JoinPoint jointpoint) {
		logger = Logger.getLogger(jointpoint.getTarget().getClass().getName());
		System.setProperty("java.util.logging.SimpleFormatter.format", "%5$s%6$s%n\u001B[30m");
		logger.info("Before " + jointpoint.getSignature() + " " + JoinPoint.METHOD_CALL);
		logger.info("Input arguments for " + jointpoint.getSignature());
		Stream.of(jointpoint.getArgs()).forEach(item -> logger.info(item.toString()));
	}

	@AfterThrowing(value = "allEmployeeControllerMethods() or allEmployeeServiceMethods()", throwing = "exception")
	public void afterThrowingAdvice(JoinPoint jointpoint, Exception exception) throws Throwable {

		logger = Logger.getLogger(jointpoint.getTarget().getClass().getName());
		System.setProperty("java.util.logging.SimpleFormatter.format", "%5$s%6$s%n\u001B[30m");
		logger.info("After Throwing  " + exception.toString() + " Exception from " + jointpoint.getSignature());
	}

	@AfterReturning(value = "allEmployeeControllerMethods() or allEmployeeServiceMethods()", returning = "returnValue")
	public void afterReturningAdvice(JoinPoint jointpoint, Object returnValue) {

		logger = Logger.getLogger(jointpoint.getTarget().getClass().getName());
		System.setProperty("java.util.logging.SimpleFormatter.format", "%5$s%6$s%n\u001B[30m");
		logger.info(
				"After " + jointpoint.getSignature() + " " + JoinPoint.METHOD_EXECUTION + " returns : " + returnValue);
	}

	/*
	 * @Around(value =
	 * "allEmployeeControllerMethods() or allEmployeeServiceMethods()") public
	 * Object arroundLoggingAdvice(ProceedingJoinPoint jointpoint) throws Throwable
	 * { logger = Logger.getLogger(jointpoint.getTarget().getClass().getName());
	 * System.setProperty("java.util.logging.SimpleFormatter.format",
	 * "%5$s%6$s%n\u001B[30m"); Object returnValue = null;
	 * 
	 * logger.info("Before " + jointpoint.getSignature() + " " +
	 * JoinPoint.METHOD_CALL); logger.info("Input arguments for " +
	 * jointpoint.getSignature()); Stream.of(jointpoint.getArgs()).forEach(item ->
	 * logger.info(item.toString())); returnValue = jointpoint.proceed();
	 * logger.info("After " + jointpoint.getSignature() + " " +
	 * JoinPoint.METHOD_EXECUTION);
	 * 
	 * logger.info(jointpoint.getSignature() + " returns : " + returnValue); return
	 * returnValue; }
	 */

}
