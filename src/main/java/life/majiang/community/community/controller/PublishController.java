package life.majiang.community.community.controller;

import life.majiang.community.community.mapper.PublishMapper;
import life.majiang.community.community.mapper.UserMapper;
import life.majiang.community.community.mode.Question;
import life.majiang.community.community.mode.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

@Controller
public class PublishController {

    @Autowired
    UserMapper userMapper;

    @Autowired
    PublishMapper publishMapper;

    @GetMapping("/publish")
    public String publish(){
        return "publish";
    }

    @PostMapping("/publish")
        public String doPublish(@RequestParam(value = "title", required = false) String title,
                                @RequestParam(value = "description", required = false) String description,
                                @RequestParam(value = "tag",required = false) String tag,
                                HttpServletRequest request,
                                Model model) {
        model.addAttribute("title", title);
        model.addAttribute("description", description);
        model.addAttribute("tag", tag);
        //内容验证
        if (title == null || title == "") {
            model.addAttribute("error", "标题不能为空");
            return "publish";
        }
        if (description == null || description == "") {
            model.addAttribute("error", "内容不能为空");
            return "publish";
        }
        if (tag == null || tag == "") {
            model.addAttribute("error", "标签不能为空");
            return "publish";
        }
        //登录状态验证
        Cookie[] cookies = request.getCookies();
        User user = null;
        for (Cookie cookie : cookies) {
            if (cookie.getName().equals("token")) {
                String token = cookie.getValue();
                user = userMapper.findByToken(token);
                if (user != null)
                    request.getSession().setAttribute("user", user);
                break;
            }
        }
        if (user == null) {
            model.addAttribute("error","用户未登录");
            return "publish";
        }
        //添加发布信息到数据库
        Question question = new Question();
        question.setCreator(user.getId());
        question.setDescription(description);
        question.setTag(tag);
        question.setTitle(title);
        question.setGmtCreate(System.currentTimeMillis());
        question.setGmtModified(question.getGmtCreate());
        publishMapper.create(question);
        return "redirect:/";
    }
}
