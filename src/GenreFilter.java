public class GenreFilter implements Filter {
    private String[] myGenres;

    public GenreFilter(String genres) {
        if (genres.contains(",")) {
            myGenres = genres.split(",".trim());
        } else {
            myGenres = new String[1];
            myGenres[0] = genres;
        }
    }

    @Override
    public boolean satisfies(String id) {
        for (String genre : myGenres) {
            if (MovieDatabase.getGenres(id).toLowerCase().contains(genre.toLowerCase())) {
                return true;
            }
        }
        return false;
    }
}
