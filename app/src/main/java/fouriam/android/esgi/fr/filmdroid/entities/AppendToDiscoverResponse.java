
package fouriam.android.esgi.fr.filmdroid.entities;

public class AppendToDiscoverResponse {

    private final Integer[] items;

    public AppendToDiscoverResponse(Integer... items) {
        this.items = items;
    }

    @Override
    public String toString() {
        if (items != null && items.length > 0) {
            StringBuffer sb = new StringBuffer();
            for (int i = 0; i < items.length ; i++) {
                sb.append(items[i]);

                if (i < items.length - 1) {
                    sb.append(',');
                }
            }

            return sb.toString();
        }

        return null;
    }
}
