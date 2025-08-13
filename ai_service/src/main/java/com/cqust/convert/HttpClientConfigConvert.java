package com.cqust.convert;

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
public class HttpClientConfigConvert {



}
