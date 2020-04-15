package com.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Shop {
    private int id;
    private Area area;
    private int aid;
    private int click;
    private List<Comment> comment;
}
