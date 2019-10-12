package com.chan.study.cloud.authentication.warm;

/**
 * 就是想提醒你.这个值不要序列化起来比较好,现在是单登录没问题,多多登录时,要保证更新所有uuid相关的roles信息.
 *
 * @author CtChan
 */
public @interface Dynamic {
}
