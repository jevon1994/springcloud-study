package cn.leon.gateway.domain.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.util.Date;

/**
 * @ClassName GatewayRoutesEntity
 * @Description
 * @Author Jevon
 * @Date2020/1/10 15:20
 **/
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@TableName("gateway_routes")
public class GatewayRoutesEntity {
    private Integer id;
    @NotBlank
    private String serviceId;
    @NotBlank
    private String uri;
    @NotEmpty
    private String predicates;
    /**
     * 路由过滤器集合配置
     */
    private String filters;
    @TableField(value = "`order`")
    private Integer order;
    @Builder.Default
    private Date createdTime = new Date();
    @TableField(value = "updated_time",update = "now()")
    private Date updatedTime;

    private Boolean deleteFlag;
}
