package com.wsq.util;

import com.wsq.vo.ResultVO;

/**
 * @author wsq
 *         Created by Tp on 2019/5/18.
 */
public class ResultVOUtil {

    public static ResultVO success(Object o){
        ResultVO resultVO = new ResultVO();
        resultVO.setData(o);
        resultVO.setCode(0);
        resultVO.setMsg("success");
        return resultVO;
    }

    public static ResultVO error(Integer code, String msg){
        ResultVO resultVO = new ResultVO();
        resultVO.setCode(code);
        resultVO.setMsg(msg);
        return resultVO;
    }

    public static ResultVO success() {
        return success(null);
    }
}
