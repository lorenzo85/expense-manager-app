package com.spring.cms.service.aspects;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.stereotype.Component;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.HashSet;
import java.util.Set;

@Aspect
@Component
@EnableAspectJAutoProxy
public class ValidateAspect {

    @Autowired
    private Validator validator;

    public void setValidator(Validator validator) {
        this.validator = validator;
    }

    @Before("execution (* *(@com.spring.cms.service.aspects.IsValid (*)))")
    public void valid(JoinPoint jp) throws NoSuchMethodException {
        // Constraint Violations to return
        Set<ConstraintViolation<?>> violations = new HashSet<ConstraintViolation<?>>();

        // Get the Target method
        Method interfaceMethod = ((MethodSignature) jp.getSignature()).getMethod();
        Method implementationMethod = jp.getTarget().getClass().getMethod(interfaceMethod.getName(), interfaceMethod.getParameterTypes());

        // Get the annotated parameters and validate those with @IsValid annotation
        Annotation[][] annotationParameters = implementationMethod.getParameterAnnotations();
        for(int i = 0; i < annotationParameters.length; i++) {
            Annotation[] annotations = annotationParameters[i];
            for(Annotation annotation : annotations) {
                if(annotation.annotationType().equals(IsValid.class)) {
                    IsValid isValid = (IsValid) annotation;
                    Object arg = jp.getArgs()[i];
                    violations.addAll(validator.validate(arg, isValid.groups()));
                }
            }
        }

        if(!violations.isEmpty()) {
            throw new IllegalArgumentException("Validation failed! " + violations.toString());
        }
    }
}
