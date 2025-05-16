package com.iroff.supportlab.adapter.auth.in.web.validation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

@Documented
@Constraint(validatedBy = KoreanPhoneValidator.class)
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface KoreanPhone {

	String message() default "유효한 한국 휴대전화 번호여야 합니다 (예: 01012345678)";

	Class<?>[] groups() default {};

	Class<? extends Payload>[] payload() default {};
}