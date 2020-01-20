package cn.leon.auth.endpoint;

import org.springframework.security.oauth2.provider.endpoint.FrameworkEndpoint;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

/**
 * @ClassName LoginEndpoint
 * @Description
 * @Author Jevon
 * @Date2020/1/19 18:00
 **/
@FrameworkEndpoint
public class LoginEndpoint {


    @GetMapping("/image/webp")
    public String webp() {
        return "success";
    }

    @GetMapping("/authentication/require")
    public ModelAndView require() {
        return new ModelAndView("login");
    }

    @GetMapping("/success")
    public String success() {
        return "success";
    }

    @GetMapping("/")
    public String only() {
        return "only";
    }
}
