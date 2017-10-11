package playoassignment.com.playoassignment.util;

/**
 * Created by Anuj on 10/10/17.
 */

public interface CallCompleted {

    void onCallComplete(String response, int requestCode, int status);
}
