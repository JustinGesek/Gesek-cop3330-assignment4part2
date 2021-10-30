package ucf.assignments;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

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
    private Date dueDate;
    private Boolean isCompleted;
    public Item(String description, Date dueDate, Boolean isCompleted)
    {
        this.description = description;
        this.dueDate = dueDate;
        this.isCompleted = isCompleted;
    }

    public void setIsCompleted(Boolean completed)
    {

    }

    public String getDescription()
    {
        return description;
    }
    public Date getDueDate()
    {

        return dueDate;

    }
    public Boolean getIsCompleted()
    {
        return isCompleted;

    }
}
