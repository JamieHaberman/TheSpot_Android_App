package acad277.patel.sonika.final_project.lists.model;

/**
 * Created by Sonika on 4/16/17.
 */

public class StudySpot {
    private String name;
    private String location;
    private int quantityLeft;

    @Override
    public String toString() {
        return "Food{" +
                "name='" + name + '\'' +
                ", location='" + location + '\'' +
                ", quantityLeft=" + quantityLeft +
                '}';
    }
}
