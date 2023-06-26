package study.cookiesessionjwt.controller;

import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
public class CookieController {

    @GetMapping("cookieC1")
    public String cookieC1(HttpServletResponse res) {
        Cookie cookie = new Cookie("cookieC1", "1111");
        cookie.setMaxAge(60 * 60 * 24);
        cookie.setHttpOnly(true);
        cookie.setSecure(true);

        res.addCookie(cookie);
        return "cookieC1";
    }

    @GetMapping("cookieC2")
    public ResponseEntity<String> cookieC2() {
        ResponseCookie responseCookie = ResponseCookie.from("cookieC2", "2222")
                .maxAge(60 * 60 * 24)
                .httpOnly(true)
                .secure(true)
                .build();

        return ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE, responseCookie.toString())
                .body("cookieC2");
    }


    @GetMapping("cookieR1")
    public String cookieR1(HttpServletRequest req) {
        Cookie[] cookies = req.getCookies();
        for (Cookie cookie : cookies) {
            System.out.println(cookie.getName() + " : " + cookie.getValue());
        }
        return "cookieR1";
    }

    @GetMapping("cookieR2")
    public String cookieR2(@CookieValue(value = "cookieC1", required = false) String cookieC1Value) {
        System.out.println(cookieC1Value);
        return "cookieR2";
    }

}
