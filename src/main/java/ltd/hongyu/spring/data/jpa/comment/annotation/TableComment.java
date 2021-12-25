package ltd.hongyu.spring.data.jpa.comment.annotation;

import java.lang.annotation.*;

/**
 * @author <a href="mailto:1527200768@qq.com">hongyu</a>
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
public @interface TableComment {

    /**
     * 字段注释
     *
     * @return String
     */
    String value() default "";
}
