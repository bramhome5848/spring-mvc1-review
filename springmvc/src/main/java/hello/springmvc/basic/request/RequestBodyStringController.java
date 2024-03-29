package hello.springmvc.basic.request;

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
import java.io.InputStream;
import java.io.Writer;
import java.nio.charset.StandardCharsets;

/**
 * HTTP message body 에 데이터를 직접 담아서 요청
 * 요청 파라미터와 다르게, HTTP 메시지 바디를 통해 데이터가 직접 데이터가 넘어오는 경우
 -> @RequestParam , @ModelAttribute 를 사용할 수 없음
 * 물론 HTML Form 형식으로 전달되는 경우는 요청 파라미터로 인정되어 @RequestParam , @ModelAttribute 사용 가능
 */
@Slf4j
@Controller
public class RequestBodyStringController {

    @PostMapping("/request-body-string-v1")
    public void requestBodyString(HttpServletRequest request, HttpServletResponse response) throws IOException {
        ServletInputStream inputStream = request.getInputStream();
        String messageBody = StreamUtils.copyToString(inputStream, StandardCharsets.UTF_8);
        log.info("messageBody = {}", messageBody);

        response.getWriter().write("ok");
    }

    /**
     * InputStream(Reader): HTTP 요청 메시지 바디의 내용을 직접 조회
     * OutputStream(Writer): HTTP 응답 메시지의 바디에 직접 결과 출력
     * 서블릿에 대한 코드가 필요하지 않음
     */
    @PostMapping("/request-body-string-v2")
    public void requestBodyStringV2(InputStream inputStream, Writer responseWriter) throws IOException {
        String messageBody = StreamUtils.copyToString(inputStream, StandardCharsets.UTF_8);
        log.info("messageBody = {}", messageBody);

        responseWriter.write("ok");
    }

    /**
     * HttpEntity
     - 메시지 헤더, 바디 정보를 직접 조회
     - 요청 파라미터를 조회하는 기능과 관계 없음 (@RequestParam X, @ModelAttribute X)
     - 요청 주소 및 queryString 은 http 의 start-line 에 존재
     - HttpMessageConverter 사용 -> StringHttpMessageConverter 적용

     * 응답시에서도 HttpEntity 사용 가능
     - 메시지 바디 정보 직접 반환(view 조회X)
     - HttpMessageConverter 사용 -> StringHttpMessageConverter 적용
     */
    @PostMapping("/request-body-string-v3")
    public HttpEntity<String> requestBodyStringV3(HttpEntity<String> httpEntity) {
        String messageBody = httpEntity.getBody();
        log.info("messageBody = {}", messageBody);

        return new HttpEntity<>("ok");
    }

    /**
     * @RequestBody
     * - 메시지 바디 정보를 직접 조회
     * - 요청 파라미터를 조회하는 기능과 관계 없음 (@RequestParam X, @ModelAttribute X)
     * - HttpMessageConverter 사용 -> StringHttpMessageConverter 적용

     * @ResponseBody
     * - 응답 결과를 HTTP 메시지 바디에 직접 담아서 전달 (view 조회X)
     * - HttpMessageConverter 사용 -> StringHttpMessageConverter 적용

     * 요청 파라미터 VS HTTP 메시지 바디
     * 요청 파라미터를 조회하는 기능 : @RequestParam, @ModelAttribute
     * HTTP 메시지 바디를 직접 조회하는 기능 : @RequestBody

     * 참고
     - 헤더 정보가 필요한 경우 HttpEntity 를 사용하거나 @RequestHeader 를 사용
     - @RequestBody - 클라이언트가 전송하는 Json(application/json) 형태의 HTTP Body 내용을 Java Object 로 변환시켜주는 역할
     - 따라서 HTTP Form 전송(application/x-www-form-urlencoded) 은 @RequestBody 를 사용할 수 없음
     */
    @ResponseBody
    @PostMapping("/request-body-string-v4")
    public String requestBodyStringV4(@RequestBody String messageBody) {
        log.info("messageBody = {}", messageBody);
        return "ok";
    }
}
