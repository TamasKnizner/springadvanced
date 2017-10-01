package com.epam.tamasknizner.springadvancedtraining;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class IndexController {

    @RequestMapping("/")
    public String index(final Model model) {
        model.addAttribute("latestProduct", new Product("https://ipon.hu/webshop/product/product/1502382", "SONY Playstation 4 Pro 1TB"));
        model.addAttribute("user", "Tamas Knizner");
        return "index";
    }

}
