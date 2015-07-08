
package fouriam.android.esgi.fr.filmdroid.enumerations;


public enum AppendToResponseItem {

    VIDEOS("videos"),
    RELEASES("releases"),
    CREDITS("credits"),
    SIMILAR("similar"),
    IMAGES("images"),
    EXTERNAL_IDS("external_ids");

    private final String value;

    private AppendToResponseItem(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return value;
    }
}
