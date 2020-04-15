package com.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Search {
    private int id;
    private int aid;
    private int tid;
    private int click;
    private Area area;
    private Article article;
}
