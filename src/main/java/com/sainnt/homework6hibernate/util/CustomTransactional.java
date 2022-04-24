package com.sainnt.homework6hibernate.util;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface CustomTransactional {
    TransactionPropagation propagation() default TransactionPropagation.REQUIRED;
}
