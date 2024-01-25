package com.example.projectstore.file;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor
public class FolderResponse {
    private List<String> folders;
    private List<String> files;

    @Override
    public String toString() {

        this.folders = this.folders.stream()
                .map(e -> "\"" + e + "\"").collect(Collectors.toList());

        this.files = this.files.stream()
                .map(e -> "\"" + e + "\"").collect(Collectors.toList());

        return "{\n\t\"folders\": [" + String.join(", ", this.folders)
                + "],\n\t\"files\": [" + String.join(", ", this.files) + "] \n}";
    }
}
