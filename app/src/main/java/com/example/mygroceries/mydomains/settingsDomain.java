package com.example.mygroceries.mydomains;

public class settingsDomain {
    String personimg,personemail, personphone,personaddress;
    public settingsDomain(){}

    public settingsDomain(String personimg, String personemail, String personphone, String personaddress) {
        this.personimg = personimg;
        this.personemail = personemail;
        this.personphone = personphone;
        this.personaddress = personaddress;
    }

    public String getPersonimg() {
        return personimg;
    }

    public void setPersonimg(String personimg) {
        this.personimg = personimg;
    }

    public String getPersonemail() {
        return personemail;
    }

    public void setPersonemail(String personemail) {
        this.personemail = personemail;
    }

    public String getPersonphone() {
        return personphone;
    }

    public void setPersonphone(String personphone) {
        this.personphone = personphone;
    }

    public String getPersonaddress() {
        return personaddress;
    }

    public void setPersonaddress(String personaddress) {
        this.personaddress = personaddress;
    }
}
