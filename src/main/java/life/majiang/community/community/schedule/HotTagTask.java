package life.majiang.community.community.schedule;

import life.majiang.community.community.mapper.QuestionMapper;
import life.majiang.community.community.mode.Question;
import life.majiang.community.community.mode.QuestionExample;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Component
@Slf4j
public class HotTagTask {
    @Autowired
    private QuestionMapper questionMapper;

    @Scheduled(fixedRate = 10000)
    //@Scheduled(cron = "0 0 1 * * *")
    public void hotTagSchedule(){
        int offset=0;
        int limit=5;
        log.info("hotSchedule start {}",new Date());
        List<Question> list = new ArrayList<>();
        while (offset==0 || list.size()==limit){
            list = questionMapper.selectByExampleWithRowbounds(new QuestionExample(),new RowBounds(offset,limit));
            for (Question question : list){
                log.info("list question : {}",question.getId());
            }
            offset += limit;
        }
        log.info("hotSchedule end {}",new Date());
    }
}
