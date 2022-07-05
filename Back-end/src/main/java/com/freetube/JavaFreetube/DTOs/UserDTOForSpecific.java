package com.freetube.JavaFreetube.DTOs;

import java.util.Date;

public class UserDTOForSpecific
{
    public int id_user;
    public String username;
    public Date createdAt;
    public Long views;
    public Long subscriptions;
    public Long subscribers;
    public String imageURL;
    public String role;

    public UserDTOForSpecific() {
    }

    public UserDTOForSpecific(int id_user, String username, Date createdAt, Long views, Long subscriptions, Long subscribers, String imageURL, String role) {
        this.id_user = id_user;
        this.username = username;
        this.createdAt = createdAt;
        this.views = views;
        this.subscriptions = subscriptions;
        this.subscribers = subscribers;
        this.imageURL = imageURL;
        this.role = role;
    }

    public int getId_user() {
        return id_user;
    }

    public void setId_user(int id_user) {
        this.id_user = id_user;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Long getViews() {
        return views;
    }

    public void setViews(Long views) {
        this.views = views;
    }

    public Long getSubscriptions() {
        return subscriptions;
    }

    public void setSubscriptions(Long subscriptions) {
        this.subscriptions = subscriptions;
    }

    public Long getSubscribers() {
        return subscribers;
    }

    public void setSubscribers(Long subscribers) {
        this.subscribers = subscribers;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
