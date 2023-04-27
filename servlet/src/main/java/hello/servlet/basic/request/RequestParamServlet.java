package hello.servlet.basic.request;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 파라미터 전송 기능 - http://localhost:8080/request-param?username=hello&age=20
 * 동일한 파라미터 전송 가능 http://localhost:8080/request-param?username=hello&username=kim&age=20

 * POST 의 HTML Form 전송시
 * URL: http://localhost:8080/request-param
 * content-type: application/x-www-form-urlencoded
 * message body: username=hello&age=20

 * content-type 은 HTTP 메시지 바디의 데이터 형식을 지정한다.
 * application/x-www-form-urlencoded 형식은 메시지 바디의 데이터 형식이 GET 방식의 쿼리 파라미터 형식과 같기 때문에 메서드를 그대로 사용할 수 있다.

 * 클라이언트(웹 브라우저) 입장에서는 두 방식(GET, POST)에 차이가 있지만, 서버 입장에서는 둘의 형식이 동일하기 때문에
 * request.getParameter()로  편리하게 구분없이 조회할 수 있다.
 * request.getParameter()는 GET URL 쿼리 파라미터 형식도 지원하고, POST HTML Form 형식도 둘 다 지원한다.

 * 참고
 * GET URL 쿼리 파라미터 형식으로 클라이언트에서 서버로 데이터를 전달할 때는 HTTP 메시지 바디를 사용하지 않기 때문에 content-type 이 없다.
 * POST HTML Form 형식으로 데이터를 전달할 경우 HTTP 메시지 바디에 해당 데이터를 포함해서 보내기 때문에 바디에 포함된 데이터가 어떤 형식인지
 * content-type 을 꼭 지정해야 한다.
 */
@WebServlet(name = "requestParamServlet", urlPatterns = "/request-param")
public class RequestParamServlet extends HttpServlet {

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("[전체 파라미터 조회] - start");

        /*
        Enumeration<String> parameterNames = request.getParameterNames();
        while (parameterNames.hasMoreElements()) {
            String paramName = parameterNames.nextElement();
            System.out.println(paramName + "=" + request.getParameter(paramName));
        }
        */

        request.getParameterNames().asIterator()
                .forEachRemaining(paramName -> System.out.println(paramName + "=" + request.getParameter(paramName)));
        System.out.println("[전체 파라미터 조회] - end");
        System.out.println();

        System.out.println("[단일 파라미터 조회]");

        // 중복되는 param 경우 가장 첫번째 값이 우선시 됨
        String username = request.getParameter("username");
        System.out.println("request.getParameter(username) = " + username);
        String age = request.getParameter("age");
        System.out.println("request.getParameter(age) = " + age);
        System.out.println();

        System.out.println("[이름이 같은 복수 파라미터 조회]");
        System.out.println("request.getParameterValues(username)");
        String[] usernames = request.getParameterValues("username");

        for(String name : usernames) {
            System.out.println("username = " + name);
        }

        response.getWriter().write("ok");
    }
}
