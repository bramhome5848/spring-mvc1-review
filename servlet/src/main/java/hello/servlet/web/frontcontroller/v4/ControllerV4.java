package hello.servlet.web.frontcontroller.v4;

import java.util.Map;

/**
 * 실제 컨트톨러를 구현하는 개발자 입장에서 보면, 항상 ModelView 객체를 생성하고 반환해야 하는 번거로움이 존재한다.
 * 좋은 프레임워크는 아키텍처도 중요하지만, 실제 개발하는 개발자가 단순하고 편리하게 사용할 수 있어야 한다.(V3 -> V4)
 * 만드는 사람이 힘들면 사용하는 사람이 편하다!!
 * 프레임워크나 공통 기능이 수고로워야 사용하는 개발자가 편리해진다..

 * ControllerV4 는 ModelView 가 없다 -> model 객체는 파라미터로 전달, 결과로 뷰의 이름만 리턴
 */
public interface ControllerV4 {
    String process(Map<String, String> paramMap, Map<String, Object> model);
}
