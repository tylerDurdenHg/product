package com.hg.product.annotations;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/*
 * If the annotation {@code @Since} is present on the declaration 
 * of the method, It shows it can be used from which version 
 *
 */

@Documented
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Since {
	
	String version();
}
