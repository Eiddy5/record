package org.record.utils;

import org.junit.jupiter.api.Test;
import org.record.menu.Menu;

import java.util.Comparator;
import java.util.List;

public class TreeBuilderTest {


    @Test
    public void testBuilder() {
        Menu root = new Menu("1", "0", "根节点", null);
        Menu child1 = new Menu("2", "1", "子节点1", null);
        Menu child2 = new Menu("3", "1", "子节点2", null);
        Menu grandchild1 = new Menu("4", "2", "孙节点1", null);

        List<Menu> menus = List.of(root, child1, child2, grandchild1);

        TreeBuilder<Menu, String> builder = TreeBuilder.create(
                Menu::getId,
                Menu::getParentId,
                Menu::setChildren,
                Comparator.comparing(Menu::getParentId)
        );

        List<Menu> tree = builder.buildTree(menus);
        System.out.println(tree);
    }

}




