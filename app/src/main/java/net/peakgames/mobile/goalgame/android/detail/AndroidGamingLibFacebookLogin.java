package net.peakgames.mobile.goalgame.android.detail;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.squareup.otto.Bus;
import com.squareup.otto.Subscribe;
import com.squareup.otto.ThreadEnforcer;

import net.peakgames.mobile.android.apptracking.KontagentGamingLibHelper;
import net.peakgames.mobile.android.apptracking.KontagentInterface;
import net.peakgames.mobile.android.common.model.FacebookUser;
import net.peakgames.mobile.android.facebook.AndroidFacebook;
import net.peakgames.mobile.android.facebook.events.FacebookLoginFailureEvent;
import net.peakgames.mobile.android.facebook.events.FacebookLoginSuccessEvent;
import net.peakgames.mobile.android.log.AndroidLogger;
import net.peakgames.mobile.goalgame.android.R;
import net.peakgames.mobile.goalgame.android.factory.FragmentFactory;

import java.util.Collections;

/**
 * A fragment representing a single Item detail screen.
 * This fragment is either contained in a {@link net.peakgames.mobile.goalgame.android.ItemListActivity}
 * in two-pane mode (on tablets) or a {@link net.peakgames.mobile.goalgame.android.ItemDetailActivity}
 * on handsets.
 */
public class AndroidGamingLibFacebookLogin extends Fragment {

    private static final String TAG = "ParseFace";
    /**
     * The fragment argument representing the item ID that this fragment
     * represents.
     */
    public static final String ARG_ITEM_ID = "item_id";

    /**
     * The dummy content this fragment is presenting.
     */
    private FragmentFactory.FragmentListItem mItem;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public AndroidGamingLibFacebookLogin() {
    }

    private AndroidFacebook androidFacebook;
    private Bus bus = new Bus(ThreadEnforcer.ANY);

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments().containsKey(ARG_ITEM_ID)) {
            // Load the dummy content specified by the fragment
            // arguments. In a real-world scenario, use a Loader
            // to load content from a content provider.
            mItem = FragmentFactory.ITEM_MAP.get(getArguments().getString(ARG_ITEM_ID));
        }

        AndroidLogger logger = new AndroidLogger();
        androidFacebook = new AndroidFacebook(bus, logger, new KontagentGamingLibHelper(logger, new KontagentInterface() {
            @Override
            public void startSession(Object o, String s, MODE mode) {

            }

            @Override
            public void stopSession() {

            }

            @Override
            public void sendEvent(KontagentEventParameters kontagentEventParameters) {

            }

            @Override
            public void sendEvent(String s, String s2, String s3, String s4, String s5, String s6) {

            }

            @Override
            public void sendEventWithData(String s, String s2, String s3, String s4, String s5, String s6, String s7) {

            }

            @Override
            public void trackRevenue(int i) {

            }

            @Override
            public String getSessionId() {
                return null;
            }

            @Override
            public boolean isFirstRun() {
                return false;
            }
        }));

        androidFacebook.initialize(getActivity(), savedInstanceState);
        androidFacebook.setDesiredNotInstalledFriendsCount(10);
        androidFacebook.setInvitableFriendsEnabled(true);

        bus.register(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        androidFacebook.onDestroy();
        try {
            bus.unregister(this);
        } catch (Exception ex) {
            Log.e(TAG, "not registered", ex);
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        androidFacebook.onPause();
    }

    @Override
    public void onResume() {
        super.onResume();
        androidFacebook.onResume();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        androidFacebook.onSaveInstanceState(outState);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        androidFacebook.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.android_gaming_lib_facebook_login, container, false);

        // Show the dummy content as text in a TextView.
        if (mItem != null) {
            ((TextView) rootView.findViewById(R.id.item_detail)).setText(mItem.content);
        }

        ((Button)rootView.findViewById(R.id.fbLoginViaGamingLibButton)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                androidFacebook.login(100, true);
            }
        });

        return rootView;
    }

    @Subscribe
    public void onFacebookLoginSuccess(FacebookLoginSuccessEvent successEvent) {
        Log.d(TAG, "success : " + successEvent.getMe());
        String name = successEvent.getMe().getName();
        Log.d(TAG, "myName is : " + name);
        Log.d(TAG, "invitableFriendSize : " + successEvent.getInvitableFacebookFriends().size());
        Log.d(TAG, "friendsSize : " + successEvent.getFriends().size());


        ((TextView)getView().findViewById(R.id.friendsLabel)).setText("== FRIENDS ==");

        for(FacebookUser fbUser : successEvent.getFriends()) {
            Log.d(TAG, "my Friend : " + fbUser.getName());
            ((TextView)getView().findViewById(R.id.friendsLabel)).setText(((TextView)getView().findViewById(R.id.friendsLabel)).getText() + "\n" + fbUser.getName());
        }

        ((TextView)getView().findViewById(R.id.gamingLibFBLoginResult)).setText(successEvent.getMe().getName() + " facebook login success");
    }

    @Subscribe
    public void onFacebookLoginFailed(FacebookLoginFailureEvent failureEvent) {
        Log.d(TAG, "failed : " + failureEvent.getErrorMessage());
        ((TextView)getView().findViewById(R.id.gamingLibFBLoginResult)).setText("facebook login failed : " + failureEvent.getErrorMessage());
    }
}
