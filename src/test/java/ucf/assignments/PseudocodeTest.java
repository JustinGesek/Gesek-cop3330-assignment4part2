/*
 *  UCF COP3330 Fall 2021 Assignment 4 Solution
 *  Copyright 2021 Justin Gesek
 */

package ucf.assignments;
import static org.junit.Assert.*;

import javafx.scene.layout.HBox;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import ucf.assignments.List;
import ucf.assignments.Pseudocode;
import ucf.assignments.ListCollection;

import java.io.*;
import java.util.GregorianCalendar;

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
    @Test
    public void testRemoveTodoList() {
        System.out.println("calling testRemoveTodoList");
        String list = "assignment 6";
        p.addList(list);
        boolean contains1 = false;
        for (List l : p.myLists.lists){
            if (l.getTitle() == list){
                contains1 = true;
            }
        }
        int removeInd = p.myLists.lists.size() - 1;
        myLists.lists.remove( removeInd);
        boolean contains2 = false;
        for (List l : p.myLists.lists){
            if (l.getTitle() == list){
                contains2 = true;
            }
        }

        assertTrue(contains1 == true && contains2 == false);
    }

    //A user shall be able to edit the title of an existing todo list
    @Test
    public void testEditListTitle() {
        System.out.println("calling testEditListTitle");
        String list = "assignment 5";
        List changeList = new List(list);

        assertTrue(changeList.getTitle().equals(list));

        changeList.setTitle("something else");

        assertTrue(!changeList.getTitle().equals(list));
    }

    //A user shall be able to add a new item to an existing todo list
    @Test
    public void testAddItem() {
        System.out.println("calling testAddItem");
        String itemDesc = "newItem";
        int originalSize = p.myLists.lists.get(0).items.size();
        p.myLists.lists.get(0).addItem(new Item(itemDesc, new GregorianCalendar(), true)) ;
        int newSize = p.myLists.lists.get(0).items.size();
        assertTrue((originalSize+1) == newSize);
    }



    //A user shall be able to remove an item from an existing todo list
    @Test
    public void testRemoveItem() {
        System.out.println("calling testRemoveItem");
        String itemDesc = "itemX";
        p.myLists.lists.get(0).addItem(new Item(itemDesc, new GregorianCalendar(), true)) ;
        boolean contains1 = false;
        for (Item i :p.myLists.lists.get(0).items){
            if (i.getDescription() == itemDesc){
                contains1 = true;
            }
        }
        int removeInd = p.myLists.lists.get(0).items.size() - 1;
        myLists.lists.get(0).items.remove( removeInd);
        boolean contains2 = false;
        for (Item i :p.myLists.lists.get(0).items){
            if (i.getDescription() == itemDesc){
                contains2 = true;
            }
        }

        assertTrue(contains1 == true && contains2 == false);
    }


    //A user shall be able to edit the description of an item within an existing todo list
    @Test
    public void testEditItemDesc() {
        System.out.println("calling testEditItemDesc");
        String itemDesc = "assignment 5";
        Item changeItem = new Item(itemDesc, new GregorianCalendar(), true);

        assertTrue(changeItem.getDescription().equals(itemDesc));
        changeItem.setDescription("something else ");

        assertTrue(!changeItem.getDescription().equals(itemDesc));
    }

    //A user shall be able to edit the due date of an item within an existing todo list
    @Test
    public void testEditDueDate() {
        System.out.println("calling testEditDueDate");
        String itemDesc = "assignment 5";
        Item changeItem = new Item(itemDesc, new GregorianCalendar(), true);
        GregorianCalendar oldDate = changeItem.getDueDate();
        assertTrue(changeItem.getDueDate().equals(oldDate));
        changeItem.changeDate(new GregorianCalendar(2000, 20, 1));

        assertTrue(!changeItem.getDueDate().equals(oldDate));
    }

    //A user shall be able to mark an item in a todo list as complete
    @Test
    public void testEditIsComplete() {
        System.out.println("calling testEditIsComplete");
        String itemDesc = "assignment 5";
        Item changeItem = new Item(itemDesc, new GregorianCalendar(), false);
        boolean oldMark = changeItem.getIsCompleted();
        assertTrue(changeItem.getIsCompleted().equals(oldMark));
        changeItem.setIsCompleted(true);

        assertTrue(!changeItem.getIsCompleted().equals(oldMark));
    }

    //A user shall be able to display all of the existing items in a todo list
    @Test
    public void testGetAllITems() {
        System.out.println("calling testEditIsComplete");
        p.addList("TestListFilter");
        p.myLists.lists.get(0).addItem(new Item("item1", new GregorianCalendar(), false));
        p.myLists.lists.get(0).addItem(new Item("item2", new GregorianCalendar(), true));
        p.myLists.lists.get(0).addItem(new Item("item3", new GregorianCalendar(), false));
        p.myLists.lists.get(0).addItem(new Item("item4", new GregorianCalendar(), true));
        p.myLists.lists.get(0).addItem(new Item("item5", new GregorianCalendar(), false));

        p.filterSelection = 0;

        int filteredCount = 0;
        for (int i =0 ; i < p.myLists.lists.get(0).items.size(); i++) {

            Item curItem =myLists.lists.get(0).items.get(i) ;
            Boolean itemCompleted = curItem.getIsCompleted();
            if (!itemCompleted && p.filterSelection == 1 ||
                    itemCompleted && p.filterSelection == 2)
                continue ;
            filteredCount++;
        }
        assertTrue(filteredCount == 5);
    }
    //A user shall be able to display only the incompleted items in a todo list
    @Test
    public void testGetAllIncompleteITems() {
        System.out.println("calling testGetAllIncompleteITems");
        p.addList("TestListFilter");
        p.myLists.lists.get(0).addItem(new Item("item1", new GregorianCalendar(), false));
        p.myLists.lists.get(0).addItem(new Item("item2", new GregorianCalendar(), true));
        p.myLists.lists.get(0).addItem(new Item("item3", new GregorianCalendar(), false));
        p.myLists.lists.get(0).addItem(new Item("item4", new GregorianCalendar(), true));
        p.myLists.lists.get(0).addItem(new Item("item5", new GregorianCalendar(), false));

        p.filterSelection = 2;

        int filteredCount = 0;
        for (int i =0 ; i < p.myLists.lists.get(0).items.size(); i++) {

            Item curItem =myLists.lists.get(0).items.get(i) ;
            Boolean itemCompleted = curItem.getIsCompleted();
            if (!itemCompleted && p.filterSelection == 1 ||
                    itemCompleted && p.filterSelection == 2)
                continue ;
            filteredCount++;
        }
        assertTrue(filteredCount ==3 );
    }
    //A user shall be able to display only the completed items in a todo list
    @Test
    public void testGetAllCompleteITems() {
        System.out.println("calling testGetAllCompleteITems");
        p.addList("TestListFilter");
        p.myLists.lists.get(0).addItem(new Item("item1", new GregorianCalendar(), false));
        p.myLists.lists.get(0).addItem(new Item("item2", new GregorianCalendar(), true));
        p.myLists.lists.get(0).addItem(new Item("item3", new GregorianCalendar(), false));
        p.myLists.lists.get(0).addItem(new Item("item4", new GregorianCalendar(), true));
        p.myLists.lists.get(0).addItem(new Item("item5", new GregorianCalendar(), false));

        p.filterSelection = 1;

        int filteredCount = 0;
        for (int i =0 ; i < p.myLists.lists.get(0).items.size(); i++) {

            Item curItem =myLists.lists.get(0).items.get(i) ;
            Boolean itemCompleted = curItem.getIsCompleted();
            if (!itemCompleted && p.filterSelection == 1 ||
                    itemCompleted && p.filterSelection == 2)
                continue ;
            filteredCount++;
        }
        assertTrue(filteredCount ==2 );
    }

    //A user shall be able to save all of the items in a single todo list to external storage
    @Test
    public void testSaveList() throws IOException {
        p.listBeingViewed = 0;
        p.saveFile("newList.txt");

        File file = new File(
                "newList.txt");

        BufferedReader br
                = new BufferedReader(new FileReader(file));

        int lineNum = 0;
        String st;
        while ((st = br.readLine()) != null) {
            lineNum ++;
        }
        assertTrue(lineNum == p.myLists.lists.get(0).items.size());

    }

    //A user shall be able to save all of the items across all of the todo lists to external storage
    @Test
    public void testSaveAllLists() throws IOException {
        for (int i = 0; i < p.myLists.lists.size(); i ++) {
            p.listBeingViewed = i;
            String fileName = p.myLists.lists.get(i).getTitle() + ".txt";
            p.saveFile( fileName);

            File file = new File(
                    fileName);

            BufferedReader br
                    = new BufferedReader(new FileReader(file));

            int lineNum = 0;
            String st;
            while ((st = br.readLine()) != null) {
                lineNum++;
            }
            assertTrue(lineNum == p.myLists.lists.get(i).items.size());
        }
    }
    //A user shall be able to load a single todo list that was previously saved to external storage
    @Test
    public void testLoadList() throws IOException {
        p.addList("Lista");
        int numListsOrig = p.myLists.lists.size();
        p.myLists.lists.get(p.myLists.lists.size() - 1).addItem(new Item("Item1", new GregorianCalendar(), false));
        p.myLists.lists.get(p.myLists.lists.size() - 1).addItem(new Item("Item2", new GregorianCalendar(), false));

        p.listBeingViewed = p.myLists.lists.size() - 1;
        p.saveFile("testSave.txt");

        p.loadFile("testSave.txt");
        int numListNow = p.myLists.lists.size();
        assertTrue(numListNow == (numListsOrig+1));

    }

    //A user shall be able to load multiple todo lists that were previous saved to external storage
    @Test
    public void testLoadMultipleLists() throws IOException {
        String[] listNames = {"Listb", "listc", "listd", "liste"};
        for (String listName : listNames) {
            p.addList(listName);
            int numListsOrig = p.myLists.lists.size();
            p.myLists.lists.get(p.myLists.lists.size() - 1).addItem(new Item("Item1", new GregorianCalendar(), false));
            p.myLists.lists.get(p.myLists.lists.size() - 1).addItem(new Item("Item2", new GregorianCalendar(), false));

            p.listBeingViewed = p.myLists.lists.size() - 1;
            p.saveFile("testSave.txt");

            p.loadFile("testSave.txt");
            int numListNow = p.myLists.lists.size();
            assertTrue(numListNow == (numListsOrig + 1));
        }

    }
}
