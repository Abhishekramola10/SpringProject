package com.BootCampProject1.BootCampProject1.users;

import javax.persistence.*;

@Entity
public class ADDRESS {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int ID;
    private String CITY;
    private String STATE;
    private String COUNTRY;
    private String ADDRESS_LINE;
    private int ZIP_CODE;
    private String LABEL;
    @ManyToOne
    @JoinColumn(name = "USER_ID")
    private USER user;

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getCITY() {
        return CITY;
    }

    public void setCITY(String CITY) {
        this.CITY = CITY;
    }

    public String getSTATE() {
        return STATE;
    }

    public void setSTATE(String STATE) {
        this.STATE = STATE;
    }

    public String getCOUNTRY() {
        return COUNTRY;
    }

    public void setCOUNTRY(String COUNTRY) {
        this.COUNTRY = COUNTRY;
    }

    public String getADDRESS_LINE() {
        return ADDRESS_LINE;
    }

    public void setADDRESS_LINE(String ADDRESS_LINE) {
        this.ADDRESS_LINE = ADDRESS_LINE;
    }

    public int getZIP_CODE() {
        return ZIP_CODE;
    }

    public void setZIP_CODE(int ZIP_CODE) {
        this.ZIP_CODE = ZIP_CODE;
    }

    public String getLABEL() {
        return LABEL;
    }

    public void setLABEL(String LABEL) {
        this.LABEL = LABEL;
    }

    public USER getUser() {
        return user;
    }

    public void setUser(USER user) {
        this.user = user;
    }
}
