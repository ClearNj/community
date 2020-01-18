package life.majiang.community.community.dto;

import life.majiang.community.community.mode.User;
import lombok.Data;

@Data
public class QuestionDto {
    private int id;
    private String title;
    private String description;
    private long gmtCreate;
    private long gmtModified;
    private int creator;
    private int commentCount;
    private int viewCount;
    private int likeCount;
    private String tag;
    private User user;
}
