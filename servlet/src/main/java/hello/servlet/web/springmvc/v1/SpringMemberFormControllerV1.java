package hello.servlet.web.springmvc.v1;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * @RequestMapping
 - 스프링은 어노테이션을 확용하여 유연하고 실용적인 컨트롤러를 구성 -> @RequestMapping 을 사용한 컨트롤러
 - RequestMappingHandlerMapping - 1. 핸들러 매핑
 - RequestMappingHandlerAdapter - 2. 핸들러 어댑터
 - RequestMappingHandlerMapping 은 @RequestMapping 또는 @Controller 가 "클래스 레벨"에 붙어 있는 경우 매핑 정보로 인식한다.

 * 참고
 - 스프링 부트 3.0(스프링 프레임워크 6.0) 부터는 클래스에 @RequestMapping 이 있어도 스프링 컨트롤러로 인식하지 않는다.
 - 오직 @Controller 가 있어야 스프링 컨트롤러로 인식된다.
 - @RequestMappingHandlerMapping 에서 @RequestMapping 은 이제 인식하지 않고, @Controller 만 인식한다.
 */
//@Component @RequestMapping -> 스프링 빈으로 컴포넌트 스캔 + 핸들러 매핑 정보 인식
@Controller
public class SpringMemberFormControllerV1 {

    @RequestMapping("/springmvc/v1/members/new-form")
    public ModelAndView process() {
        return new ModelAndView("new-form");
    }
}
