package study.cookiesessionjwt.controller;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.Claim;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.Map;

@RestController
public class JwtController {

    private static final String SECRET_KEY = "ttFU7bFArj3jNcQDbl19KWVnuF6y3OyA";

    @GetMapping("/jwtCreate")
    public ResponseEntity<String> jwtCreate() {
        String jwt = JWT.create()
                .withSubject("사용자 정보 토큰")
                .withExpiresAt(new Date(System.currentTimeMillis() + (1000 * 60 * 10)))
                .withClaim("이름", "홍길동")
                .withClaim("나이", "100")
                .sign(Algorithm.HMAC512(SECRET_KEY));

        ResponseCookie responseCookie = ResponseCookie.from("access_token", jwt)
                .maxAge(60 * 10)
                .httpOnly(true)
                .secure(true)
                .build();

        return ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE, responseCookie.toString())
                .body(jwt);
    }

    @GetMapping("/jwtRead")
    public ResponseEntity<String> jwtRead(@CookieValue("access_token") String accessToken) {
        Map<String, Claim> claims = JWT.require(Algorithm.HMAC512(SECRET_KEY))
                .build()
                .verify(accessToken)
                .getClaims();

        StringBuilder sb = new StringBuilder();
        for (Map.Entry<String, Claim> entry : claims.entrySet()) {
            sb.append(entry.getKey()).append(" : ").append(entry.getValue()).append("<br>");
        }

        // secret key 없이 복호화하는 메서드를 지원한다.
        Map<String, Claim> decode = JWT.decode(accessToken)
                .getClaims();
        System.out.println("decode = " + decode);

        return ResponseEntity.ok()
                .body(sb.toString());
    }


}
