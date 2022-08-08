import com.hzh.crm.workbench.domain.Activity;
import com.hzh.crm.workbench.mapper.ActivityMapper;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author DAHUANG
 * @date 2022/3/22
 */
public class SqlSessionCacheTest {

    public SqlSessionFactory getSqlSessionFactory(){
        String resource = "mybatis-config.xml";
        InputStream inputStream = null;
        try {
            inputStream = Resources.getResourceAsStream(resource);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new SqlSessionFactoryBuilder().build(inputStream);
    }

    @Test
    public void test(){
        SqlSessionFactory sqlSessionFactory=getSqlSessionFactory();
        SqlSession openSession = sqlSessionFactory.openSession();
        ActivityMapper mapper = openSession.getMapper(ActivityMapper.class);
        Map<String,Object> map=new HashMap<>();
        map.put("id","06f5fc056eac41558a964f96daa7f27c");
        List<Activity> activities = mapper.selectActivityByConditionForPage(map);
        for (Activity activity : activities) {
            System.out.println(activities);
        }
    }
}
