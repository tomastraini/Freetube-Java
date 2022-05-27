package com.freetube.JavaFreetube.Models.DriveModels;

import java.util.List;

public class DriveVideos
{
    public String kind;
    public String incompleteSearch;
    public List<Files> files;

    public DriveVideos(String kind, String incompleteSearch, List<Files> files) {
        this.kind = kind;
        this.incompleteSearch = incompleteSearch;
        this.files = files;
    }

    public DriveVideos() {
    }

    public String getKind() {
        return kind;
    }

    public void setKind(String kind) {
        this.kind = kind;
    }

    public String getIncompleteSearch() {
        return incompleteSearch;
    }

    public void setIncompleteSearch(String incompleteSearch) {
        this.incompleteSearch = incompleteSearch;
    }

    public List<Files> getFiles() {
        return files;
    }

    public void setFiles(List<Files> files) {
        this.files = files;
    }
}

