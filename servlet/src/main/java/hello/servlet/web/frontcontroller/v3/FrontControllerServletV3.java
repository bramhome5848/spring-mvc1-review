package hello.servlet.web.frontcontroller.v3;

import hello.servlet.web.frontcontroller.ModelView;
import hello.servlet.web.frontcontroller.MyView;
import hello.servlet.web.frontcontroller.v3.controller.MemberFormControllerV3;
import hello.servlet.web.frontcontroller.v3.controller.MemberListControllerV3;
import hello.servlet.web.frontcontroller.v3.controller.MemberSaveControllerV3;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * 서블릿 종속성 제거
 - 요청 파라미터 정보는 Map 으로 대체한다.
 - request 객체를 Model 로 사용하는 대신 별도의 Model 객체를 만들어 반환한다.
 - 사용하는 컨트롤러(MemberForm, MemberList, MemberSave) 가 서블릿 기술을 전혀 사용하지 않도록 변경한다.
 - 구현 코드도 매우 단순해지고, 테스트 코드 작성이 쉽다.

 * 뷰 이름 중복 제거
 - 컨트롤러는 뷰의 논리 이름을 반환하고, 실제 물리 위치의 이름은 프론트 컨트롤러에서 처리하여 단순화 할 수 있다.
 - 향후 폴더의 위치가 함께 이동해도 수정이 용이하다.(FrontController 만 수정하면 됨)

 * 수행과정
 1. FrontController 에서 매핑정보 확인
 2. 해당 컨트롤러 호출
 3. ModelView 반환
 4. viewResolver 호출
 5. MyView 반환
 6. render(model) 호출 -> HTML 응답
 */
@WebServlet(name = "frontControllerServletV3", urlPatterns = "/front-controller/v3/*")  // /v3 를 포함한 하위 모든 요청
public class FrontControllerServletV3 extends HttpServlet {

    private final Map<String, ControllerV3> controllerMap = new HashMap<>();

    public FrontControllerServletV3() {
        controllerMap.put("/front-controller/v3/members/new-form", new MemberFormControllerV3());
        controllerMap.put("/front-controller/v3/members/save", new MemberSaveControllerV3());
        controllerMap.put("/front-controller/v3/members", new MemberListControllerV3());
    }

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("FrontControllerServletV3.service");
        String requestURI = request.getRequestURI();

        ControllerV3 controller = controllerMap.get(requestURI);

        if(controller == null) {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            return;
        }

        Map<String, String> paramMap = createParamMap(request);
        ModelView mv = controller.process(paramMap);

        String viewName = mv.getViewName();
        MyView view = viewResolver(viewName);
        view.render(mv.getModel(), request, response);
    }

    private Map<String, String> createParamMap(HttpServletRequest request) {
        Map<String, String> paramMap = new HashMap<>();

        request.getParameterNames().asIterator()
                .forEachRemaining(paramName -> paramMap.put(paramName, request.getParameter(paramName)));

        return paramMap;
    }

    private MyView viewResolver(String viewName) {
        return new MyView("/WEB-INF/views/" + viewName + ".jsp");
    }
}
