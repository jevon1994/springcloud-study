package cn.leon.gateway.mapper;

import cn.leon.gateway.domain.dto.GatewayRouteDefinition;
import cn.leon.gateway.domain.entity.GatewayRoutesEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
 * @ClassName GatewayRoutesMapper
 * @Description
 * @Author Jevon
 * @Date2020/1/14 9:27
 **/
public interface GatewayRoutesMapper extends BaseMapper<GatewayRoutesEntity> {
    List<GatewayRouteDefinition> selectAll();
}
