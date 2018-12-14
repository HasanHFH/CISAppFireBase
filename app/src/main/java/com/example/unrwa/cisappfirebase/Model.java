package com.example.unrwa.cisappfirebase;

public class Model {
    String id,name,age,phone,profilepic;

    public Model(String id, String name, String age, String phone) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.phone = phone;
    }

    public Model() {

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getProfilepic() {
        return profilepic;
    }

    public void setProfilepic(String profilepic) {
        this.profilepic = profilepic;
    }

    public Model(String id, String name, String age, String phone, String profilepic) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.phone = phone;
        this.profilepic = profilepic;
    }

    public Model(String trim, String s) {
    }
}
