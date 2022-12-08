package com.example.springprojecttasc.model.response;

import com.example.springprojecttasc.database.entity.Category;
import com.google.gson.Gson;
import lombok.*;

import javax.persistence.Lob;
import java.math.BigInteger;
import java.util.*;

@Getter
@Setter
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CategoryResponse {
    private BigInteger id;
    private String name;
    private String description;
    private String icon;
    private Date created_at;
    private Date updated_at;
    private int is_root;
    @Lob
    private Set<Category> child;
    @Lob
    private Set<Category> parent;

    public void setChild(String child) {
        Gson gson = new Gson();
        Set<Category> childSet = gson.fromJson(child, Set.class);
        this.child = childSet;
    }

    public void setParent(String parent) {
        Gson gson = new Gson();
        Set<Category> parentSet = gson.fromJson(parent, Set.class);
        this.parent = parentSet;
    }
}
