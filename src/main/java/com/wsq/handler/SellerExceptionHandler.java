package com.wsq.handler;

import com.wsq.config.ProjectURL;
import com.wsq.exception.SellException;
import com.wsq.exception.SellerAuthorizeException;
import com.wsq.util.ResultVOUtil;
import com.wsq.vo.ResultVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

/**
 * @author wsq
 * @date 2019/7/1 17:43
 */
@ControllerAdvice
public class SellerExceptionHandler {

    @Autowired
    private ProjectURL projectURL;

    // 拦截登录异常
    @ExceptionHandler(value = SellerAuthorizeException.class)
    public ModelAndView handlerException() {
        return new ModelAndView("redirect:".concat(projectURL.getWechatOpenAuthorize()).concat("/wechat/qrAuthorize")
                .concat("returnUrl=").concat(projectURL.getSell().concat("/login")));
    }

    /**
     * 异常处理
     */
    @ExceptionHandler(value = SellException.class)
    @ResponseBody
    public ResultVO handlerSellException(SellException e){
        return ResultVOUtil.error(e.getCode(), e.getMessage());

    }
}
