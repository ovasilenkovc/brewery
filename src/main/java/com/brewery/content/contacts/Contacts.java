package com.brewery.content.contacts;

import java.util.Set;

public class Contacts {

    private String address;

    private String email;

    private String phone;

    private String vk;

    private String facebook;

    private String twitter;

    private Set<String> channels;

    public Contacts() {
    }


    public Contacts(String address, String email, String phone, String vk, String facebook, String twitter, Set<String> channels) {
        this.address = address;
        this.email = email;
        this.phone = phone;
        this.vk = vk;
        this.facebook = facebook;
        this.twitter = twitter;
        this.channels = channels;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Set<String> getChannels() {
        return channels;
    }

    public void setChannels(Set<String> channels) {
        this.channels = channels;
    }

    public String getVk() {
        return vk;
    }

    public void setVk(String vk) {
        this.vk = vk;
    }

    public String getFacebook() {
        return facebook;
    }

    public void setFacebook(String facebook) {
        this.facebook = facebook;
    }

    public String getTwitter() {
        return twitter;
    }

    public void setTwitter(String twitter) {
        this.twitter = twitter;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Contacts contacts = (Contacts) o;

        if (address != null ? !address.equals(contacts.address) : contacts.address != null) return false;
        if (email != null ? !email.equals(contacts.email) : contacts.email != null) return false;
        if (phone != null ? !phone.equals(contacts.phone) : contacts.phone != null) return false;
        return channels != null ? channels.equals(contacts.channels) : contacts.channels == null;
    }

    @Override
    public int hashCode() {
        int result = address != null ? address.hashCode() : 0;
        result = 31 * result + (email != null ? email.hashCode() : 0);
        result = 31 * result + (phone != null ? phone.hashCode() : 0);
        result = 31 * result + (channels != null ? channels.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Contacts{" +
                "address='" + address + '\'' +
                ", email='" + email + '\'' +
                ", phone='" + phone + '\'' +
                ", channels=" + channels +
                '}';
    }
}
