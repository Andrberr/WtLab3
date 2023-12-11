package com.es.core.entity.order.dto;

import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.Pattern;

public class ClientOrderDto {
    @NotEmpty(message = "Value is required")
    private String firstName;
    @NotEmpty(message = "Value is required")
    private String lastName;
    @NotEmpty(message = "Value is required")
    private String deliveryAddress;
    @NotEmpty(message = "Value is required")
    @Pattern(regexp = "\\+\\d{12}", message = "Wrong format of number")
    private String contactcarNo;
    private String additionalInformation;

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getDeliveryAddress() {
        return deliveryAddress;
    }

    public void setDeliveryAddress(String deliveryAddress) {
        this.deliveryAddress = deliveryAddress;
    }

    public String getContactcarNo() {
        return contactcarNo;
    }

    public void setContactcarNo(String contactcarNo) {
        this.contactcarNo = contactcarNo;
    }

    public String getAdditionalInformation() {
        return additionalInformation;
    }

    public void setAdditionalInformation(String additionalInformation) {
        this.additionalInformation = additionalInformation;
    }
}
