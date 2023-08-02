package hello.springmvc.basic.response;

import hello.springmvc.basic.HelloData;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @Controller 대신에 @RestController 애노테이션을 사용하면 해당 컨트롤러(전체 메서드)에 모두 @ResponseBody 가 적용
 * 뷰 템플릿을 사용하는 것이 아니라, HTTP 메시지 바디에 직접 데이터를 입력
 * 이름 그대로 Rest API(HTTP API)를 만들 때 사용하는 컨트롤러

 * @RestController -> @Controller + @ResponseBody
 */
@Slf4j
@Controller
public class ResponseBodyController {

    /**
     * 서블릿을 직접 다룰 때 처럼
     * HttpServletResponse 객체를 통해서 HTTP 메시지 바디에 직접 ok 응답 메시지를 전달
     */
    @GetMapping("/response-body-string-v1")
    public void responseBodyV1(HttpServletResponse response) throws IOException {
        response.getWriter().write("ok!");
    }

    /**
     * HttpEntity, ResponseEntity(Http Status 추가)
     * ResponseEntity 는 HttpEntity 를 상속 받아 HTTP 메시지의 헤더, 바디 정보를 가지고 있음
     * ResponseEntity 는 추가로 HTTP 응답 코드를 설정 가능(HttpStatus.CREATED -> 201 응답)
     */
    @GetMapping("/response-body-string-v2")
    public ResponseEntity<String> responseBodyV2() {
        return new ResponseEntity<>("ok!!", HttpStatus.OK);
    }

    /**
     * @ResponseBody 를 사용하면 view 를 사용하지 않고, HTTP 메시지 컨버터를 통해서 HTTP 메시지를 직접 입력 가능
     * ResponseEntity 도 동일한 방식으로 동작
     */
    @ResponseBody
    @GetMapping("/response-body-string-v3")
    public String responseBodyV3() {
        return "ok!!!";
    }

    /**
     * ResponseEntity 를 반환 -> HTTP 메시지 컨버터를 통해서 JSON 형식으로 변환되어서 반환
     */
    @GetMapping("/response-body-json-v1")
    public ResponseEntity<HelloData> responseBodyJsonV1() {
        HelloData helloData = new HelloData();
        helloData.setUsername("userA");
        helloData.setAge(20);

        return new ResponseEntity<>(helloData, HttpStatus.OK);
    }

    /**
     * ResponseEntity 는 HTTP 응답 코드 설정가능, @ResponseBody 를 사용랄 경우 설정하기 까다로움
     * @ResponseStatus(HttpStatus.OK) 애노테이션을 사용하면 응답 코드를 쉽게 설정가능
     * 하지만 어노테이션이기 때문에 응답 코드를 동적으로 변경할 수는 없음 -> 조건에 따라 동적으로 변경하기 위해서는 ResponseEntity 사용
     */
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    @GetMapping("/response-body-json-v2")
    public HelloData responseBodyJsonV2() {
        HelloData helloData = new HelloData();
        helloData.setUsername("userB");
        helloData.setAge(20);

        return helloData;
    }
}
