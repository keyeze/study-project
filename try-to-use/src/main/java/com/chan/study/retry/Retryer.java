package com.chan.study.retry;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

/**
 * 自己尝试重试框架
 *
 * @param <T>
 */
public class Retryer<T> {
    //是否暂停的检测策略
    private StopStrategy<T> stopStrategy;

    public T execute(IService<T> service) throws RetryException {
        try {
            return asynsExecute(service).apply();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public Result<T> asynsExecute(IService<T> service) throws RetryException {
        return null;
    }

    private Result<T> tryDo(IService<T> service) {
        try {
            T result = service.doService();
        } catch (Exception e) {
            Exception expResult;
        }
        return null;

    }


    private static class StopException<T> {
        private Result<T> result;

        public StopException(T result) {
            this.result = new ExpectedResult<>(result);
        }

        public Result<T> getResult() {
            return result;
        }
    }

    private static class RetryException extends Exception {
    }

    public static void main(String[] args) throws ParseException, InterruptedException {
        while (true) {
            long endTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse("2018-08-06 19:00:00").getTime();
            long nowTime = System.currentTimeMillis();
            long temp;
            long hour = (endTime - nowTime) / (1000 * 60 * 60);
            temp = (endTime - nowTime) % (1000 * 60 * 60);
            long min = temp / (1000 * 60);
            temp = (endTime - nowTime) % (1000 * 60);
            long second = temp / 1000;
            long mil = temp % 1000;
            NumberFormat nf = new DecimalFormat("00");
            NumberFormat nf2 = new DecimalFormat("00");
            System.out.println("距离下班还有:" + nf.format(hour) + ":" + nf.format(min) + ":" + nf.format(second) + "." + nf2.format(mil));
            Thread.sleep(1000);
        }
    }


    private class UnexpectedExpResult<T> implements Result<T> {
        private Exception e;

        public UnexpectedExpResult(Exception e) {
            super();
        }

        @Override
        public T apply() throws Exception {
            throw e;
        }

        @Override
        public ResultType resultType() {
            return ResultType.UnexpectedExp;
        }
    }

    public interface Result<T> {
        enum ResultType {
            /**
             *
             */
            UnexpectedExp, UnexpectedResult, ExpectedExp, ExpectedResult;
        }

        /**
         * 应用结果集
         *
         * @return
         *
         * @throws Exception
         */
        T apply() throws Exception;

        /**
         * 获得结果类型
         *
         * @return
         */
        ResultType resultType();
    }

    private class ExpectedExpResult<T> implements Result<T> {

        private Exception e;

        public ExpectedExpResult(Exception e) {
            super();
        }

        @Override
        public T apply() throws Exception {
            throw e;
        }

        @Override
        public ResultType resultType() {
            return ResultType.ExpectedExp;
        }
    }


    /**
     * 预期的结果
     *
     * @param <T>
     */
    private static class ExpectedResult<T> implements Result<T> {
        private T result;

        public ExpectedResult(T result) {
            super();
            this.result = result;
        }

        @Override
        public T apply() throws Exception {
            return result;
        }

        /**
         * 获得结果类型
         *
         * @return
         */
        @Override
        public ResultType resultType() {
            return ResultType.ExpectedResult;
        }
    }
}
