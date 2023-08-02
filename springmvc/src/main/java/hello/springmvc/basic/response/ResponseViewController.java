package hello.springmvc.basic.response;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * 스프링에서 응답 데이터를 만드는 3가지 방법
 1. 정적 리소스
 - 웹 브라우저에 정적인 HTML, CSS, JS 를 제공할 때는 정적 리소스 사용
 - 스프링 부트는 클래스 패스의 다음 디렉토리에 있는 정적 리소스 제공(/static, /public, /resources, /META-INF/resources)
 - src/main/resources 는 리소스를 보관하는 곳이고, 클래스패스의 시작 경로
 - 정적 리소스 경로 : src/main/resources/static
 - src/main/resources/static/basic/hello-form.html -> http://localhost:8080/static/hello-form.html

 2. 뷰 템플릿 사용
 - 웹 브라우저에 동적인 HTML 을 제공할 때는 뷰 템플릿 사용
 - 스프링 부트는 기본 뷰 템플릿 경로를 제공
 - 뷰 템플릿 경로 : src/main/resources/static

 3. HTTP 메시지 사용 - HTTP API 제공시 HTML 이 아니라 데이터를 전달해야 하므로, HTTP 메시지 바디에 JSON 형태의 데이터를 실어보냄
 */
@Controller
public class ResponseViewController {

    @RequestMapping("/response-view-v1")
    public ModelAndView responseViewV1() {
        return new ModelAndView("response/hello")
                .addObject("data", "hello!");
    }

    /**
     * @ResponseBody 가 없으면 response/hello 로 뷰 리졸버가 실행되어서 뷰를 찾고, 렌더링
     * @ResponseBody 가 있으면 뷰 리졸버를 실행하지 않고, HTTP 메시지 바디에 직접 response/hello 라는 문자가 입력
     */
    @RequestMapping("/response-view-v2")
    public String responseView2(Model model) {
        model.addAttribute("data", "hello!!");
        return "response/hello";
    }

    /**
     * 반환 타입이 void 인 경우
     * @Controller 를 사용하고 HttpServletResponse, OutputStream(Writer) 같은 HTTP 메시지 바디를 처리하는 파라미터가 없는 경우
     -> 요청 URL 을 참고하여 논리 뷰 이름으로 사용
     * URL: /response/hello
     * 실행: templates/response/hello.html
     * 해당 방식은 명시성이 너무 떨어지고 이렇게 딱 맞는 경우도 많이 없어서, 권장하지 않음!
     */
    @RequestMapping("/response/hello")
    public void responseViewV3(Model model) {
        model.addAttribute("data", "hello!!!");
    }
}
