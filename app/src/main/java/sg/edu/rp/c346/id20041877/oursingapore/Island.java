package sg.edu.rp.c346.id20041877.oursingapore;

import java.io.Serializable;

public class Island implements Serializable {

    private int id;
    private String name;
    private String description;
    private int squareKm;
    private int stars;

    public Island(String name, String description, int squareKm, int stars) {
        this.name = name;
        this.description = description;
        this.squareKm = squareKm;
        this.stars = stars;
    }

    public Island(int id, String name, String description, int squareKm, int stars) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.squareKm = squareKm;
        this.stars = stars;
    }

    public int getId() { return id; }

    public void setId(int id) { this.id = id; }

    public String getName() { return name; }

    public void setName(String name) { this.name = name; }

    public String getDescription() { return description; }

    public void setDescription(String description) { this.description = description; }

    public int getSquareKm() { return squareKm; }

    public void setSquareKm(int squareKm) { this.squareKm = squareKm; }

    public int getStars() { return stars; }

    public void setStars(int stars) { this.stars = stars; }


}
