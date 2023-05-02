package hello.servlet.web.servletmvc;

import hello.servlet.domain.member.Member;
import hello.servlet.domain.member.MemberRepository;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * MVC 패턴의 한계
 - MVC 패턴 적용으로 컨트롤러 역할과 뷰를 렌더링 하는 역할을 명확하게 구분 -> 코드가 깔끔하고 직관적이다.
 - 컨트롤러는 중복이 많고 필요하지 않는 코드들이 많다.

 * 포워드 중복 - View 로 이동하는 코드가 항상 중복된다.(공통화해도 되지만 해당 메서드도 항상 직접 호출해야 한다)
 * View Path 중복 - String viewPath = "/WEB-INF/views/new-form.jsp"
 * 사용하지 않는 코드가 존재 - HttpServletRequest request, HttpServletResponse response

 * 공통 처리의 어려움
 - 컨트롤러에서 공통으로 처리해야 하는 부분이 점점 더 많이 증가한다.
 - 공통 기능을 메서드로 뽑으면 될 것 같지만, 결과적으로 해당 메서드를 항상 호출해야 하고, 실수로 호출하지 않으면 문제가 된다.
 - 호출 하는 것 자체도 중복이 된다.
 - 컨트롤러 호출 전에 먼저 공통 기능 처리가 필요하다.(수문장 역할이 필요)
 - 프론트 컨트롤러(Front Controller) 패턴을 도입하면 문제를 해결할 수 있다.(입구를 하나로!)
 - 스프링 MVC 의 핵심도 프론트 컨트롤러에 있다.
 */
@WebServlet(name = "mvcMemberListServlet", urlPatterns = "/servlet-mvc/members")
public class MvcMemberListServlet extends HttpServlet {

    private final MemberRepository memberRepository = MemberRepository.getInstance();

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        System.out.println("MvcMemberListServlet.service");
        List<Member> members = memberRepository.findAll();

        request.setAttribute("members", members);

        String viewPath = "/WEB-INF/views/members.jsp";
        RequestDispatcher dispatcher = request.getRequestDispatcher(viewPath);
        dispatcher.forward(request, response);
    }
}
