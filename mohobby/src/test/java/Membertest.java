import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.yedam.mohobby.mapper.user.MemberMapper;
import lombok.extern.log4j.Log4j;

import lombok.extern.log4j.Log4j;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("file:src/main/webapp/WEB-INF/spring/**/*-context.xml")
@Log4j
public class Membertest {


	@Autowired
	MemberMapper mapper;
	
	@Test
	public void getMemberTest() {
		mapper.getMember("gabin");
	};
}

