package com.zjc.blog.vo;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.org.apache.xerces.internal.util.Status;
import com.zjc.blog.enums.StateCodeMsg;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Serializable;

/**
 * 统一响应结果
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResponseResult implements Serializable {

    private static final long serialVersionUID = 7857767361428548395L;
    /**
     * 自定义状态码
     */
    private int code;
    /**
     * 响应提示信息
     */
    private String msg;
    /**
     * 响应数据
     */
    private Object data;

    /**
     * 返回错误状态
     * @param state
     * @param data
     * @return
     */
    public static ResponseResult fail(StateCodeMsg state, Object data) {
        ResponseResult result = new ResponseResult();
        result.setCode(state.getCode());
        result.setMsg(state.getMsg());
        result.setData(data);
        return result;
    }

    /**
     * 返回成功状态
     * @param data
     * @return
     */
    public static ResponseResult success(Object data) {
        ResponseResult result = new ResponseResult();
        result.setCode(StateCodeMsg.SUCCESS.getCode());
        result.setMsg(StateCodeMsg.SUCCESS.getMsg());
        result.setData(data);
        return result;
    }

    /**
     * 强制输出成功结果，用于覆盖身份认证与授权过程的强制报错
     * @param response
     * @param res
     * @throws IOException
     */
    public static void outputSuccessResult(HttpServletResponse response, ResponseResult res) throws IOException {
        // 配置响应头信息
        response.setContentType("text/json; charset=utf-8");
        response.setStatus(HttpStatus.OK.value());
        // 写入响应信息
        ServletOutputStream outputStream = response.getOutputStream();
        outputStream.write(new ObjectMapper().writeValueAsString(res).getBytes("utf-8"));
        // 刷新并关闭输出流
        outputStream.flush();
        outputStream.close();
    }
}
