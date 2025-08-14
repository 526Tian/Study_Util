package com.cqust.convert;

import com.cqust.config.HttpClientInfoConfig;
import com.cqust.http.config.HttpConfig;
import org.mapstruct.Mapper;
import org.mapstruct.NullValueCheckStrategy;

/**
 * @author Ltian
 * @date 2025/8/14 0:05
 * @description:
 */
@Mapper(
    componentModel = "spring",
        nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS
)
public interface HttpClientConfigConvert {

    /**
     * 本地http配置转换为工具类http配置
     * @param httpClientInfoConfig httpClientInfoConfig
     * @return HttpConfig
     */
     HttpConfig httpClientInfoConfigConvertHttpConfig(HttpClientInfoConfig httpClientInfoConfig);

}
