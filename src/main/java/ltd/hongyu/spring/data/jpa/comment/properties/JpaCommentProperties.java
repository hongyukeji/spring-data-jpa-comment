package ltd.hongyu.spring.data.jpa.comment.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * JpaCommentProperties 配置文件
 *
 * @author <a href="mailto:1527200768@qq.com">hongyu</a>
 */
@ConfigurationProperties(prefix = "spring.jpa.comment")
public class JpaCommentProperties {

    private boolean enable = true;

    private boolean alwaysUpdate = true;

    public boolean isEnable() {
        return enable;
    }

    public void setEnable(boolean enable) {
        this.enable = enable;
    }

    public boolean isAlwaysUpdate() {
        return alwaysUpdate;
    }

    public void setAlwaysUpdate(boolean alwaysUpdate) {
        this.alwaysUpdate = alwaysUpdate;
    }
}
