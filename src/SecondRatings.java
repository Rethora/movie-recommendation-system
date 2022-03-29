
/**
 * Write a description of SecondRatings here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */

import java.io.IOException;
import java.util.*;

public class SecondRatings {
    private ArrayList<Movie> myMovies;
    private ArrayList<EfficientRater> myRaters;

    public SecondRatings(String movieFile, String ratingsFile) {
        FirstRatings ratings = new FirstRatings();
        try {
            myMovies = ratings.loadMovies(movieFile);
            myRaters = ratings.loadRaters(ratingsFile);
        } catch (IOException e) {
            System.out.println(e);
        }
    }

    public int getMovieSize() {
        return myMovies.size();
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
        for (Movie movie : myMovies) {
            String movieId = movie.getID();
            String movieName = movie.getTitle();
            double movieRating = getAverageById(movieId, minimalRaters);
            if (movieRating != 0.0) {
                Rating rating = new Rating(movieName, movieRating);
                ratings.add(rating);
            }
        }
        return ratings;
    }

    public String getTitle(String id) {
        for (Movie movie : myMovies) {
            String movieId = movie.getID();
            if (movieId.equals(id)) {
                return movie.getTitle();
            }
        }
        return "NO SUCH ID.";
    }

    public String getID(String title) {
        for (Movie movie : myMovies) {
            String movieTitle = movie.getTitle();
            if (movieTitle.equals(title)) {
                return movie.getID();
            }
        }
        return "NO SUCH TITLE";
    }

}