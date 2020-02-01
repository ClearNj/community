package life.majiang.community.community.controller;

import life.majiang.community.community.dto.CommentDto;
import life.majiang.community.community.mapper.CommentMapper;
import life.majiang.community.community.mode.Comment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class CommentController {
    @Autowired
    CommentMapper commentMapper;

    @ResponseBody
    @RequestMapping(value = "/comment",method = RequestMethod.POST)
    public Object psot(@RequestBody CommentDto commentDto){
        Comment comment = new Comment();
        comment.setContent(commentDto.getContent());
        comment.setCommentator(1);
        comment.setGmtCreate(System.currentTimeMillis());
        comment.setGmtModified(System.currentTimeMillis());
        comment.setType(commentDto.getType());
        comment.setParentId(commentDto.getParentId());
        commentMapper.insert(comment);
        return null;
    }
}
