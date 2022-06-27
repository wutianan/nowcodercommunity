package com.fz.newcoder.community.controller.advice;

import com.fz.newcoder.community.util.CommunityUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * 异常配置类，可以用于同一处理异常，而不用在控制层加入任何代码
 * @author zxf
 * @date 2022/6/26
 */
@ControllerAdvice(annotations = Controller.class)
public class ExceptionAdvice {

    private static final Logger logger = LoggerFactory.getLogger(ExceptionAdvice.class);

    @ExceptionHandler({Exception.class})
    public void handleException(Exception e, HttpServletResponse response, HttpServletRequest request) throws IOException {
        logger.error("服务器发生异常"+e.getMessage());
        for(StackTraceElement element : e.getStackTrace()) {
            logger.error(element.toString());
        }
        //判断是不是异步请求，是，只是输出错误，如果是请求网页，发生错误时要返回到错误页面
        String header = request.getHeader("x-requested-with");
        if ("XMLHttpRequest".equals(header)){
            response.setContentType("application/plain;charset=utf-8");
            PrintWriter writer = response.getWriter();
            writer.write(CommunityUtil.getJSONString(1,"服务器发生异常"));
        }else{
            response.sendRedirect(request.getContextPath() + "/error");
        }
    }
}
