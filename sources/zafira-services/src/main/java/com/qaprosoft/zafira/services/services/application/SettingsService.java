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
package com.qaprosoft.zafira.services.services.application;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.qaprosoft.zafira.dbaccess.dao.mysql.application.SettingsMapper;
import com.qaprosoft.zafira.models.db.Setting;
import com.qaprosoft.zafira.models.db.Setting.SettingType;

@Service
public class SettingsService {

    //private static final String ERR_MSG_MULTIPLE_TOOLS_UPDATE = "Unable to update settings for multiple tools at once";
    //private static final String ERR_MSG_NOT_EXISTS_SETTING_UPDATE = "Unable to update not existing setting '%s'";
    //private static final String ERR_MSG_INCORRECT_TOOL_SETTING_UPDATE = "Unable to update '%s': setting does not belong to specified tool '%s'";

    private final SettingsMapper settingsMapper;
    /*private final IntegrationService integrationService;
    private final CryptoService cryptoService;
    private final ElasticsearchService elasticsearchService;
    private final EventPushService<ReinitEventMessage> eventPushService;*/

    public SettingsService(SettingsMapper settingsMapper/*,
            @Lazy IntegrationService integrationService,
            @Lazy CryptoService cryptoService,
            ElasticsearchService elasticsearchService,
            EventPushService<ReinitEventMessage> eventPushService*/) {
        this.settingsMapper = settingsMapper;
        /*this.integrationService = integrationService;
        this.cryptoService = cryptoService;
        this.elasticsearchService = elasticsearchService;
        this.eventPushService = eventPushService;*/
    }

    @Transactional(readOnly = true)
    public Setting getSettingByName(String name) {
        return settingsMapper.getSettingByName(name);
    }

    @Transactional(readOnly = true)
    public Setting getSettingByType(SettingType type) {
        return settingsMapper.getSettingByName(type.name());
    }

    /*@Transactional(readOnly = true)
    public List<Setting> getSettingsByEncrypted(boolean isEncrypted) {
        return settingsMapper.getSettingsByEncrypted(isEncrypted);
    }*/

    /*@Transactional(readOnly = true)
    public Map<Tool, Boolean> getToolsStatuses() {
        return Arrays.stream(Tool.values())
                .collect(Collectors.toMap(tool -> tool, tool -> integrationService.getServiceByTool(tool).isEnabledAndConnected()));
    }*/

    /*public List<Setting> getSettingsByTool(String toolName, boolean decrypt) {
        List<Setting> result;
        if ("ELASTICSEARCH".equals(toolName)) {
            result = elasticsearchService.getSettings();
        } else {
            Tool tool = Tool.valueOf(toolName);
            result = getSettingsByTool(tool);
            if (decrypt) {
                if (!tool.isDecrypt()) {
                    throw new ForbiddenOperationException();
                }
                for (Setting setting : result) {
                    if (setting.isEncrypted()) {
                        setting.setValue(cryptoService.decrypt(setting.getValue()));
                        setting.setEncrypted(false);
                    }
                }
            }
        }
        return result;
    }*/

    /*@Transactional(readOnly = true)
    public List<Setting> getSettingsByTool(Tool tool) {
        return settingsMapper.getSettingsByTool(tool);
    }*/

    @Transactional(readOnly = true)
    public List<Setting> getAllSettings() {
        return settingsMapper.getAllSettings();
    }

    /*public boolean isConnected(Tool tool) {
        return tool != null && integrationService.getServiceByTool(tool).isEnabledAndConnected();
    }*/

    @Transactional(readOnly = true)
    public String getSettingValue(Setting.SettingType type) {
        return getSettingByName(type.name()).getValue();
    }

    /*@Transactional(rollbackFor = Exception.class)
    public ConnectedToolType updateSettings(List<Setting> settings) {
        ConnectedToolType connectedTool = null;
        if (settings != null && !settings.isEmpty()) {
            Tool tool = settings.get(0).getTool();
            validateSettingsOwns(settings, tool);
            settings.forEach(setting -> {
                decryptSetting(setting);
                updateIntegrationSetting(setting);
            });
            notifyToolReinitiated(tool, TenancyContext.getTenantName());
            connectedTool = new ConnectedToolType();
            connectedTool.setName(tool);
            connectedTool.setSettingList(settings);
            connectedTool.setConnected(integrationService.getServiceByTool(tool).isEnabledAndConnected());
        }
        return connectedTool;
    }*/

    @Transactional(rollbackFor = Exception.class)
    public Setting updateSetting(Setting setting) {
        //setting.setValue(StringUtils.isBlank(setting.getValue() != null ? setting.getValue().trim() : null) ? null : setting.getValue());
        settingsMapper.updateSetting(setting);
        return setting;
    }

    /*@Transactional(rollbackFor = Exception.class)
    public void updateIntegrationSetting(Setting setting) {
        setting.setValue(StringUtils.isBlank(setting.getValue() != null ? setting.getValue().trim() : null) ? null : setting.getValue());
        settingsMapper.updateIntegrationSetting(setting);
    }*/

    /*@Transactional(rollbackFor = Exception.class)
    public ConnectedToolType createSettingFile(byte[] fileBytes, String originalFileName, String name, Tool tool) {
        Setting dbSetting = getSettingByNameSafely(name, tool);
        if (dbSetting != null) {
            dbSetting.setFile(fileBytes);
            dbSetting.setValue(originalFileName);
            updateSetting(dbSetting);
            integrationService.getServiceByTool(dbSetting.getTool()).init();
            notifyToolReinitiated(dbSetting.getTool(), TenancyContext.getTenantName());
        }
        ConnectedToolType connectedToolType = new ConnectedToolType();
        connectedToolType.setName(dbSetting.getTool());
        connectedToolType.setSettingList(Collections.singletonList(dbSetting));
        connectedToolType.setConnected(integrationService.getServiceByTool(dbSetting.getTool()).isEnabledAndConnected());
        return connectedToolType;
    }*/

    /*@Transactional(rollbackFor = Exception.class)
    public void reEncrypt() {
        List<Setting> settings = getSettingsByEncrypted(true);
        settings.forEach(setting -> {
            String decValue = cryptoService.decrypt(setting.getValue());
            setting.setValue(decValue);
        });
        cryptoService.regenerateKey();
        settings.forEach(setting -> {
            String encValue = cryptoService.encrypt(setting.getValue());
            setting.setValue(encValue);
            updateSetting(setting);
        });
    }*/

    @Transactional(readOnly = true)
    public String getPostgresVersion() {
        return settingsMapper.getPostgresVersion();
    }

    /**
     * Sends message to broker to notify about changed integration.
     *
     * @param tool that was re-initiated
     * @param tenant whose integration was updated
     */
    /*public void notifyToolReinitiated(Tool tool, String tenant) {
        eventPushService.convertAndSend(EventPushService.Type.SETTINGS, new ReinitEventMessage(tenant, tool));
        initIntegration(tool, tenant);
    }*/

    /*@RabbitListener(queues = "#{settingsQueue.name}")
    public void process(Message message) {
        ReinitEventMessage rm = new Gson().fromJson(new String(message.getBody()), ReinitEventMessage.class);
        if (!eventPushService.isSettingQueueConsumer(message)) {
            initIntegration(rm.getTool(), rm.getTenancy());
        }
    }*/

    /*private void initIntegration(Tool tool, String tenancyName) {
        Integration<?> integration = integrationService.getServiceByTool(tool);
        if (integration != null) {
            TenancyContext.setTenantName(tenancyName);
            integration.init();
        }
    }*/

    /*private void decryptSetting(Setting setting) {
        Setting dbSetting = getSettingByNameSafely(setting.getName(), setting.getTool());
        setting.setEncrypted(dbSetting.isEncrypted());
        if (dbSetting.isValueForEncrypting()) {
            if (StringUtils.isBlank(setting.getValue())) {
                setting.setEncrypted(false);
            } else {
                if (!setting.getValue().equals(dbSetting.getValue())) {
                    setting.setValue(cryptoService.encrypt(setting.getValue()));
                    setting.setEncrypted(true);
                }
            }
        }
    }*/

    /*private Setting getSettingByNameSafely(String name, Tool tool) {
        Setting setting = getSettingByName(name);
        if (setting == null) {
            throw new ForbiddenOperationException(String.format(ERR_MSG_NOT_EXISTS_SETTING_UPDATE, name));
        }
        if (!setting.getTool().equals(tool)) {
            throw new ForbiddenOperationException(String.format(ERR_MSG_INCORRECT_TOOL_SETTING_UPDATE, setting.getName(), tool));
        }
        return setting;
    }*/

    /*private void validateSettingsOwns(List<Setting> settings, Tool tool) {
        settings.forEach(setting -> {
            if (!tool.equals(setting.getTool())) {
                throw new ForbiddenOperationException(ERR_MSG_MULTIPLE_TOOLS_UPDATE);
            }
        });
    }*/

}
