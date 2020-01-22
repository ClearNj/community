package life.majiang.community.community.controller;

import life.majiang.community.community.Service.QuestionService;
import life.majiang.community.community.dto.PageinationDto;
import life.majiang.community.community.mapper.UserMapper;
import life.majiang.community.community.mode.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

@Controller

public class ProfileController {
    @Autowired
    UserMapper userMapper;
    @Autowired
    QuestionService questionService;
        @RequestMapping("/profile/{action}")
        public String profile (@PathVariable(name = "action") String action,
                               HttpServletRequest request,
                               Model model,
                               @RequestParam(name = "page",defaultValue = "1") Integer page,
                               @RequestParam(name = "size",defaultValue = "5") Integer size){
            //验证登录状态
            Cookie[] cookies = request.getCookies();
            User user=null;
            if(cookies!=null && cookies.length!=0){
                for (Cookie cookie : cookies) {
                    if(cookie.getName().equals("token")) {
                        String token = cookie.getValue();
                        user = userMapper.findByToken(token);
                        if(user!=null)
                            request.getSession().setAttribute("user",user);
                        break;
                    }
                }
            }
            if (user==null){
                return "redirect:/";
            }
            if ("questions".equals(action)){
                model.addAttribute("section","questions");
                model.addAttribute("sectionName","我的提问");
            }else if ("replies".equals(action)){
                model.addAttribute("section","replies");
                model.addAttribute("sectionName","最新回复");
            }
            //查询个人发布
            PageinationDto pageinationDto = questionService.list(user.getId(), page, size);
            model.addAttribute("pageination",pageinationDto);
            return "profile";
        }
}
