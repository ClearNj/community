package life.majiang.community.community.dto;

import lombok.Data;

import javax.servlet.http.PushBuilder;

@Data
public class ResultDto {
    private Integer code;
    private String message;

    public static ResultDto errorof(Integer code,String message){
        ResultDto resultDto = new ResultDto();
        resultDto.setCode(code);
        resultDto.setMessage(message);
        return resultDto;
    }
}
