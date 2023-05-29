package com.webpower.myapp.web.rest;

import com.webpower.myapp.common.ResultResponse;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ClientForwardController {

    /**
     * Forwards any unmapped paths (except those containing a period) to the client {@code index.html}.
     * @return forward to client {@code index.html}.
     */
    @GetMapping(value = "/**/{path:[^\\.]*}")
    public String forward() {
        ResultResponse resultResponse = new ResultResponse<>(false, HttpStatus.OK,"成功",null);
        return "forward:/";
    }
}
