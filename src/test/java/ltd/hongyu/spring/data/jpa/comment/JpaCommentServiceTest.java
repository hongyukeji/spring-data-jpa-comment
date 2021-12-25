package ltd.hongyu.spring.data.jpa.comment;

import ltd.hongyu.spring.data.jpa.comment.service.JpaCommentService;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.StopWatch;

import javax.annotation.Resource;

@RunWith(SpringRunner.class)
@SpringBootTest
public class JpaCommentServiceTest {

    public Logger logger = LoggerFactory.getLogger(getClass());

    @Test
    void contextLoads() {
    }

    @Resource
    public JpaCommentService jpaCommentService;

    /**
     * 更新全库字段注释
     */
    @Test
    public void alterAllTableAndColumn() {

        logger.info("JpaCommentService 更新全库字段注释...");

        StopWatch watch = new StopWatch();
        watch.start();

        jpaCommentService.alterAllTableAndColumn();

        watch.stop();
        logger.info("JpaCommentService 更新全库字段注释耗时: {} ms", watch.getTotalTimeMillis());

    }

}
