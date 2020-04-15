package com.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LocalHot {
    private int id;
    private int aid;
    private int tid;
    private String city;
    private int click;
}
