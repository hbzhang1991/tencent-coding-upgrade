package org.coding.model;

import java.util.List;

public class RequestParameter {
    /**
     * 构建目标的 ref （ commit sha / tag / branch ），若构建计划不使用代码仓库可以忽略 填写master
     */
    private String ref;
    /**
     * 	构建计划的启动参数(可不配置，非必填)
     */
    private List<EnvsItem> envs;

    public String getRef() {
        return ref;
    }

    public void setRef(String ref) {
        this.ref = ref;
    }

    public List<EnvsItem> getEnvs() {
        return envs;
    }

    public void setEnvs(List<EnvsItem> envs) {
        this.envs = envs;
    }
}
