package com.chan.study.web;

import com.chan.study.common.CommonConstant;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.PreDestroy;

@Controller
@RequestMapping("/")
public class BaseController {
    public final String REGISTER_RESULT_ANSWER_BASE_WORDS = "@@1@@ ! @@2@@ @@3@@ register into WebApplication";

    @ResponseBody
    @RequestMapping(value = "/demo/annotation/${annotationType}/register-into-web-application/{result}", method = RequestMethod.GET)
    public String registerIntoWebApplication(@PathVariable("annotationType") String annotationType, @PathVariable("result") String result) {

        return dealResultAnswer(annotationType, result);
    }

    private String dealResultAnswer(String annotationType, String result) {
        String resultAnswer = REGISTER_RESULT_ANSWER_BASE_WORDS;
        if (CommonConstant.CHECK_RESULT.SUCCESS.sign.equals(result)) {
            resultAnswer = resultAnswer.replaceAll("@@1@@", "YES");
            resultAnswer = resultAnswer.replaceAll("@@3@@", "is");
        } else if (CommonConstant.CHECK_RESULT.FAIL.sign.equals(result)) {
            resultAnswer = resultAnswer.replaceAll("@@1@@", "Sorry");
            resultAnswer = resultAnswer.replaceAll("@@3@@", "is not");
        }
        return resultAnswer.replaceAll("@@2@@", annotationType);
    }

    @PreDestroy
    public void destory() {
        //清除redis缓存
    }
}
