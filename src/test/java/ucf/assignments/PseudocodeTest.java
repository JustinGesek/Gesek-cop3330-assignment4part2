package ucf.assignments;
import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import ucf.assignments.List;
import ucf.assignments.Pseudocode;
import ucf.assignments.ListCollection;

public class PseudocodeTest {
    ListCollection myLists;
    Pseudocode p;

    @Before
    public void init() {
        System.out.println("calling init");
        p = new Pseudocode();
        myLists = new ListCollection();
        List l1 = new List("assignment 1");
        List l2 = new List("assignment 2");
        List l3 = new List("assignment 3");
        List l4 = new List("assignment 4");
        myLists = new ListCollection();
        myLists.lists.add(l1);
        myLists.lists.add(l2);
        myLists.lists.add(l3);
        myLists.lists.add(l4);
        p.myLists = myLists;
    }

    @After
    public void teardown() {
        System.out.println("calling teardown");
        myLists = null;
        p = null;
    }

    //A user shall be able to add a new todo list
    @Test
    public void testAddNewTodoListNotAdded() {
        System.out.println("calling testAddNewTodoList");
        String list = "assignment 1";
        int originalSize = p.myLists.lists.size();
        p.addList(list);
        int newSize = myLists.lists.size();
        assertTrue(originalSize == newSize);
    }

    //A user shall be able to add a new todo list
    @Test
    public void testAddNewTodoListAdded() {
        System.out.println("calling testAddNewTodoList");
        String list = "assignment 5";
        int originalSize = p.myLists.lists.size();
        p.addList(list);
        int newSize = myLists.lists.size();
        assertTrue((originalSize+1)== newSize);
    }

    //A user shall be able to remove an existing todo list
    //A user shall be able to edit the title of an existing todo list
    //A user shall be able to add a new item to an existing todo list
    //A user shall be able to remove an item from an existing todo list
    //A user shall be able to edit the description of an item within an existing todo list
    //A user shall be able to edit the due date of an item within an existing todo list
    //A user shall be able to mark an item in a todo list as complete
    //A user shall be able to display all of the existing items in a todo list
    //A user shall be able to display only the incompleted items in a todo list
    //A user shall be able to display only the completed items in a todo list
    //A user shall be able to save all of the items in a single todo list to external storage
    //A user shall be able to save all of the items across all of the todo lists to external storage
    //A user shall be able to load a single todo list that was previously saved to external storage
    //A user shall be able to load multiple todo lists that were previous saved to external storage

}
