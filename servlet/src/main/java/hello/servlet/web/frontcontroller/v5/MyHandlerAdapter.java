package hello.servlet.web.frontcontroller.v5;

import hello.servlet.web.frontcontroller.ModelView;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * ControllerV3, ControllerV4 는 완전히 다른 인터페이스로 호환이 불가능하다.
 * 호환이 불가능한 인터페이스를 어댑터 패턴을 사용하여 프론트 컨트롤러가 다양한 방식의 컨트롤러를 처리할 수 있도록 변경한다.
 */
public interface MyHandlerAdapter {

    /**
     * 어댑터가 해당 컨트롤러를 처리할 수 있는지 판단하는 메서드
     * handler 는 컨트롤러를 의미, 컨트롤러의 이름을 더 넓은 범위인 핸들러로 변경
     -> 컨트롤러의 개념뿐만 아니라 어떠한 것이든 해당하는 종류의 어댑터만 있으면 다 처리 가능 하기 때문에
     */
    boolean supports(Object handler);

    /**
     * 어댑터는 실제 컨트롤러를 호출하고, 그 결과로 ModelView 를 반환
     * 실제 컨트롤러가 ModelView 를 반환하지 못하면, 어댑터가 ModelView 를 직접 생성해서라도 반환
     * 이전 버전에서는 프론트 컨트롤러가 실제 컨트롤러를 호출했지만, 이제는 해당 어댑터를 통해 실제 컨트롤러가 호출
     */
    ModelView handle(HttpServletRequest request, HttpServletResponse response,
                     Object handler) throws ServletException, IOException;
}
