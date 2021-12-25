package ltd.hongyu.spring.data.jpa.comment.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;

@Configuration
@ComponentScan("ltd.hongyu.spring.data.jpa.comment")
public class JpaCommentConfig {

    @PostConstruct
    public void setProperties() {
        //System.setProperty("spring.jpa.comment.enable", "true");
    }

}
