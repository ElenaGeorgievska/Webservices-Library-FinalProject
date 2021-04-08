package com.example.library;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ViewController {
	
	@GetMapping("/")
	public String welcome() {
		return "index";
		
	}
	
	@GetMapping("/privacy-policy")
	public String privacyPolicyPage() {
		return "privacy-policy";
	}
	
	@GetMapping("/terms")
	public String termsAndConditionsPage() {
		return "terms-and-conditions";
	}

}
