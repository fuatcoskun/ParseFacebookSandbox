package net.peakgames.mobile.goalgame.android.detail;

import net.peakgames.mobile.goalgame.android.R;
import net.peakgames.mobile.goalgame.android.factory.FragmentFactory;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseFacebookUtils;
import com.parse.ParseUser;

/**
 * A fragment representing a single Item detail screen.
 * This fragment is either contained in a {@link net.peakgames.mobile.goalgame.android.ItemListActivity}
 * in two-pane mode (on tablets) or a {@link net.peakgames.mobile.goalgame.android.ItemDetailActivity}
 * on handsets.
 */
public class FacebookLoginWithParseFragment extends Fragment {
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
    public FacebookLoginWithParseFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments().containsKey(ARG_ITEM_ID)) {
            // Load the dummy content specified by the fragment
            // arguments. In a real-world scenario, use a Loader
            // to load content from a content provider.
            mItem = FragmentFactory.ITEM_MAP.get(getArguments().getString(ARG_ITEM_ID));
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.facebook_login_with_parse_fragment, container, false);

        // Show the dummy content as text in a TextView.
        if (mItem != null) {
            ((TextView) rootView.findViewById(R.id.item_detail)).setText(mItem.content);
        }

        ((Button) rootView.findViewById(R.id.fbLoginViaParseButton)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                facebookLoginViaParse(rootView);
            }
        });

        return rootView;
    }

    private void facebookLoginViaParse(final View rootView) {

        ParseFacebookUtils.logIn(getActivity(), new LogInCallback() {
            @Override
            public void done(ParseUser parseUser, ParseException e) {
                Log.d("ParseFace", "result occured");
                if (parseUser == null) {
                    ((TextView) rootView.findViewById(R.id.fbLoginViaParseResult)).setText("Uh oh. The user cancelled the Facebook login.");
                } else if (parseUser.isNew()) {
                    ((TextView) rootView.findViewById(R.id.fbLoginViaParseResult)).setText("User signed up and logged in through Facebook!");
                } else {
                    ((TextView) rootView.findViewById(R.id.fbLoginViaParseResult)).setText("User logged in through Facebook!");
                }
            }
        });

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.d("ParseFace", "On Activity Result called");
        ParseFacebookUtils.finishAuthentication(requestCode, resultCode, data);
    }

}
