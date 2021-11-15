package com.BootCampProject1.BootCampProject1.users;

import javax.persistence.*;


@PrimaryKeyJoinColumn(name = "CUSTOMER_USER_ID")
@Entity
public class CUSTOMER extends USER {

    private long phoneNumber;

    public long getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(long phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}
