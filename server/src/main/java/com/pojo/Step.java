package com.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Step {
    private int id;
    private int uid;
    private int active;
    private int isForm;
    private int isFile;
}
