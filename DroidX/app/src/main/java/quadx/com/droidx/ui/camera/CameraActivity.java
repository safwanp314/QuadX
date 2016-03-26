package quadx.com.droidx.ui.camera;

import android.net.Uri;
import android.widget.MediaController;
import android.widget.VideoView;

import quadx.com.droidx.R;
import quadx.com.droidx.resources.Resources;
import quadx.com.droidx.ui.MainActivity;

/**
 * Created by SAFWAN on 2/13/16.
 */
public class CameraActivity extends MainActivity {

    private VideoView videoView;

    protected void setViewResources() {
        super.setViewResources();
        //-------------------------------------------------------
        videoView = (VideoView) findViewById(R.id.cameraVideoView);
        Uri uri = Uri.parse(Resources.CAMERA_URI);
        videoView.setVideoURI(uri);
        videoView.requestFocus();
        videoView.start();
        //-------------------------------------------------------
    }

    @Override
    protected int getLayoutViewId() {
        return R.layout.activity_camera;
    }
}
