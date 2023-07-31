package hello.springmvc.basic;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 로깅라이브러리
 * SLF4J 는 인터페이스이고 구현체로 Logback 같은 라이브러리를 선택하면 된다.
 * 실무에서는 스프링 부트가 기본으로 제공하는 Logback 을 대부분 사용

 * @Controller 는 반환 값이 String 이면 뷰 이름으로 인식 -> 뷰를 찾고 뷰가 랜더링
 * @RestController 는 반환 값으로 뷰를 찾는 것이 아니라, HTTP 메시지 바디에 바로 입력

 * 로그사용시 장점
 * 쓰레드 정보, 클래스 이름 같은 부가 정보를 함께 볼 수 있고, 출력 모양을 조정 가능
 * 로그 레벨에 따라 개발 서버, 운영서버와 같은 상황에 맞게 조절 가능
 * 콘솔에만 출력뿐만 아니라 파일이나 네트워크 등, 로그를 별도의 위치에 남기는 것이 가능
 * 파일로 로그를 남길 때는 일별, 특정 용량에 따라 로그를 분할하는 것도 가능
 * System.out 보다 성능이 우수함(내부 버퍼링, 멀티 쓰레드 등등) -> 실무에서는 꼭 로그를 사용
 */
@Slf4j
@RestController
public class LogTestController {

    //private final Logger log = LoggerFactory.getLogger(getClass());

    @GetMapping("/log-test")
    public String logTest() {
        String name = "Spring";

        //로그 레벨 -> TRACE > DEBUG > INFO > WARN > ERROR
        log.trace("trace log = {}", name);
        log.debug("debug log = {}", name);
        log.info("info log = {}", name);
        log.warn("warn log = {}", name);
        log.error("error log = {}", name);

        //로그레벨에 따라 출력이 되지 않아도 a+b 계산 연산이 먼저 실행됨 -> 쓸모없는 연산이 발생
        //log.debug("String concat log = " + name);
        return "ok";
    }
}
