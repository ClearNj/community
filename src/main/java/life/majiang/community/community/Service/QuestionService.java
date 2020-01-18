package life.majiang.community.community.Service;

import life.majiang.community.community.dto.QuestionDto;
import life.majiang.community.community.mapper.PublishMapper;
import life.majiang.community.community.mapper.UserMapper;
import life.majiang.community.community.mode.Question;
import life.majiang.community.community.mode.User;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class QuestionService {
    @Autowired
    UserMapper userMapper;
    @Autowired
    PublishMapper publishMapper;
    public List<QuestionDto> list() {
        List<Question> questions = publishMapper.list();
        List<QuestionDto> questionDtoList = new ArrayList<>();
        for(Question question : questions){
            User user = userMapper.findById(question.getCreator());
            QuestionDto questionDto = new QuestionDto();
            BeanUtils.copyProperties(question,questionDto);
            questionDto.setUser(user);
            questionDtoList.add(questionDto);
        }
        return questionDtoList;
    }
}
