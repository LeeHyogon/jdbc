package hello.jdbc.repository;

import hello.jdbc.domain.Member;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
public class MemberRepositoryVOTest {

    MemberRepositoryVO repository= new MemberRepositoryVO();

    @Test
    public void curd() throws Exception {
        //save
        Member member = new Member("memberV4", 10000);
        repository.save(member);

        //findById
        Member findMember = repository.findById(member.getMemberId());
        log.info("findMember={}", findMember);
        log.info("member=findMember {}", member == findMember);
        log.info("member equals findMember {}", member.equals(findMember));
        assertThat(findMember).isEqualTo(member);
    }
}
