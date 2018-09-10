package com.zmm.tmsystem.dagger.scope;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;

import javax.inject.Scope;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * Description:
 * Author:zhangmengmeng
 * Date:2018/5/24
 * Time:上午11:19
 */

@Scope
@Documented
@Retention(RUNTIME)
public @interface FragmentScope {
}
