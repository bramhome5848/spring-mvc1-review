package hello.servlet.domain.member;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 동시성 문제가 고려되지 않음, 실무에서는 ConcurrentHashMap, AtomicLong 사용 고려
 * 회원 저장소는 싱글톤 패턴 적용
 * 스프링을 사용하면 빈으로 등록하여 사용하면 되지만, 최대한 스프링 없이 순수 서블릿만으로 구성
 *
 */
public class MemberRepository {

    private final static Map<Long, Member> store = new HashMap<>();
    private static long sequence = 0L;

    private static final MemberRepository instance = new MemberRepository();

    public static MemberRepository getInstance() {
        return instance;
    }

    //싱글톤 패턴 구현을 위해 단 하나의 객체를 생성하고 공유하기 위해 생성자를 private 접근자로 막아둔다.
    private MemberRepository() {
    }

    public Member save(Member member) {
        member.setId(++sequence);
        store.put(member.getId(), member);
        return member;
    }

    public Member findById(Long id) {
        return store.get(id);
    }

    public List<Member> findAll() {
        return new ArrayList<>(store.values());
    }

    public void clearStore() {
        store.clear();
    }
}
