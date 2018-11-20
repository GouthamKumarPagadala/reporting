/*******************************************************************************
 * Copyright 2013-2018 QaProSoft (http://www.qaprosoft.com).
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *******************************************************************************/
package com.qaprosoft.zafira.models.dto.tag;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import java.util.List;

@JsonInclude(Include.NON_NULL)
public class TestRailIntegrationType extends IntegrationInfoType
{

    private static final long serialVersionUID = 1948601171483936535L;

    private String testRunName;
    private String testRunStatus;
    private String testRunComment;
    private String testRunAppVersion;
    private Integer testRunElapsed;
    private List<String> defects;
    private Long createdAfter;
    private String createdBy;
    private String milestone;

    public String getTestRunName() {
        return testRunName;
    }

    public void setTestRunName(String testRunName) {
        this.testRunName = testRunName;
    }

    public String getTestRunStatus() {
        return testRunStatus;
    }

    public void setTestRunStatus(String testRunStatus) {
        this.testRunStatus = testRunStatus;
    }

    public String getTestRunComment() {
        return testRunComment;
    }

    public void setTestRunComment(String testRunComment) {
        this.testRunComment = testRunComment;
    }

    public String getTestRunAppVersion() {
        return testRunAppVersion;
    }

    public void setTestRunAppVersion(String testRunAppVersion) {
        this.testRunAppVersion = testRunAppVersion;
    }

    public Integer getTestRunElapsed() {
        return testRunElapsed;
    }

    public void setTestRunElapsed(Integer testRunElapsed) {
        this.testRunElapsed = testRunElapsed;
    }

    public List<String> getDefects() {
        return defects;
    }

    public void setDefects(List<String> defects) {
        this.defects = defects;
    }

    public Long getCreatedAfter() {
        return createdAfter;
    }

    public void setCreatedAfter(Long createdAfter) {
        this.createdAfter = createdAfter;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public String getMilestone() {
        return milestone;
    }

    public void setMilestone(String milestone) {
        this.milestone = milestone;
    }

 }
