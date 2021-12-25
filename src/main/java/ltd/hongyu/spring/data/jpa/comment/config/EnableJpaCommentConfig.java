package ltd.hongyu.spring.data.jpa.comment.config;

import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
@Documented
@Import({JpaCommentConfig.class})
public @interface EnableJpaCommentConfig {
}
