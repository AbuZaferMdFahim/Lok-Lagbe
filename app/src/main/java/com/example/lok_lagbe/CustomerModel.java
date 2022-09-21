package com.example.lok_lagbe;

public class CustomerModel {

    String ProviderName, ProviderPhone, ProviderAddress, ProviderJob, Request, ProviderCharge,Status;

    CustomerModel() {
    }

    public CustomerModel(String providerName, String providerPhone, String providerAddress, String providerJob, String request, String providerCharge, String status) {
        ProviderName = providerName;
        ProviderPhone = providerPhone;
        ProviderAddress = providerAddress;
        ProviderJob = providerJob;
        Request = request;
        ProviderCharge = providerCharge;
        Status = status;
    }

    public String getProviderName() {
        return ProviderName;
    }

    public void setProviderName(String providerName) {
        ProviderName = providerName;
    }

    public String getProviderPhone() {
        return ProviderPhone;
    }

    public void setProviderPhone(String providerPhone) {
        ProviderPhone = providerPhone;
    }

    public String getProviderAddress() {
        return ProviderAddress;
    }

    public void setProviderAddress(String providerAddress) {
        ProviderAddress = providerAddress;
    }

    public String getProviderJob() {
        return ProviderJob;
    }

    public void setProviderJob(String providerJob) {
        ProviderJob = providerJob;
    }

    public String getRequest() {
        return Request;
    }

    public void setRequest(String request) {
        Request = request;
    }

    public String getProviderCharge() {
        return ProviderCharge;
    }

    public void setProviderCharge(String providerCharge) {
        ProviderCharge = providerCharge;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }
}