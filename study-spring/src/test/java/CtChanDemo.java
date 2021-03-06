import com.chan.study.bean.DemoBean;
import com.chan.study.bean.OfflineBean;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Optional;

@ContextConfiguration(locations = {"classpath:spring.xml"})
@RunWith(SpringJUnit4ClassRunner.class)
public class CtChanDemo {
    @Resource
    private BeanFactory beanFactory;

    @Resource
    private OfflineBean offlineBeanInFullConfiguration;

    @Resource
    private OfflineBean offlineBeanLite;

    @Test
    public void fun() {
        Optional.of("hahah").ifPresent(System.out::println);
    }

    @Test
    public void fun3() {
        //beanFactory API 使用
        beanFactory.containsBean("demoBean");
        beanFactory.isSingleton("demoBean");
        beanFactory.isPrototype("demoBean");
        beanFactory.isTypeMatch("demoBean", DemoBean.class);
        beanFactory.getType("demoBean");
        for (String demoBean : beanFactory.getAliases("demoBean")) {
            System.out.println(demoBean);
        }
    }

    @Test
    public void fun4() throws UnsupportedEncodingException {
        System.out.println(URLEncoder.encode("http://10.40.2.115:8080/weChat/publicNumber/openId", "utf-8"));
    }


    @Test
    public void differAtBeanInAtConfirgurationAndAtCompment() {
        System.out.println(offlineBeanInFullConfiguration);
        System.out.println(offlineBeanLite);
    }
}
