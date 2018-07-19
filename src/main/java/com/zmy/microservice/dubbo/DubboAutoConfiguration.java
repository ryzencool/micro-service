package com.zmy.microservice.dubbo;

/*
 * Copyright (C) 2018 The gingkoo Authors
 * This file is part of The gingkoo library.
 *
 * The gingkoo is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * The gingkoo is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with The gingkoo.  If not, see <http://www.gnu.org/licenses/>.
 */

import com.alibaba.dubbo.config.*;
import com.alibaba.dubbo.config.spring.schema.DubboBeanDefinitionParser;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

/**
 * @author: zmy
 * @create: 2018/7/19
 */
@Configuration
@EnableConfigurationProperties(DubboProperties.class)
@ConditionalOnClass({DubboBeanDefinitionParser.class})
public class DubboAutoConfiguration {


    @Autowired
    private DubboProperties dubboProperties;

    @Bean
    @ConditionalOnProperty(prefix = "spring.dubbo.application", name = "name", havingValue = "")
    public ApplicationConfig applicationConfig() {
        ApplicationConfig application = new ApplicationConfig();
        BeanUtils.copyProperties(dubboProperties.getApplication(), application);
        return application;
    }

    @Bean
    @ConditionalOnProperty(prefix = "spring.dubbo.registry", name = "address", havingValue = "")
    public RegistryConfig registryConfig() {
        RegistryConfig registry = new RegistryConfig();
        BeanUtils.copyProperties(dubboProperties.getRegistry(), registry);
        return registry;
    }

    @Bean
    @Primary
    @ConditionalOnProperty(prefix = "spring.dubbo.primary-protocol", name = "name", havingValue = "")
    public ProtocolConfig primaryProtocolConfig() {
        ProtocolConfig protocol = new ProtocolConfig();
        BeanUtils.copyProperties(dubboProperties.getPrimaryProtocol(), protocol);
        protocol.setDefault(dubboProperties.getPrimaryProtocol().getIsDefault());
        if (protocol.isDefault() == null) {
            protocol.setDefault(true);
        }
        return protocol;
    }


    @Bean
    @ConditionalOnProperty(prefix = "spring.dubbo.secondary-protocol", name = "name", havingValue = "")
    public ProtocolConfig secondaryProtocolConfig() {
        ProtocolConfig protocol = new ProtocolConfig();
        BeanUtils.copyProperties(dubboProperties.getSecondaryProtocol(), protocol);
        protocol.setDefault(dubboProperties.getSecondaryProtocol().getIsDefault());
        if (protocol.isDefault() == null) {
            protocol.setDefault(false);
        }
        return protocol;
    }

    @Bean
    @ConditionalOnProperty(prefix = "spring.dubbo.provider", name = {"enable"}, havingValue = "true")
    public ProviderConfig providerConfig() {
        ProviderConfig provider = new ProviderConfig();
        BeanUtils.copyProperties(dubboProperties.getProvider(), provider);
        return provider;
    }

    @Bean
    @ConditionalOnProperty(prefix = "spring.dubbo.consumer", name = {"enable"}, havingValue = "true")
    public ConsumerConfig consumerConfig() {
        ConsumerConfig consumer = new ConsumerConfig();
        BeanUtils.copyProperties(dubboProperties.getConsumer(), consumer);
        return consumer;
    }
}


