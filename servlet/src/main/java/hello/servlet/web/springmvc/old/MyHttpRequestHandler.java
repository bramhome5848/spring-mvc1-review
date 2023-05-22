package hello.servlet.web.springmvc.old;

import org.springframework.stereotype.Component;
import org.springframework.web.HttpRequestHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 핸들러(컨트롤러)는 서블릿과 가장 유사한 형태의 핸들러

 * localhost:8080/springmvc/request-handler 조회시
 * 1. 핸들러 매핑으로 핸들러 조회 - RequestMapping(찾을 수 없음) -> BeanNameUrlHandleMapping(MyHttpRequestHandler 반환)
 * 2. 핸들러 어댑터 조회 - HandlerAdapter 의 support() 를 순서대로 호출(support -> instance of 확인했던 부분!!)
 * 3. 핸들러 어댑터 실행
 - 디스패처 서블릿이 조회한 HttpRequestHandlerAdapter 를 실행하면서 핸들러 정보도 함께 넘겨준다.
 - HttpRequestHandlerAdapter 는 MyHttpRequestHandler 를 내부에서 실행하고, 그 결과를 반환한다.
 */
@Component("/springmvc/request-handler")
public class MyHttpRequestHandler implements HttpRequestHandler {

    @Override
    public void handleRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("MyHttpRequestHandler.handleRequest");
    }
}
