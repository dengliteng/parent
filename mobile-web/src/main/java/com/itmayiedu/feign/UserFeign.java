package com.itmayiedu.feign;

import com.itmayiedu.api.service.UserSerice;
import org.springframework.cloud.netflix.feign.FeignClient;

/**
 * 功能描述:
 *        继承api中的接口UserSerice,利用SpringCloud的@FeignClient("member")
 */
@FeignClient("member")
public interface UserFeign extends UserSerice {
}
