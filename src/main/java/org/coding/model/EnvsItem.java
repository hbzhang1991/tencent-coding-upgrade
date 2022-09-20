package org.coding.model;

/**
 * 构建计划启动参数
 */
public class EnvsItem {
    /**
     * 构建计划的启动参数的名称
     */
    private String name;
    /**
     * 	构建计划的启动值
     */
    private String value;
    /**
     * 是否将启动参数设置为保密,设置保密后日志中不可见。 1 为保密，0 为明文 默认：明文
     */
    private Integer sensitive = 0;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public Integer getSensitive() {
        return sensitive;
    }

    public void setSensitive(Integer sensitive) {
        this.sensitive = sensitive;
    }
}
