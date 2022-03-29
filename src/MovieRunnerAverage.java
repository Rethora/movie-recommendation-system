import java.util.ArrayList;
import java.util.Collections;

public class MovieRunnerAverage {
    public void printAverageRatings() {
        String movies = "data/ratedmoviesfull.csv";
        String raters = "data/ratings.csv";

        // String movies = "data/ratedmovies_short.csv";
        // String raters = "data/ratings_short.csv";

        SecondRatings sr = new SecondRatings(movies, raters);
        System.out.println("Number of movies: " + sr.getMovieSize());
        System.out.println("Number of raters: " + sr.getRaterSize());

        int minimalRaters = 12;
        ArrayList<Rating> ratings = sr.getAverageRatings(minimalRaters);
        System.out.println(ratings.size());
        Collections.sort(ratings);

        if (ratings.size() <= 10) {
            for (Rating rating : ratings) {
                System.out.println(rating.getValue() + " " + rating.getItem());
            }
        }
    }

    public void getAverageRatingOneMovie() {
        String movies = "data/ratedmoviesfull.csv";
        String raters = "data/ratings.csv";

        // String movies = "data/ratedmovies_short.csv";
        // String raters = "data/ratings_short.csv";

        SecondRatings sr = new SecondRatings(movies, raters);

        String movieTitle = "Vacation";
        int minimalRaters = 0;
        ArrayList<Rating> ratings = sr.getAverageRatings(minimalRaters);
        for (Rating rating : ratings) {
            if (rating.getItem().equals(movieTitle)) {
                System.out.println(rating.toString());
            }
        }
    }

    public static void main(String[] args) {
        MovieRunnerAverage runner = new MovieRunnerAverage();
        runner.printAverageRatings();
        // runner.getAverageRatingOneMovie();
    }
}
