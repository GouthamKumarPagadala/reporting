/*******************************************************************************
 * Copyright 2013-2019 Qaprosoft (http://www.qaprosoft.com).
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *******************************************************************************/
package com.qaprosoft.zafira.service.bean;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Getter
public class RabbitMQConfigBean {

    private final String host;
    private final int port;
    private final String clientUser;
    private final String clientPasscode;
    private final String systemUser;
    private final String systemPasscode;

    public RabbitMQConfigBean(
            @Value("${rabbitmq.stomp.host}") String host,
            @Value("${rabbitmq.stomp.port}") int port,
            @Value("${rabbitmq.username}") String clientUser,
            @Value("${rabbitmq.password}") String clientPasscode,
            @Value("${rabbitmq.username}") String systemUser,
            @Value("${rabbitmq.password}") String systemPasscode) {
        this.host = host;
        this.port = port;
        this.clientUser = clientUser;
        this.clientPasscode = clientPasscode;
        this.systemUser = systemUser;
        this.systemPasscode = systemPasscode;
    }
}
