package com.freetube.JavaFreetube.Models.DriveModels;

import org.springframework.beans.factory.annotation.Value;

public class DriveAuthGetter
{
    public String token_uri;

    public String refresh_token;
    public String client_id;
    public String client_secret;

    public DriveAuthGetter(String token_uri, String refresh_token, String client_id, String client_secret) {
        this.token_uri = token_uri;
        this.refresh_token = refresh_token;
        this.client_id = client_id;
        this.client_secret = client_secret;
    }

    public DriveAuthGetter() {
    }

    public String getClient_id() {
        return client_id;
    }

    public void setClient_id(String client_id) {
        this.client_id = client_id;
    }

    public String getClient_secret() {
        return client_secret;
    }

    public void setClient_secret(String client_secret) {
        this.client_secret = client_secret;
    }

    public String getToken_uri() {
        return token_uri;
    }

    public void setToken_uri(String token_uri) {
        this.token_uri = token_uri;
    }

    public String getRefresh_token() {
        return refresh_token;
    }

    public void setRefresh_token(String refresh_token) {
        this.refresh_token = refresh_token;
    }
}
