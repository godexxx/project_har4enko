package Classes;

import java.util.ArrayList;

/**
 * Created by Alex Shcherbak on 24.04.2014.
 */
public class Task {
    private Integer id;
    private String name;
    private String description;
    private ArrayList<Architecture> architectures;

    public Task(Integer id, String name, String description, ArrayList<Architecture> architectures) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.architectures = architectures;
    }

    public Task(){
        this.id = null;
        this.name = "";
        this.description = "";
        this.architectures = null;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public ArrayList<Architecture> getArchitectures() {
        return architectures;
    }

    public void setArchitectures(ArrayList<Architecture> architectures) {
        this.architectures = architectures;
    }
}
