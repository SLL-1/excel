import com.example.DemoApplication;
import com.example.entity.student;
import com.example.utils.RedisUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = DemoApplication.class)
public class RedisTest {
    @Autowired
   private RedisUtil redisUtil;
    @Test
    public void addRedis(){

            student stu=new student(1,"张三",12,"男",new Date(),"乒乓球");
            redisUtil.set("student"+1,stu);
            student stu1=new student();
            stu1.setStuage(1);
            stu1.setStuname("李四");
            stu1.setStunum(2);
            stu1.setStusex("nv");
            stu1.setStuhobby("篮球");
            stu1.setStubirthday(new Date());
            redisUtil.set("student"+2,stu1);


    }
    @Test
    public void getRedis(){

//            student stu=new student(1,"张三",12,"男",new SimpleDateFormat().parse("2020-9-2"),"乒乓球");
           System.out.println((student)redisUtil.get("student"+1));


    }
}
