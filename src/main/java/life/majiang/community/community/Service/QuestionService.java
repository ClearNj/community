package life.majiang.community.community.Service;

import life.majiang.community.community.dto.PageinationDto;
import life.majiang.community.community.dto.QuestionDto;
import life.majiang.community.community.dto.QuestionQueryDTO;
import life.majiang.community.community.exception.CustomizeErrorCode;
import life.majiang.community.community.exception.CustomizeException;
import life.majiang.community.community.mapper.QuestionExtMapper;
import life.majiang.community.community.mapper.QuestionMapper;
import life.majiang.community.community.mapper.UserMapper;
import life.majiang.community.community.mode.Question;
import life.majiang.community.community.mode.QuestionExample;
import life.majiang.community.community.mode.User;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.StringJoiner;
import java.util.stream.Collectors;

@Service
public class QuestionService {
    @Autowired
    UserMapper userMapper;
    @Autowired
    QuestionMapper questionMapper;
    @Autowired
    QuestionExtMapper questionExtMapper;
    //首页查询
    public PageinationDto list(String search,String tag,Integer page, Integer size) {
        //分割搜索内容
        if(StringUtils.isNoneBlank(search)){
            String[] tags = StringUtils.split(search," ");
            search = Arrays.stream(tags).collect(Collectors.joining("|"));
        }
        //分页，size*(page-1)
        Integer offset = size*(page-1);
        QuestionQueryDTO questionQueryDTO = new QuestionQueryDTO();
        questionQueryDTO.setPage(offset);
        questionQueryDTO.setSize(size);
        questionQueryDTO.setSearch(search);
        //添加搜索标签
        if (StringUtils.isNotBlank(tag)) {
            tag = tag.replace("+", "").replace("*", "").replace("?", "");
            questionQueryDTO.setTag(tag);
        }
        List<Question> questions = questionExtMapper.selectBySearch(questionQueryDTO);
        List<QuestionDto> questionDtoList = new ArrayList<>();
        PageinationDto pageinationDto = new PageinationDto();
        for(Question question : questions){
            User user = userMapper.selectByPrimaryKey(question.getCreator());
            QuestionDto questionDto = new QuestionDto();
            BeanUtils.copyProperties(question,questionDto);
            questionDto.setUser(user);
            questionDtoList.add(questionDto);
        }
        pageinationDto.setData(questionDtoList);
       //查询总数
        Integer totalCount = questionExtMapper.countBySearch(questionQueryDTO);
        pageinationDto.setPageination(totalCount,page,size);
        return pageinationDto;
    }
    //个人页面查询
    public PageinationDto list(Long userId, Integer page, Integer size) {
        //size*(page-1)
        Integer offset = size*(page-1);
        QuestionExample questionExample = new QuestionExample();
        questionExample.createCriteria().andCreatorEqualTo(userId);
        List<Question> questions = questionMapper.selectByExampleWithRowbounds(questionExample, new RowBounds(offset, size));
        List<QuestionDto> questionDtoList = new ArrayList<>();
        PageinationDto pageinationDto = new PageinationDto();
        for(Question question : questions){
            User user = userMapper.selectByPrimaryKey(question.getCreator());
            QuestionDto questionDto = new QuestionDto();
            BeanUtils.copyProperties(question,questionDto);
            questionDto.setUser(user);
            questionDtoList.add(questionDto);
        }
        pageinationDto.setData(questionDtoList);
        QuestionExample questionExample1 = new QuestionExample();
        questionExample.createCriteria().andCreatorEqualTo(userId);
        Integer totalCount =(int) questionMapper.countByExample(questionExample1);
        pageinationDto.setPageination(totalCount,page,size);
        return pageinationDto;
    }

    public QuestionDto getById(Long id) {
        Question question = questionMapper.selectByPrimaryKey(id);
        if (question==null){
            throw new CustomizeException(CustomizeErrorCode.QUESTION_NOT_FOUND);
        }
        QuestionDto questionDto = new QuestionDto();
        BeanUtils.copyProperties(question,questionDto);
        User user = userMapper.selectByPrimaryKey(question.getCreator());
        questionDto.setUser(user);
        return questionDto;
    }

    public void createOrUpdate(Question question) {
        if (question.getId()!=null){
            //更新
            Question updateQuestion = new Question();
            updateQuestion.setTitle(question.getTitle());
            updateQuestion.setTag(question.getTag());
            updateQuestion.setDescription(question.getDescription());
            updateQuestion.setGmtModified(System.currentTimeMillis());
            QuestionExample questionExample = new QuestionExample();
            questionExample.createCriteria().andIdEqualTo(question.getId());
            int update = questionMapper.updateByExampleSelective(updateQuestion, questionExample);
            if (update !=1){
                throw new CustomizeException(CustomizeErrorCode.QUESTION_NOT_FOUND);
            }
        }else{
            //创建
            question.setGmtCreate(System.currentTimeMillis());
            question.setGmtModified(question.getGmtCreate());
            question.setViewCount(0);
            question.setLikeCount(0);
            question.setCommentCount(0);
            questionMapper.insert(question);

        }
    }
    //增加阅读数
    public void incView(Long id) {
        Question question = new Question();
        question.setId(id);
        question.setViewCount(1);
        questionExtMapper.incView(question);

    }

    //关联标签
    public List<QuestionDto> selectRelated(QuestionDto questionDto) {
        //分割标签
        if(StringUtils.isBlank(questionDto.getTag())){
            return new ArrayList<>();
        }
        String[] tags = StringUtils.split(questionDto.getTag(),",");
        String regexpTag = Arrays.stream(tags).collect(Collectors.joining("|"));
        Question question = new Question();
        question.setId(questionDto.getId());
        question.setTag(regexpTag);

        List<Question> questions = questionExtMapper.selectRelated(question);
        List<QuestionDto> questionDtos = questions.stream().map(q -> {
            QuestionDto questionDto1 = new QuestionDto();
            BeanUtils.copyProperties(q, questionDto1);
            return questionDto1;
        }).collect(Collectors.toList());
        return questionDtos;
    }
}
