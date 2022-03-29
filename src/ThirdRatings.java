
/**
 * Write a description of SecondRatings here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */

import java.io.IOException;
import java.util.*;

public class ThirdRatings {
    private ArrayList<EfficientRater> myRaters;

    public ThirdRatings() {
        this("data/ratings.csv");
    }

    public ThirdRatings(String ratingsFile) {
        FirstRatings ratings = new FirstRatings();
        try {
            // myMovies = ratings.loadMovies(movieFile);
            myRaters = ratings.loadRaters("data/" + ratingsFile);
        } catch (IOException e) {
            System.out.println(e);
        }
    }

    public int getRaterSize() {
        return myRaters.size();
    }

    private double getAverageById(String id, int minimalRaters) {
        double sum = 0.0;
        int count = 0;
        for (EfficientRater rater : myRaters) {
            double rating = rater.getRating(id);
            if (rating != -1) {
                sum += rating;
                count++;
            }
        }
        if (count < minimalRaters) {
            return 0.0;
        }
        return sum / count;
    }

    public ArrayList<Rating> getAverageRatings(int minimalRaters) {
        ArrayList<Rating> ratings = new ArrayList<Rating>();
        ArrayList<String> movies = MovieDatabase.filterBy(new TrueFilter());
        for (String id : movies) {
            double avg = getAverageById(id, minimalRaters);
            if (avg != 0.0) {
                Rating rating = new Rating(id, avg);
                ratings.add(rating);
            }
        }
        return ratings;
    }

    public ArrayList<Rating> getAverageRatingsByFilter(int minimalRaters, Filter filterCriteria) {
        ArrayList<Rating> ratings = new ArrayList<Rating>();
        ArrayList<String> movies = MovieDatabase.filterBy(filterCriteria);
        for (String id : movies) {
            double avg = getAverageById(id, minimalRaters);
            if (avg != 0.0) {
                // Rating rating = new Rating(MovieDatabase.getTitle(id), avg);
                Rating rating = new Rating(id, avg);
                ratings.add(rating);
            }
        }
        return ratings;
    }
}