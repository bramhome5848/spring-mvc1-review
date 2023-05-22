package hello.servlet.web.springmvc.old;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * SpringMVC 동작순서
 1. 핸들러 조회 : 핸들러 매핑을 통해 요청 URL 에 매핑된 핸들러(컨트롤러)를 조회한다.
 2. 핸들러 어댑터 조회 : 핸들러를 실행할 수 있는 핸들러 어댑터를 조회한다.
 3. 핸들러 어댑터 실행 : 핸들러 어댑터를 실행한다.
 4. 핸들러 실행 : 핸들러 어댑터가 실제 핸들러를 실행한다.(컨트롤러를 실행한다)
 5. ModelAndVIew 반환 : 핸들러 어댑터는 핸들러가 반환하는 정보를 ModelAndView 로 변환해서 반환한다.
 6. viewResolver 호출 : 뷰 리졸버를 찾고 실행한다.
 (JSP 의 경우 InternalResourceViewResolver 가 자동 등록되고 사용된다)
 7. View 반환 : 뷰 리졸버는 뷰의 논리 이름을 물리 이름으로 바꾸고, 렌더링 역할을 담당하는 뷰 객체를 반환한다.
 (JSP 의 경우 InternalResourceView(JstlView)를 반환하는데, 내부에 forward() 로직이 있다)
 8. 뷰 렌더링 : 뷰를 통해서 뷰를 렌더링 한다.

 * 과거에 주로 사용했던 스프링이 제공하는 간단한 컨트롤러 -> Controller
 * 해당 컨트롤러가 호출되려면 2가지 필요 HandlerMapping, HandlerAdapter
 * HandlerMapping - 핸들러 매핑에서 이 컨트롤러를 찾을 수 있어야 한다.
 -> 스프링 빈의 이름으로 핸들러를 찾을 수 있는 핸들러 매핑이 필요하다.
 * HandlerAdapter - 핸들러 매핑을 통해서 찾은 핸들러를 실행할 수 있는 핸들러 어댑터가 필요하다.
 -> Controller 인터페이스를 실행할 수 있는 핸들러 어댑터를 찾고 실행해야 한다.

 * 우선순위 RequestMappingHandlerMapping > BeanNameUrlHandleMapping

 * ViewResolver - 스프링부트가 자동으로 등록하는 뷰 리졸버
 1. 핸들러 어댑터 호출 - 핸들러 어댑터를 통해 new-form 이라는 논리 뷰 이름을 획득한다.
 2. ViewResolver 호출
 - new-form 이라는 뷰 이름으로 viewResolver 를 순서대로 호출한다.
 - BeanNameViewResolver 는 new-form 이라는 이름의 스프링 빈으로 등록된 뷰를 찾아야 하는데 없다.
 - InternalResourceViewResolver 가 호출된다.
 3. InternalResourceViewResolver - InternalResourceView 를 반환한다.
 4. 뷰 - InternalResourceView - JSP 처럼 forward() 를 호출해서 처리할 수 있는 경우에 사용한다.
 5. view.render() - view.render() 가 호출되고 InternalResourceView 는 forward() 를 사용해서 JSP 를 실행한다.

 * 우선순위 BeanNameViewResolver > InternalResourceViewResolver

 * 참고
 - InternalResourceViewResolver 는 만약 JSTL 라이브러리가 있으면 InternalResourceView 를 상속받은 JstlView 를 반환한다.
 - JSP 의 경우 forward() 통해서 해당 JSP 로 이동해야 렌더링이 된다.(JSP 를 제외한 나머지는 forward() 과정없이 바로 렌더링이 된다)
 - Thymeleaf 뷰 템플릿을 사용하면 ThymeleafViewResolver 를 등록해야 한다.
 */
@Component("/springmvc/old-controller")
public class OldController implements Controller {

    @Override
    public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
        System.out.println("OldController.handleRequest");
        return new ModelAndView("new-form");
    }
}
