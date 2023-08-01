package hello.springmvc.basic.request;

import hello.springmvc.basic.HelloData;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 클라이언트 -> 서버로 요청 데이터를 전달할 때 주로 3가지 방법 사용
 1. GET - 쿼리 파라미터
 - 메시지 바디 없이, URL 의 쿼리 파라미터에 데이터를 포함해서 전달
 - 검색, 필터, 페이징 등에서 많이 사용하는 방식

 2. POST - HTML FORM
 - content-type:application/x-www-form-urlencoded
 - 메시지 바디에 쿼리 파라미터 형식으로 전달
 - 회원 가입, 상품 주문, HTML form 사용

 3. HTTP message body 에 데이터를 직접 담아 요청
 - HTTP API 에서 주로 사용, JSON, XML, TEXT
 - 데이터 형식은 주로 JSON 사용
 - POST, PUT, PATCH
 */
@Slf4j
@Controller
public class RequestParamController {

    /**
     * 요청 파라미터 조회
     * GET 쿼리 파라미터 전송방식과, POST HTML Form 전송방식 둘다 형식이 같으므로 구분없이 조회할 수 있음
     * http://localhost:8080/basic/hello-form.html 에서 전송 확인
     */
    @RequestMapping("/request-param-v1")
    public void requestParamV1(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String username = request.getParameter("username");
        int age = Integer.parseInt(request.getParameter("age"));
        log.info("username = {}", username);
        log.info("age = {}", age);

        response.getWriter().write("ok");   //반환 타입이 없으면서 응답에 값을 직접 집어넣으면, view 조회를 하지 않음
    }

    /**
     * @RequestParam 사용 - 파라미터 이름으로 바인딩
     * @ResponseBody 추가 - View 조회를 무시하고, HTTP message body 에 직접 해당 내용 입력
     */
    @ResponseBody
    @RequestMapping("/request-param-v2")
    public String requestParamV2(@RequestParam("username") String memberName,
                                 @RequestParam("age") int memberAge) {
        log.info("username = {}", memberName);
        log.info("age = {}", memberAge);
        return "ok";
    }

    /**
     * @RequestParam 사용
     * HTTP 파라미터 이름이 변수 이름과 같으면 @RequestParam(name="xx") 생략 가능
     */
    @ResponseBody
    @RequestMapping("/request-param-v3")
    public String requestParamV3(@RequestParam String username,
                                 @RequestParam int age) {
        log.info("username = {}", username);
        log.info("age = {}", age);
        return "ok";
    }

    /**
     * @RequestParam 사용
     * String, Integer 등의 단순 타입이면 @RequestParam 도 생략 가능
     * @RequestParam 이 있으면 요청 파라미터에서 데이터를 읽는다는 것을 명확하게 알 수 있음
     */
    @ResponseBody
    @RequestMapping("/request-param-v4")
    public String requestParamV4(String username, int age) {
        log.info("username = {}", username);
        log.info("age = {}", age);
        return "ok";
    }

    /**
     * @RequestParam 사용
     * required -> 파라미터 필수여부, default true
     * username -> 필수값, null 인 경우 예외발생, "" 빈 문자의 경우 경우는 통과
     * age -> 필수 값은 아니지만 int 형인 경우 null 을 입력할 수 없음 -> Integer 로 변경(또는 defaultValue 사용)
     */
    @ResponseBody
    @RequestMapping("/request-param-required")
    public String requestParamRequired(@RequestParam(required = true) String username,
                                       @RequestParam(required = false) Integer age) {
        log.info("username = {}", username);
        log.info("age = {}", age);
        return "ok";
    }

    /**
     * @RequestParam 사용
     * defaultValue -> null, "" 빈 문자 둘 다 적용
     * /request-param?username= -> username == ""
     * /request-param -> username == null
     */
    @ResponseBody
    @RequestMapping("/request-param-default")
    public String requestParamDefault(@RequestParam(required = true, defaultValue = "guest") String username,
                                      @RequestParam(required = false, defaultValue = "-1") Integer age) {
        log.info("username = {}", username);
        log.info("age = {}", age);
        return "ok";
    }

    /**
     * @RequestParam 사용
     * Map, MultiValueMap
     * Map(key=value) - Key, Value
     * MultiValueMap(key=[value1, value2, ...] ex) (key=userIds, value=[id1, id2]) - Key, List
     */
    @ResponseBody
    @RequestMapping("/request-param-map")
    public String requestParamMap(@RequestParam MultiValueMap<String, Object> paramMap) {
        log.info("username = {}", paramMap.get("username"));
        log.info("age = {}", paramMap.get("age"));
        return "ok";
    }

    /**
     * @ModelAttribute 사용
     * Spring MVC 는 @ModelAttribute 가 있으면
     1. HelloData 객체를 생성
     2. 요청 파라미터의 이름으로 HelloData 객체의 프로퍼티를 찾음
     3. 해당 프로퍼티의 setter 를 호출해서 파라미터 값을 입력(바인딩)
     */
    @ResponseBody
    @RequestMapping("/model-attribute-v1")
    public String modelAttributeV1(@ModelAttribute HelloData helloData) {
        log.info("username = {}", helloData.getUsername());
        log.info("age = {}", helloData.getAge());
        return "ok";
    }

    /**
     * @ModelAttribute 생략 가능
     * String, int 같은 단순 타입 = @RequestParam
     * argument resolver 로 지정해둔 타입 외 @ModelAttribute 사용
     */
    @ResponseBody
    @RequestMapping("/model-attribute-v2")
    public String modelAttributeV2(HelloData helloData) {
        log.info("username = {}", helloData.getUsername());
        log.info("age = {}", helloData.getAge());
        return "ok";
    }
}
