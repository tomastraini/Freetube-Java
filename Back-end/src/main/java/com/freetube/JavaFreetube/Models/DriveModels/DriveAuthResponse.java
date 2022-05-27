package com.freetube.JavaFreetube.Models.DriveModels;

public class DriveAuthResponse
{
    public Boolean success;
    public String access_token;

    public DriveAuthResponse(Boolean success, String access_token)
    {
        this.success = success;
        this.access_token = access_token;
    }

    public DriveAuthResponse() {
    }

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public String getAccess_token() {
        return access_token;
    }

    public void setAccess_token(String access_token) {
        this.access_token = access_token;
    }
}
