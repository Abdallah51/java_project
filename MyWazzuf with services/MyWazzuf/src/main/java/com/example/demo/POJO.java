/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.demo;

/**
 *
 * @author dell
*/
public class POJO {
   private String Title; 
   private String Company;
   private String Location;
   private String Type;
   private String Level;
   private String Exp;
   private String Country;
   private String[] Skills;

    public POJO() {
    }

    public POJO(String Title, String Company, String Location, String Type, String Level, String Exp, String Country, String[] Skills) {
        this.Title = Title;
        this.Company = Company;
        this.Location = Location;
        this.Type = Type;
        this.Level = Level;
        this.Exp = Exp;
        this.Country = Country;
        this.Skills = Skills;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String Title) {
        this.Title = Title;
    }

    public String getCompany() {
        return Company;
    }

    public void setCompany(String Company) {
        this.Company = Company;
    }

    public String getLocation() {
        return Location;
    }

    public void setLocation(String Location) {
        this.Location = Location;
    }

    public String getType() {
        return Type;
    }

    public void setType(String Type) {
        this.Type = Type;
    }

    public String getLevel() {
        return Level;
    }

    public void setLevel(String Level) {
        this.Level = Level;
    }

    public String getExp() {
        return Exp;
    }

    public void setExp(String Exp) {
        this.Exp = Exp;
    }

    public String getCountry() {
        return Country;
    }

    public void setCountry(String Country) {
        this.Country = Country;
    }

    public String[] getSkills() {
        return Skills;
    }

    public void setSkills(String[] Skills) {
        this.Skills = Skills;
    }
  
 
}

