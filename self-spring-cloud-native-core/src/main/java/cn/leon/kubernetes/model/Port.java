package cn.leon.kubernetes.model;

/**
 * @author mujian
 * @Classname Port
 * @Description
 * @Date 2022/2/15
 */
public class Port {
    private String name;
    private String num;
    private String target;
    private String protocol;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNum() {
        return num;
    }

    public void setNum(String num) {
        this.num = num;
    }

    public String getTarget() {
        return target;
    }

    public void setTarget(String target) {
        this.target = target;
    }

    public String getProtocol() {
        return protocol;
    }

    public void setProtocol(String protocol) {
        this.protocol = protocol;
    }
}