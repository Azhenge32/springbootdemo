package com.azhen.service;

/**
 * @author Azhen
 * @date 2018/04/15
 */
public interface PropagationService {
    /**
     * 每一次方法调用都会创建一个新的事务，若当前有事务，则会把当前事务挂起
     * 所有方法都在不同的事务中运行
     */
    void requireNewSuccess();

    /**
     * 有事务就用事务，没有事务就不用事务
     */
    void supports();

    /**
     * 强制方法不用事务，若有事务，则在方法结束阶段事务都会被挂起
     */
    void notSupported();
    /**
     * 强制不在事务中运行，若有事务则抛出异常
     */
    void never();

    /**
     * 强制在事务中运行，没有事务则抛出异常
     */
    void mandatory();
    /**
     * 有事务则在事务中运行，没有事务则创建一个事务，最终所有方法都在同一个事务中
     */
    void requireFail();

    void require2();
}
