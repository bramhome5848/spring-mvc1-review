package hello.springmvc.basic.request;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpMethod;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Locale;

@Slf4j
@RestController
public class RequestHeaderController {

    @RequestMapping("/headers")
    public String headers(HttpServletRequest request,
                          HttpServletResponse response,
                          HttpMethod httpMethod,
                          Locale locale,
                          @RequestHeader MultiValueMap<String, String> headerMap,   //하나의 키에 여러값 받을 수 있음
                          @RequestHeader("host") String host,
                          @CookieValue(value = "myCookie", required = false) String cookie) {
        log.info("request = {}", request);        //request
        log.info("response = {}", response);      //response
        log.info("httpMethod = {}", httpMethod);  //HTTP 메서드
        log.info("locale = {}", locale);          //Locale 정보
        log.info("headerMap = {}", headerMap);    //모든 HTTP 헤더를 MultiValueMap 형식으로 조회
        log.info("header host = {}", host);       //특정 HTTP 헤더를 조회
        log.info("myCookie = {}", cookie);        //특정 쿠키를 조회

        return "ok";
    }
}
