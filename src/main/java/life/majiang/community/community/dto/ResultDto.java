package life.majiang.community.community.dto;

import life.majiang.community.community.exception.CustomizeErrorCode;
import life.majiang.community.community.exception.CustomizeException;
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

    public static ResultDto errorof(CustomizeErrorCode errorCode) {
        return errorof(errorCode.getCode(),errorCode.getMessage());
    }

    public static ResultDto errorof(CustomizeException e){
        return errorof(e.getCode(),e.getMessage());
    }

    public static ResultDto okof(){
        ResultDto resultDto = new ResultDto();
        resultDto.setCode(200);
        resultDto.setMessage("请求成功");
        return resultDto;
    }
}
