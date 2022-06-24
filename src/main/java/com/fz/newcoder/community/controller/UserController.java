package com.fz.newcoder.community.controller;

import com.fz.newcoder.community.annotation.LoginRequired;
import com.fz.newcoder.community.entity.User;
import com.fz.newcoder.community.service.UserService;
import com.fz.newcoder.community.util.CommunityConstant;
import com.fz.newcoder.community.util.CommunityUtil;
import com.fz.newcoder.community.util.HostHolder;
import org.apache.catalina.Host;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.websocket.server.PathParam;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;

/**
 * @author zxf
 * @date 2022/6/15
 */
@Controller
@RequestMapping("/user")
public class UserController {

    @Value("${community.path.upload}")
    private String uploadPath;

    @Value("${community.path.domain}")
    private String domain;

    @Value("${server.servlet.context-path}")
    private String contextPath;

    @Autowired
    private HostHolder hostHolder;

    @Autowired
    private UserService userService;

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    /**
     * 跳转setting页面
     *
     * @return
     */
    @LoginRequired
    @RequestMapping(value = "/setting", method = RequestMethod.GET)
    public String getSettingPage() {
        return "/site/setting";
    }

    /**
     * 上传文件方法
     *
     * @param headerImage
     * @param model
     * @return
     */
    @LoginRequired
    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    public String uploadHeader(MultipartFile headerImage, Model model) {
        if (headerImage == null) {
            model.addAttribute("error", "您还没有选择图片");
            return "/site/setting";
        }
        String fileName = headerImage.getOriginalFilename();
        String suffix = fileName.substring(fileName.lastIndexOf("."));
        if (StringUtils.isBlank(suffix)) {
            model.addAttribute("error", "您上传的图片格式有误");
            return "/site/setting";
        }
        //生成随机文件名
        fileName = CommunityUtil.generateUUID() + suffix;
        //确定文件路径
        File dest = new File(uploadPath + "/" + fileName);
        try {
            headerImage.transferTo(dest);
        } catch (IOException e) {
            logger.error("上传文件失败" + e.getMessage());
            throw new RuntimeException("上传文件失败，服务器发生异常", e);
        }
        User user = hostHolder.getUser();
        // 更新当前用户的头像的路径(web访问路径)
        // http://localhost:8080/community/user/header/xxx.png
        String headerUrl = domain + contextPath + "/user/header/" + fileName;
        userService.updateHeader(user.getId(), headerUrl);
        return "redirect:/index";
    }


    /**
     * 获取图片
     * // http://localhost:8080/community/user/header/xxx.png
     * @param fileName
     * @param response
     */

    @RequestMapping(value = "/header/{fileName}", method = RequestMethod.GET)
    public void getHeader(@PathVariable("fileName") String fileName, HttpServletResponse response) {
        fileName = uploadPath + "/" + fileName;
        String suffix = fileName.substring(fileName.lastIndexOf("."));
        //响应图片
        response.setContentType("image/" + suffix);
        try (
                FileInputStream fileInputStream = new FileInputStream(fileName);
                OutputStream outputStream = response.getOutputStream();
        ) {
            byte[] bytes = new byte[1024];
            int b = 0;
            while ((b = fileInputStream.read(bytes)) != -1) {
                outputStream.write(bytes, 0, b);
            }
        } catch (IOException e) {
            logger.error("获取头像失败" + e.getMessage());
        }
    }

    /**
     * 修改密码
     * @param oldPassword 旧密码
     * @param newPassword 新密码
     * @param model 模型
     * @param ticket 用户登陆状态查询
     * @return
     */
    @RequestMapping(value = "/updatepassword",method = RequestMethod.POST)
    public String updatePassword(String oldPassword, String newPassword, Model model, @CookieValue("ticket") String ticket){
        User user = hostHolder.getUser();
        //user.setPassword(CommunityUtil.md5(user.getPassword() + user.getSalt()));
        if(!user.getPassword().equals(CommunityUtil.md5(oldPassword + user.getSalt()))){
            model.addAttribute("passwordMsg","原密码输入错误");
            return "/site/setting";
        }
        else if(oldPassword.equals(newPassword)){
            model.addAttribute("passwordMsg","修改后的密码和原密码一样");
            return "/site/setting";
        }
        //修改密码
        else {
            userService.updatePassword(CommunityUtil.md5(newPassword + user.getSalt()), user.getId());
            model.addAttribute("msg", "修改密码成功");
            model.addAttribute("target","/login");
            //退出登陆
            userService.logout(ticket);
            return "/site/operate-result";
        }
    }
}
