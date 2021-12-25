package ltd.hongyu.spring.data.jpa.comment.config;

import cn.hutool.core.util.StrUtil;
import ltd.hongyu.spring.data.jpa.comment.enums.DbTypeEnum;
import ltd.hongyu.spring.data.jpa.comment.properties.JpaCommentProperties;
import ltd.hongyu.spring.data.jpa.comment.service.AlterCommentService;
import ltd.hongyu.spring.data.jpa.comment.service.JpaCommentService;
import ltd.hongyu.spring.data.jpa.comment.service.impl.MysqlAlterCommentServiceImpl;
import ltd.hongyu.spring.data.jpa.comment.service.impl.OracleAlterCommentServiceImpl;
import ltd.hongyu.spring.data.jpa.comment.service.impl.PgSqlAlterCommentServiceImpl;
import ltd.hongyu.spring.data.jpa.comment.service.impl.SqlServerAlterCommentServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.orm.jpa.JpaProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.annotation.Resource;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.sql.DataSource;
import java.sql.DatabaseMetaData;
import java.sql.SQLException;
import java.util.Map;

/**
 * JpaCommentAutoConfig 配置
 *
 * @author <a href="mailto:1527200768@qq.com">hongyu</a>
 */
@Configuration
@EnableConfigurationProperties({JpaCommentProperties.class})
@AutoConfigureAfter({EntityManager.class, JdbcTemplate.class, JpaProperties.class})
@ConditionalOnProperty(prefix = "spring.jpa.comment", value = "enable", havingValue = "true")
public class JpaCommentAutoConfig {

    public static Logger logger = LoggerFactory.getLogger(JpaCommentAutoConfig.class);

    @PersistenceContext
    private EntityManager entityManager;

    @Resource
    private JdbcTemplate jdbcTemplate;

    @Resource
    DataSource dataSource;

    @Resource
    JpaProperties jpaProperties;

    @Bean
    @ConditionalOnMissingBean
    public AlterCommentService alterCommentService() throws SQLException {
        DatabaseMetaData metaData = dataSource.getConnection().getMetaData();
        String databaseType = metaData.getDatabaseProductName().toUpperCase();
        String schema = "";
        AlterCommentService service;
        if (databaseType.contains(DbTypeEnum.MYSQL.getValue())) {
            schema = jdbcTemplate.queryForObject("select database() from dual", String.class);
            service = new MysqlAlterCommentServiceImpl();
        } else if (databaseType.contains(DbTypeEnum.SQLSERVER.getValue())) {
            schema = "dbo";
            String jpaDefaultSchema = "default_schema";
            Map<String, String> params = jpaProperties.getProperties();
            if (params != null && StrUtil.isNotBlank(params.get(jpaDefaultSchema))) {
                schema = params.get(jpaDefaultSchema);
            }
            service = new SqlServerAlterCommentServiceImpl();
        } else if (databaseType.contains(DbTypeEnum.ORACLE.getValue())) {
            schema = jdbcTemplate.queryForObject("select SYS_CONTEXT('USERENV','CURRENT_SCHEMA') CURRENT_SCHEMA from dual", String.class);
            service = new OracleAlterCommentServiceImpl();
        } else if (databaseType.contains(DbTypeEnum.POSTGRESQL.getValue())) {
            schema = jdbcTemplate.queryForObject(" SELECT CURRENT_SCHEMA ", String.class);
            service = new PgSqlAlterCommentServiceImpl();
        } else {
            service = null;
            logger.error("can not find DatabaseProductName {}", databaseType);
        }

        if (service != null) {
            service.setSchema(schema);
            service.setJdbcTemplate(jdbcTemplate);
            logger.debug("当前数据库schema为 {}", service.getSchema());
        }

        return service;
    }

    @Bean(initMethod = "init")
    @ConditionalOnMissingBean
    public JpaCommentService jpacommentService() throws SQLException {
        JpaCommentService service = new JpaCommentService();
        service.setEntityManager(entityManager);
        service.setAlterCommentService(alterCommentService());
        return service;
    }

}
