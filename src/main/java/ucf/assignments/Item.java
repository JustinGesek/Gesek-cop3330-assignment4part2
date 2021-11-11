package ucf.assignments;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.GregorianCalendar;

//+Item(description, dueDate, isCompleted)
//    +display()
//    +setDescription()
//    +setDueDate()
//    +setIsCompleted()
//    +delete()
//    +save()
//    +load()
public class Item {
    private String description;
    private GregorianCalendar dueDate;
    private Boolean isCompleted;
    public Item(String description, GregorianCalendar dueDate, Boolean isCompleted)
    {
        this.description = description;
        this.dueDate = dueDate;
        this.isCompleted = isCompleted;
    }

    public void setIsCompleted(Boolean completed)
    {
        isCompleted = completed;
    }

    public String getDescription()
    {
        return description;
    }

    public void setDescription(String newDesc){
        description = newDesc;
    }
    public GregorianCalendar getDueDate()
    {

        return dueDate;

    }

    public void changeDate( GregorianCalendar newDate){
        this.dueDate = newDate;
    }
    public Boolean getIsCompleted()
    {
        return isCompleted;

    }
}
