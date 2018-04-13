import com.chan.study.spring.profile.DevEnvironment;
import com.chan.study.spring.profile.Environment;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Optional;

@ContextConfiguration(locations = {"classpath:spring.xml"})
@RunWith(SpringJUnit4ClassRunner.class)
public class CtChanDemo {
    @Autowired
    DevEnvironment environment;

    @Test
    public void fun() {
        Optional.of("hahah").ifPresent(System.out::println);
    }

    @Test
    public void fun2() {
        environment.say();
    }
}
