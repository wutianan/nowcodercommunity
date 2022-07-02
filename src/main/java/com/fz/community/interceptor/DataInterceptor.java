package com.fz.community.interceptor;

import com.fz.community.entity.User;
import com.fz.community.util.HostHolder;
import com.fz.community.service.DataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 统计数据
 * @author zxf
 * @date 2022/7/1
 */
@Component
public class DataInterceptor implements HandlerInterceptor {

    @Autowired
    private DataService dataService;

    @Autowired
    private HostHolder hostHolder;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //统计UV
        String ip = request.getRemoteHost();
        dataService.recordUV(ip);

        //统计dau
        User user = hostHolder.getUser();
        if(user != null){
            dataService.recordDAU(user.getId());
        }

        return true;
    }
}
