package life.majiang.community.community.dto;

import lombok.Data;
/*
用于配置分页查询对象封装
 */
@Data
public class QuestionQueryDTO {
    private String search;
    private String tag;
    private Integer page;
    private Integer size;
}
