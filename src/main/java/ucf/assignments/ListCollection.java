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
class ListCollection {
    private String title;
    public ArrayList< List> lists;

    public ListCollection() {
        lists = new ArrayList< List>();
    }

    public void addList(String listName) {
        lists.add( new List(listName ) );
    }
}
