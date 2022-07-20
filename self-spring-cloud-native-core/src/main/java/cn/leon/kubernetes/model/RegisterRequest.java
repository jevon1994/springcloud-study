package cn.leon.kubernetes.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author mujian
 * @Classname RegisterRequest
 * @Description
 * @Date 2022/2/10
 */
public class RegisterRequest {
    private Port[] ports;
    private String ipAddr;

    public Port[] getPorts() {
        return ports;
    }

    public void setPorts(Port[] ports) {
        this.ports = ports;
    }

    public String getIpAddr() {
        return ipAddr;
    }

    public void setIpAddr(String ipAddr) {
        this.ipAddr = ipAddr;
    }
}
