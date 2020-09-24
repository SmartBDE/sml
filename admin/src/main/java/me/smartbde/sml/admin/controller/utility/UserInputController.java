package me.smartbde.sml.admin.controller.utility;

import me.smartbde.sml.admin.domain.model.*;
import me.smartbde.sml.admin.domain.repository.MySQLTextfileRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/utility")
public class UserInputController {
    @Autowired
    MySQLTextfileRepository mySQLTextfileRepository;

    @RequestMapping("/userinput")
    public String userInput(Model model) {
        UserInput userInput = new UserInput();
        model.addAttribute("userinput", userInput);
        return "utility/userinput";
    }

    @RequestMapping("/userinput/submit")
    public String userInputSubmit(@ModelAttribute(value="userinput") UserInput userInput, Model model) {
        Logger logger = LoggerFactory.getLogger(UserInputController.class);
        try {
            String[] list = userInput.getPath().split("/");
            String fileName = list[list.length - 1];

            BufferedReader in = new BufferedReader(new FileReader(userInput.getPath()));
            String str;
            while ((str = in.readLine()) != null) {
                Textfile file = new Textfile();
                file.setName(fileName);
                file.setText(str);
                mySQLTextfileRepository.save(file);
            }
        } catch (IOException e) {
            logger.info(e.toString());

            model.addAttribute("head", "执行结果");
            model.addAttribute("msg", "失败");
            model.addAttribute("url", "/utility/userinput");
            return "info";
        }

        model.addAttribute("head", "执行结果");
        model.addAttribute("msg", "成功");
        model.addAttribute("url", "/utility/userinput");
        return "info";
    }
}