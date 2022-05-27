package com.freetube.JavaFreetube.Models.DriveModels;

public class Files {
    public String id;
    public String kind;
    public String name;
    public String mimeType;

    public Files(String id, String kind, String name, String mimeType) {
        this.id = id;
        this.kind = kind;
        this.name = name;
        this.mimeType = mimeType;
    }

    public Files() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getKind() {
        return kind;
    }

    public void setKind(String kind) {
        this.kind = kind;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMimeType() {
        return mimeType;
    }

    public void setMimeType(String mimeType) {
        this.mimeType = mimeType;
    }
}
