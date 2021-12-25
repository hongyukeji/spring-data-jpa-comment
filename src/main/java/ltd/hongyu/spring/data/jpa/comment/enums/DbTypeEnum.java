package ltd.hongyu.spring.data.jpa.comment.enums;

import ltd.hongyu.spring.data.jpa.comment.behavior.IEnum;

/**
 * @author <a href="mailto:1527200768@qq.com">hongyu</a>
 */
public enum DbTypeEnum implements IEnum {
    /**
     * dbtype
     */
    MYSQL {
        @Override
        public String getCode() {
            return "1";
        }

        @Override
        public String getValue() {
            return "MYSQL";
        }
    },
    SQLSERVER {
        @Override
        public String getCode() {
            return "2";
        }

        @Override
        public String getValue() {
            return "MICROSOFT SQL SERVER";
        }
    },
    ORACLE {
        @Override
        public String getCode() {
            return "3";
        }

        @Override
        public String getValue() {
            return "ORACLE";
        }
    },
    POSTGRESQL {
        @Override
        public String getCode() {
            return "4";
        }

        @Override
        public String getValue() {
            return "POSTGRESQL";
        }
    }
}
