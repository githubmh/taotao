package com.taotao.sso.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 登录和注册页面的controller
 * @author Administrator
 *
 */
@Controller
public class PageController {
	@RequestMapping("/page/register")
	public String shouRegister(){
		return "register";
	}
	@RequestMapping("/page/login")
	public String shouLogin(String url,Model model){
		model.addAttribute("redirect", url);
		return "login";
	}
}
