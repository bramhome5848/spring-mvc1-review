package hello.springmvc.basic.request;

import com.fasterxml.jackson.databind.ObjectMapper;
import hello.springmvc.basic.HelloData;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

/**
 * {"username":"hello", "age":20}
 * content-type: application/json
 */
@Slf4j
@Controller
public class RequestBodyJsonController {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @PostMapping("/request-body-json-v1")
    public void requestBodyJsonV1(HttpServletRequest request, HttpServletResponse response) throws IOException {
        ServletInputStream inputStream = request.getInputStream();
        String messageBody = StreamUtils.copyToString(inputStream, StandardCharsets.UTF_8);
        log.info("messageBody = {}", messageBody);

        //문자 형태인 JSON 데이터를 Jackson 라이브러리인 objectMapper 를 사용해서 자바 객체로 변환
        HelloData data = objectMapper.readValue(messageBody, HelloData.class);
        log.info("username = {}", data.getUsername());
        log.info("age = {}", data.getAge());

        response.getWriter().write("ok");
    }

    /**
     * @RequestBody
     - HttpMessageConverter 사용 -> StringHttpMessageConverter 적용

     * @ResponseBody
     - 모든 메서드에 @ResponseBody 적용
     - 메시지 바디 정보 직접 반환(view 조회X)
     - HttpMessageConverter 사용 -> StringHttpMessageConverter 적용
     */
    @ResponseBody
    @PostMapping("/request-body-json-v2")
    public String requestBodyJsonV2(@RequestBody String messageBody) throws IOException {
        HelloData data = objectMapper.readValue(messageBody, HelloData.class);
        log.info("username = {}", data.getUsername());
        log.info("age = {}", data.getAge());

        return "ok";
    }

    /**
     * @RequestBody 생략 불가능(생략시 @ModelAttribute 가 적용되어 버림)
     * HttpMessageConverter 사용 -> MappingJackson2HttpMessageConverter (content-type: application/json)

     * HttpEntity , @RequestBody 를 사용하면
     * HTTP 메시지 컨버터가 HTTP 메시지 바디의 내용을 우리가 원하는 문자나 객체 등으로 변환

     * 스프링은 @ModelAttribute , @RequestParam 생략시 다음과 같은 규칙을 적용
     * String , int , Integer 같은 단순 타입 -> @RequestParam
     * 나머지 -> @ModelAttribute (argument resolver 로 지정해둔 타입 제외)
     */
    @ResponseBody
    @PostMapping("/request-body-json-v3")
    public String requestBodyJsonV3(@RequestBody HelloData data) {
        log.info("username = {}", data.getUsername());
        log.info("age = {}", data.getAge());

        return "ok";
    }

    @ResponseBody
    @PostMapping("/request-body-json-v4")
    public String requestBodyJsonV4(HttpEntity<HelloData> httpEntity) {
        HelloData data = httpEntity.getBody();
        log.info("username = {}", data.getUsername());
        log.info("age = {}", data.getAge());

        return "ok";
    }

    /**
     * @RequestBody 생략 불가능(생략시 @ModelAttribute 가 적용되어 버림)
     * HttpMessageConverter 사용 -> MappingJackson2HttpMessageConverter (content-type: application/json)

     * @ResponseBody 적용
     * - 메시지 바디 정보 직접 반환(view 조회X)
     * - HttpMessageConverter 사용 -> MappingJackson2HttpMessageConverter 적용 (Accept: application/json)

     * @RequestBody 요청 : JSON 요청 -> HTTP 메시지 컨버터 -> 객체
     * @ResponseBody 응답 : 객체 -> HTTP 메시지 컨버터 -> JSON 응답
     */
    @ResponseBody
    @PostMapping("/request-body-json-v5")
    public HelloData requestBodyJsonV5(@RequestBody HelloData data) {
        log.info("username = {}", data.getUsername());
        log.info("age = {}", data.getAge());

        return data;
    }
}
