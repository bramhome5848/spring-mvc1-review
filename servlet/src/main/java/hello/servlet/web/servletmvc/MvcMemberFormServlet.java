package hello.servlet.web.servletmvc;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * MVC 패턴 개요
 * 너무 많은 역할
 - 하나의 서블릿이나 JSP 만으로 비즈니스 로직과 뷰 렌더링까지 모두 호출하게 되면 너무 많은 역할을 하게되고, 유지보수가 어렵다.

 * 변경의 라이프 사이클
 - UI 수정과 비즈니스 로직 수정은 각각 다르게 발생할 가능성이 높고 대부분 서로에게 영향을 주지 않는다.
 - 변경 라이프 사이클이 다른 부분을 하나의 코드로 관리하는 것은 유지보수 관점에서 좋지 않다.

 * 기능 특화
 - JSP 같은 뷰 템플릿은 화면을 렌더링 하는데 최적화 되어 있다.

 * Model View Controller
 - MVC 패턴은 서블릿이나, JSP 로 처리하던 것을 Controller 와 View 영역으로 서로 역할을 나눈 것을 의미한다.
 - 컨트롤러 : HTTP 요청을 받아 파라미터 검증, 비즈니스 로직을 실행한다. 뷰에 전달할 결과 데이터를 조회해서 모델에 담는다.
 - 뷰 : 모델에 담겨있는 데이터를 사용해서 화면을 그리는 일에 집중한다.

 * 참고
 * 컨트롤러에 비즈니스 로직을 두는 경우는 컨트롤러의 역할이 너무 많아지기에 일반적으로 서비스라는 계층을 별도로 만들어 처리한다.
 * 컨트롤러는 비즈니스 로직이 있는 서비스를 호출하는 역할을 담당한다.
 * MVC1, MVC2 약간의 차이

 * mvcMemberFormServlet
 - 서블릿을 컨트롤러로 사용, JSP 를 뷰로 사용하여 MVC 패턴 적용
 - HttpServletRequest : Model

 * redirect vs forward
 - 리다이렉트는 실제 클라이언트(웹 브라우저)에 응답이 나갔다가, 클라이언트가 redirect 경로로 다시 요청을 수행한다.
 - 따라서 클라이언트가 인지할 수 있고, URL 경로도 실제로 변경된다.
 - 반면에 포워드는 서버 내부에서 일어나는 호출이기 때문에 클라이언트가 전혀 인지할 수 없다.
 */
@WebServlet(name = "mvcMemberFormServlet", urlPatterns = "/servlet-mvc/members/new-form")
public class MvcMemberFormServlet extends HttpServlet {

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // WEB-INF 경로안에 JSP 가 존재할 경우 외부에서 직접 JSP 를 호출할 수 없다. -> 항상 컨트롤러를 통해서 JSP 를 호출해야한다.
        String viewPath = "/WEB-INF/views/new-form.jsp";
        RequestDispatcher dispatcher = request.getRequestDispatcher(viewPath);
        dispatcher.forward(request, response);  // 다른 서블릿이나 JSP 로 이동할 수 있는 기능, 서버 내부에서 다시 호출이 발생한다.
    }
}
