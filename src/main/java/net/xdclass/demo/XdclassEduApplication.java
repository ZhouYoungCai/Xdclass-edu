package net.xdclass.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.TimeUnit;

@SpringBootApplication
@RestController
public class XdclassEduApplication {

    public static void main(String[] args) {
        SpringApplication.run(XdclassEduApplication.class, args);
    }

    private static List<String> tokenList = new ArrayList<>();

    private static List<String> productList = new ArrayList<>();

    static {
        productList.add("分布式系统ELK搭建日志采集系统");
        productList.add("后端一对一辅导");
        productList.add("前端一对一辅导");
        productList.add("IT开发测试环境安装视频【热门技术+工具类】");
        productList.add("永久会员");
        productList.add("22年新版【前端高级工程师】面试专题第一季");
        productList.add("新版丨急速掌握分布式链路追踪Apache Skywalking最佳实践");
    }


    /**
     * 断言测试- 持久时间断言
     *
     * @return
     */
    @GetMapping("/api/v1/product/second_kill")
    public JsonData secondKill() throws InterruptedException {

        Random random = new Random();
        int num = random.nextInt(500)+1;
        TimeUnit.MILLISECONDS.sleep(num);
        System.out.println("接口访问时间:" + LocalDateTime.now()+",睡眠时间(毫秒):"+num);
        return JsonData.buildSuccess(num);
    }



    /**
     * 可变参数压测
     *
     * @return
     */
    @GetMapping("/api/v1/product/detail")
    public JsonData detail(@RequestParam("id") Integer id,@RequestParam("title") String title) {

        System.out.println("接口访问时间:" + LocalDateTime.now());
        return JsonData.buildSuccess("id="+id+", 商品:"+title);
    }






    /**
     * 查询商品列表
     *
     * @return
     */
    @GetMapping("/api/v1/product/list")
    public JsonData productList() {
        return JsonData.buildSuccess(productList);
    }


    /**
     * 查询用户信息 ,需要登录
     *
     * @return
     */
    @GetMapping("/api/v1/user/info")
    public JsonData userInfo(HttpServletRequest request) {
        String token = request.getHeader("token");
        System.out.println("接口访问时间:" + LocalDateTime.now());

        if(token != null && tokenList.contains(token)){
            Map<String, Object> map = new HashMap<>(1);
            map.put("name", "jack");
            map.put("wechat", "xdclass6");
            map.put("token",token);
            return JsonData.buildSuccess(map);

        }else {
            return JsonData.buildError("请登录");
        }

    }


    /**
     * 登录
     * @param param
     * @return
     *
     */
    @PostMapping("/api/v1/user/login")
    public JsonData login(@RequestBody Map<String, String> param) {

        System.out.println("接口访问时间:" + LocalDateTime.now());

        String mail = param.get("mail");
        String pwd = param.get("pwd");

        if ("794666918@qq.com".equals(mail) && "123456".equals(pwd)) {

            String token = UUID.randomUUID().toString().replaceAll("-","");

            tokenList.add(token);
            return JsonData.buildSuccess(token);

        }else {
            return JsonData.buildError("账号密码错误");
        }


    }


}
