package com.BootCampProject1.BootCampProject1.users;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

@PrimaryKeyJoinColumn(name = "SELLER_USER_ID")
@Entity
public class SELLER extends USER {

    @NotNull(message = "GST cannot be null")
    private String GST;
//    @NotNull(message = "GST cannot be null")
    private long companyContact;

    @Email
    @NotNull
    private String email;

    @Override
    public String getEmail() {
        return email;
    }

    @Override
    public void setEmail(String email) {
        this.email = email;
    }

//    @NotNull(message = "GST cannot be null")

    @Column(unique = true)
    private String companyName;

    @Column(unique = true)
    private String companyAddress;

    public String getCompanyAddress() {
        return companyAddress;
    }

    public void setCompanyAddress(String companyAddress) {
        this.companyAddress = companyAddress;
    }

    private boolean isValidCompanyName;

    private boolean isValidGST;

    public boolean isValidGST() {
        return isValidGST;
    }

    public void setValidGST(boolean validGST) {
        isValidGST = validGST;
    }

    public boolean isValidCompanyName() {
        return isValidCompanyName;
    }

    public void setValidCompanyName(boolean validCompanyName) {
        isValidCompanyName = validCompanyName;
    }

    public String getGST() {
        return GST;
    }

    public void setGST(String GST) {
        this.GST = GST;
    }

    public long getCompanyContact() {
        return companyContact;
    }

    public void setCompanyContact(long companyContact) {
        this.companyContact = companyContact;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }
}
