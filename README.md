# Movie Recommendation System

![Movie Recommendations Results Example](assets/movie-recommendations.png)

This is a capstone project for Java Programming and Software Engineering Fundamentals. This program renders a table of 10 random movies for a user to rate from 1-10 (or no rating if they have not seen the movie). Upon submitting their ratings, they are compared to a csv file of people who have also submitted ratings for the movies and put into a list. The list is then sorted by the highest weighted averages. Once the list is sorted, I made a new list and added movies from the most similar users that our user did not rate. This is to avoid recommending movies that our user as already seen. Once the list is at a desired length, I display those movies to the user.
