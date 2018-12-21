package cn.zhmj.zkexample.bus.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.zhmj.zkexample.vo.BaseVo;
import cn.zhmj.zkexample.vo.MessageVo;

@Controller
@RequestMapping("/test")
public class TestController {
	@RequestMapping(value = "/home", method = { RequestMethod.GET, RequestMethod.POST })
	@ResponseBody
	public BaseVo index() {
		return new MessageVo();
	}
}
