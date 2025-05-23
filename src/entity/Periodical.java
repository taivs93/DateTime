package entity;

public enum Periodical {
    NONE(1,"NONE"),DAY(2,"DAY"),WEEK(3,"WEEK"),MONTH(4,"MONTH");

    private int value;
    private String description;

    Periodical(int value, String description) {
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

    public static Periodical getPeriodicalByValue(int value){
        for (Periodical periodical : Periodical.values()){
            if (periodical.getValue() == value){
                return periodical;
            }
        }
        throw new IllegalArgumentException("Selection not found");
    }
}
