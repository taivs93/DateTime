package entity;

public enum Selection {
    ADD(1,"ADD EVENT"),
    UPDATE(2,"UPDATE EVENT"),
    DELETE(3,"DELETE EVENT"),
    TIMETONEXTEVENT(4,"TIME TO NEXT EVENT"),
    TIMEBETWEENTOEVENTS(5,"TIME BETWEEN TWO EVENTS"),
    SHOWEVENTINWEEK(6,"SHOW EVENTS IN THIS WEEK"),
    SHOWEVENTINMONTH(7,"SHOW EVENTS IN MONTH"),
    EXIT(8,"EXIT");

    private int value;
    private String description;

    Selection(int value, String description) {
        this.value = value;
        this.description = description;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public static Selection getSelectionByValue(int value){
        for (Selection selection : Selection.values()){
            if(selection.value == value) return selection;
        }
        throw new IllegalArgumentException("Selection not found");
    }
}
