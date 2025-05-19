package org.record.menu;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Menu {
    private String id;
    private String parentId;
    private String name;
    private List<Menu> children;
}
