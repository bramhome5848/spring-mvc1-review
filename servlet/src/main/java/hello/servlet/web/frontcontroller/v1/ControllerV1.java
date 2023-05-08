package hello.servlet.web.frontcontroller.v1;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * FrontController 패턴 특징
 - FrontController 서블릿 하나로 클라이언트의 요청을 받는다.
 - FrontController 가 요청에 맞는 컨트롤러를 찾아서 호출한다.
 - 입구를 하나로 -> 공통 처리 가능
 - FrontController 를 제외한 나머지 컨트롤러는 서블릿을 사용하지 않아도 된다.

 * 스프링 웹 MVC 와 프론트 컨트롤러
 - 스프링 웹 MVC 의 핵심 -> FrontController
 - 스프링 웹 MVC 의 DispatcherServlet 이 FrontController 패턴으로 구현되어 있다.
 */
public interface ControllerV1 {
    void process(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException;
}
