package org.example.mypackage;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Grid {
    public Element[] elements;


    public Node[] nodes;

//    public Element[] getElements() {
//        return elements;
//    }
//
//    public Node[] getNodes() {
//        return nodes;
//    }

    public Grid(int nE, int nN) {
        elements = new Element[nE];
        nodes = new Node[nN];
    }
}
