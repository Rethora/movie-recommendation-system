import java.util.*;

public class EfficientRater implements Rater {
    private String myID;
    private HashMap<String, Rating> myRatings;

    public EfficientRater(String id) {
        myID = id;
        myRatings = new HashMap<String, Rating>();
    }

    public void addRating(String item, double rating) {
        Rating newRating = new Rating(item, rating);
        myRatings.put(item, newRating);
    }

    public boolean hasRating(String item) {
        for (String key : myRatings.keySet()) {
            if (myRatings.get(key).getItem().equals(item)) {
                return true;
            }
        }
        return false;
    }

    public String getID() {
        return myID;
    }

    public double getRating(String item) {
        for (String key : myRatings.keySet()) {
            if (myRatings.get(key).getItem().equals(item)) {
                return myRatings.get(key).getValue();
            }
        }
        return -1;
    }

    public int numRatings() {
        return myRatings.size();
    }

    public ArrayList<String> getItemsRated() {
        ArrayList<String> list = new ArrayList<String>();
        for (String key : myRatings.keySet()) {
            list.add(myRatings.get(key).getItem());
        }
        return list;
    }
}
