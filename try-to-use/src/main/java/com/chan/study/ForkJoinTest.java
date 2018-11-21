package com.chan.study;


import com.chan.study.forkjoin.bean.CustomerInfo;
import com.chan.study.forkjoin.bean.DiscountInfo;
import com.chan.study.forkjoin.bean.FoodListInfo;
import com.chan.study.forkjoin.bean.OrderInfo;
import com.chan.study.forkjoin.bean.OtherInfo;
import com.chan.study.forkjoin.bean.TenantInfo;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

public class ForkJoinTest {
    public static class OrderTask extends RecursiveTask<OrderInfo> {
        @Override
        protected OrderInfo compute() {
            System.out.println("执行" + this.getClass().getSimpleName() + "线程名字为:" + Thread.currentThread().getName());// 定义其他五种并行TasK

            CustomerTask customerTask = new CustomerTask();
            TenantTask tenantTask = new TenantTask();
            DiscountTask discountTask = new DiscountTask();
            FoodTask foodTask = new FoodTask();
            OtherTask otherTask = new OtherTask();
            invokeAll(customerTask, tenantTask, discountTask, foodTask, otherTask);
            OrderInfo orderInfo = new OrderInfo(customerTask.join(), tenantTask.join(), discountTask.join(), foodTask.join(), otherTask.join());
            return orderInfo;
        }
    }

    public static void main(String[] args) {
        ForkJoinPool forkJoinPool = new ForkJoinPool(Runtime.getRuntime().availableProcessors() - 1);
        System.out.println(forkJoinPool.invoke(new OrderTask()));
    }


    static class CustomerTask extends RecursiveTask<CustomerInfo> {
        @Override
        protected CustomerInfo compute() {
            System.out.println("执行" + this.getClass().getSimpleName() + "线程名字为:" + Thread.currentThread().getName());
            return new CustomerInfo();
        }

    }

    static class TenantTask extends RecursiveTask<TenantInfo> {
        @Override
        protected TenantInfo compute() {
            System.out.println("执行" + this.getClass().getSimpleName() + "线程名字为:" + Thread.currentThread().getName());
            return new TenantInfo();
        }

    }

    static class DiscountTask extends RecursiveTask<DiscountInfo> {
        @Override
        protected DiscountInfo compute() {
            System.out.println("执行" + this.getClass().getSimpleName() + "线程名字为:" + Thread.currentThread().getName());
            return new DiscountInfo();
        }

    }

    static class FoodTask extends RecursiveTask<FoodListInfo> {
        @Override
        protected FoodListInfo compute() {
            System.out.println("执行" + this.getClass().getSimpleName() + "线程名字为:" + Thread.currentThread().getName());
            return new FoodListInfo();
        }
    }

    static class OtherTask extends RecursiveTask<OtherInfo> {
        @Override
        protected OtherInfo compute() {
            System.out.println("执行" + this.getClass().getSimpleName() + "线程名字为:" + Thread.currentThread().getName());
            return new OtherInfo();
        }
    }
}
