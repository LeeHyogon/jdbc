package hello.jdbc.repository;

import hello.jdbc.domain.Member;
import org.junit.jupiter.api.Test;

public class MemberRepositoryVOTest {

    MemberRepositoryVO repository= new MemberRepositoryVO();

    @Test
    public void curd() throws Exception {
        Member member = new Member("memberV1", 10000);
        repository.save(member);
    }
}
