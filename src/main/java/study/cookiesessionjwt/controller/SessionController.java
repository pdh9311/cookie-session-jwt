package study.cookiesessionjwt.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.SessionAttribute;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@RestController
public class SessionController {

    @GetMapping("/sessionC1")
    public String sessionC1(HttpServletRequest req) {
        HttpSession session = req.getSession();
        session.setAttribute("sessionC1", "Hello!" );
        return "sessionC1";
    }

    @GetMapping("/sessionC2")
    public String sessionC2(HttpSession session) {
        session.setAttribute("sessionC2", "bye!" );
        return "sessionC2";
    }

    @GetMapping("/sessionC3")
    public String sessionC3(HttpSession session) {
        session.setAttribute("sessionC3", "and then!" );
        return "sessionC3";
    }

    @GetMapping("/sessionR1")
    public String sessionR1(HttpServletRequest req) {
        HttpSession session = req.getSession(false);
        System.out.println(session.getAttribute("sessionC1"));
        return "sessionR1";
    }

    @GetMapping("/sessionR2")
    public String sessionR2(@SessionAttribute(value = "sessionC1", required = false) String sessionValue) {
        System.out.println("sessionValue = " + sessionValue);
        return "sessionR2";
    }

}
