package com.example.lok_lagbe;

public class ProviderModel {

    String CustomerName,CustomerAddress,CustomerPhone,Request,Status,Charge;

    ProviderModel(){}

    public ProviderModel(String customerName, String customerAddress, String customerPhone, String request, String status, String charge) {
        CustomerName = customerName;
        CustomerAddress = customerAddress;
        CustomerPhone = customerPhone;
        Request = request;
        Status = status;
        Charge = charge;
    }

    public String getCustomerName() {
        return CustomerName;
    }

    public void setCustomerName(String customerName) {
        CustomerName = customerName;
    }

    public String getCustomerAddress() {
        return CustomerAddress;
    }

    public void setCustomerAddress(String customerAddress) {
        CustomerAddress = customerAddress;
    }

    public String getCustomerPhone() {
        return CustomerPhone;
    }

    public void setCustomerPhone(String customerPhone) {
        CustomerPhone = customerPhone;
    }

    public String getRequest() {
        return Request;
    }

    public void setRequest(String request) {
        Request = request;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }

    public String getCharge() {
        return Charge;
    }

    public void setCharge(String charge) {
        Charge = charge;
    }
}
