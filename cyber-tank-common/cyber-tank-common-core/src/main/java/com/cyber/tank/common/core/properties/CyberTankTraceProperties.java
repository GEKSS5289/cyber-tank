package com.cyber.tank.common.core.properties;

import com.cyber.tank.common.core.constant.SecurityConstants;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "cyber.tank.trace")
public class CyberTankTraceProperties {

    private boolean enabled = true;
    private String headerName = SecurityConstants.TRACE_ID_HEADER;
    private String mdcKey = SecurityConstants.LOG_TRACE_ID;
    private int order = Integer.MIN_VALUE;

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public String getHeaderName() {
        return headerName;
    }

    public void setHeaderName(String headerName) {
        this.headerName = headerName;
    }

    public String getMdcKey() {
        return mdcKey;
    }

    public void setMdcKey(String mdcKey) {
        this.mdcKey = mdcKey;
    }

    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }
}
