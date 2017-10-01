package com.epam.tamasknizner.springadvancedtraining.service.settings;

import org.springframework.stereotype.Component;

@Component
public class BookingSettings {
    private float vipExtraChargePercent = 100.0f;
    private float highRatedExtraChargePercent = 20.0f;

    public float getVipExtraChargePercent() {
        return vipExtraChargePercent;
    }

    public void setVipExtraChargePercent(float vipExtraChargePercent) {
        this.vipExtraChargePercent = vipExtraChargePercent;
    }

    public float getHighRatedExtraChargePercent() {
        return highRatedExtraChargePercent;
    }

    public void setHighRatedExtraChargePercent(float highRatedExtraChargePercent) {
        this.highRatedExtraChargePercent = highRatedExtraChargePercent;
    }
}
