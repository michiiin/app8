package com.example.app8;
import android.net.Uri;

import androidx.lifecycle.ViewModel;

public class ProfileViewModel extends ViewModel {
    private Uri profileImageUri;

    public Uri getProfileImageUri() {
        return profileImageUri;
    }



















    public void setProfileImageUri(Uri profileImageUri) {
        this.profileImageUri = profileImageUri;
    }
}
