package com.tf.base.notification.domain;


public class Notificationobject {
    private Integer id;

    /**
     * 系统id
     */
    private Integer systemid;

    /**
     * 系统ip
     */
    private String systemip;

    /**
     * 系统端口
     */
    private String systemport;

    /**
     * 系统URL
     */
    private String notificationUrl;

    /**
     * @return id
     */
    public Integer getId() {
        return id;
    }

    /**
     * @param id
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * 获取系统id
     *
     * @return systemid - 系统id
     */
    public Integer getSystemid() {
        return systemid;
    }

    /**
     * 设置系统id
     *
     * @param systemid 系统id
     */
    public void setSystemid(Integer systemid) {
        this.systemid = systemid;
    }

    /**
     * 获取系统ip
     *
     * @return systemip - 系统ip
     */
    public String getSystemip() {
        return systemip;
    }

    /**
     * 设置系统ip
     *
     * @param systemip 系统ip
     */
    public void setSystemip(String systemip) {
        this.systemip = systemip;
    }

    /**
     * 获取系统端口
     *
     * @return systemport - 系统端口
     */
    public String getSystemport() {
        return systemport;
    }

    /**
     * 设置系统端口
     *
     * @param systemport 系统端口
     */
    public void setSystemport(String systemport) {
        this.systemport = systemport;
    }

    /**
     * 获取系统URL
     *
     * @return notificationUrl - 系统URL
     */
    public String getNotificationUrl() {
        return notificationUrl;
    }

    /**
     * 设置系统URL
     *
     * @param notificationUrl 系统URL
     */
    public void setNotificationUrl(String notificationUrl) {
        this.notificationUrl = notificationUrl;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", systemid=").append(systemid);
        sb.append(", systemip=").append(systemip);
        sb.append(", systemport=").append(systemport);
        sb.append(", notificationUrl=").append(notificationUrl);
        sb.append("]");
        return sb.toString();
    }
}