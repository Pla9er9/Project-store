package com.example.projectstore.release;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Release {
    String filename;
    byte[] bytes;
}
