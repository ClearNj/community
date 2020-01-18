package life.majiang.community.community.controller;

import life.majiang.community.community.Service.QuestionService;
import life.majiang.community.community.dto.QuestionDto;
import life.majiang.community.community.mapper.UserMapper;
import life.majiang.community.community.mode.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.List;


@Controller
public class IndexController {
    @Autowired
    UserMapper userMapper;

    @Autowired
    QuestionService questionService;

    @GetMapping("/")
    public String index(HttpServletRequest request,
                        Model model){
        Cookie[] cookies = request.getCookies();
        if(cookies!=null && cookies.length!=0){
            for (Cookie cookie : cookies) {
                if(cookie.getName().equals("token")) {
                    String token = cookie.getValue();
                    User user = userMapper.findByToken(token);
                    if(user!=null)
                        request.getSession().setAttribute("user",user);
                    break;
                }
            }
        }

        //列表数据获取
        List<QuestionDto> questionList = questionService.list();
        model.addAttribute("questions",questionList);
        return "index";
    }
}
