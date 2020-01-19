package life.majiang.community.community.Service;

import life.majiang.community.community.dto.PageinationDto;
import life.majiang.community.community.dto.QuestionDto;
import life.majiang.community.community.mapper.QuestionMapper;
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
    QuestionMapper questionMapper;
    public PageinationDto list(Integer page, Integer size) {
        //size*(page-1)
        Integer offset = size*(page-1);
        List<Question> questions = questionMapper.list(offset,size);
        List<QuestionDto> questionDtoList = new ArrayList<>();
        PageinationDto pageinationDto = new PageinationDto();
        for(Question question : questions){
            User user = userMapper.findById(question.getCreator());
            QuestionDto questionDto = new QuestionDto();
            BeanUtils.copyProperties(question,questionDto);
            questionDto.setUser(user);
            questionDtoList.add(questionDto);
        }
        pageinationDto.setQuestions(questionDtoList);
        Integer totalCount = questionMapper.count();
        pageinationDto.setPageination(totalCount,page,size);
        return pageinationDto;
    }
}
