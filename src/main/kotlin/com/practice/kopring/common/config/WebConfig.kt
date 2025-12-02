package com.practice.kopring.common.config

import com.practice.kopring.common.converter.MultipartJackson2HttpMessageConverter
import org.springframework.context.annotation.Configuration
import org.springframework.http.converter.HttpMessageConverter
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer

@Configuration
class WebConfig(
    private val multipartJackson2HttpMessageConverter: MultipartJackson2HttpMessageConverter
) : WebMvcConfigurer {

    override fun configureMessageConverters(converters: MutableList<HttpMessageConverter<*>>) {
        converters.add(multipartJackson2HttpMessageConverter)
    }
}