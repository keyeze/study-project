package com.chan.study.cloud;

import static org.junit.Assert.assertTrue;

import com.chan.study.cloud.ribbon.RibbonApplication;
import com.chan.study.cloud.service.TestInterface;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * Unit test for simple App.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = RibbonApplication.class)
public class RibbonApplicationTest {
    /**
     * Rigorous Test :-)
     */
    @Autowired
    TestInterface testInterface;

    @Test
    public void shouldAnswerWithTrue() {
        assertTrue(true);
    }

    @Test
    public void testValue() throws InterruptedException {
        System.out.println(testInterface.returnBody("str"));
    }
}
