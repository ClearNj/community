package life.majiang.community.community.dto;

import life.majiang.community.community.mode.User;
import lombok.Data;

@Data
public class CommentDto {
    private Long id;
    private Long parentId;
    private Integer type;
    private Long commentator;
    private Long gmtCreate;
    private Long gmtModified;
    private String content;
    private Long likeCount;
    private Integer commentCount;
    private User user;

}
