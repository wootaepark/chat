package com.example.chatting.chat.viewController;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ChattingViewController {

	@GetMapping("/")
	public String chat() {
		return "index";
	}
}
