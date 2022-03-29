import java.util.ArrayList;

public interface Rater {

    public String getID();

    public void addRating(String item, double rating);

    public double getRating(String item);

    public ArrayList<String> getItemsRated();

    public boolean hasRating(String item);
}
