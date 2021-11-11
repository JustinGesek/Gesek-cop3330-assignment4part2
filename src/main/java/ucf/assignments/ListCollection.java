/*
 *  UCF COP3330 Fall 2021 Assignment 4 Solution
 *  Copyright 2021 Justin Gesek
 */

package ucf.assignments;

import java.util.*;

import java.util.Date;
import java.util.Map;

//     -title
//     -lists
//     +Lists(title)
//     +display()
//     +setTitle()
//     +addList()
//     +removeList()
//     +getList()
//     +delete()
//     +save()
//     +load()
//     +getTitle()
public class ListCollection {
    private String title;
    public ArrayList< List> lists;

    public ListCollection() {
        lists = new ArrayList< List>();
    }

    public void addList(String listName) {
        lists.add( new List(listName ) );
    }
}
