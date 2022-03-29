import java.util.ArrayList;
import java.util.Collections;

public class FourthRatings {

    private double getAverageById(String id, int minimalRaters) {
        double sum = 0.0;
        int count = 0;
        ArrayList<Rater> raters = RaterDatabase.getRaters();
        for (Rater rater : raters) {
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
                Rating rating = new Rating(id, avg);
                ratings.add(rating);
            }
        }
        return ratings;
    }

    public ArrayList<Rating> getSimilarRatings(String raterId, int numSimilarRaters, int minimalRaters) {
        ArrayList<Rating> list = getSimilarities(raterId);
        ArrayList<Rating> res = new ArrayList<Rating>();

        for (String movieId : MovieDatabase.filterBy(new TrueFilter())) {
            double sum = 0.0;
            int count = 0;
            for (int i = 0; i < numSimilarRaters; i++) {
                Rating r = list.get(i);
                double raterWeight = r.getValue();
                Rater currRater = RaterDatabase.getRater(r.getItem());
                double currRating = currRater.getRating(movieId);
                if (currRating != -1) {
                    sum += raterWeight * currRating;
                    count++;
                }
            }
            if (count >= minimalRaters) {
                double weightedAvg = sum / count;
                Rating rating = new Rating(movieId, weightedAvg);
                res.add(rating);
            }
        }
        Collections.sort(res, Collections.reverseOrder());
        return res;
    }

    public ArrayList<Rating> getSimilarRatingsByFilter(String raterId, int numSimilarRaters, int minimalRaters,
            Filter filter) {
        ArrayList<Rating> list = getSimilarities(raterId);
        ArrayList<Rating> res = new ArrayList<Rating>();

        for (String movieId : MovieDatabase.filterBy(filter)) {
            double sum = 0.0;
            int count = 0;
            for (int i = 0; i < numSimilarRaters; i++) {
                Rating r = list.get(i);
                double raterWeight = r.getValue();
                Rater currRater = RaterDatabase.getRater(r.getItem());
                double currRating = currRater.getRating(movieId);
                if (currRating != -1) {
                    sum += raterWeight * currRating;
                    count++;
                }
            }
            if (count >= minimalRaters) {
                double weightedAvg = sum / count;
                Rating rating = new Rating(movieId, weightedAvg);
                res.add(rating);
            }
        }
        Collections.sort(res, Collections.reverseOrder());
        return res;
    }

    // helpers
    private double dotProduct(Rater me, Rater r) {
        double sum = 0.0;
        ArrayList<String> myRatings = me.getItemsRated();
        for (String moiveId : myRatings) {
            if (r.hasRating(moiveId)) {
                double myRating = me.getRating(moiveId) - 5;
                double otherRating = r.getRating(moiveId) - 5;
                sum += myRating * otherRating;
            }
        }
        return sum;
    }

    private ArrayList<Rating> getSimilarities(String raterId) {
        ArrayList<Rating> list = new ArrayList<Rating>();
        Rater me = RaterDatabase.getRater(raterId);
        for (Rater r : RaterDatabase.getRaters()) {
            if (!me.getID().equals(r.getID())) {
                double dot = dotProduct(me, r);
                if (dot > 0.0) {
                    Rating rating = new Rating(r.getID(), dot);
                    list.add(rating);
                }
            }
        }
        Collections.sort(list, Collections.reverseOrder());
        return list;
    }
}
