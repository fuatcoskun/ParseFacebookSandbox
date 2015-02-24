package net.peakgames.mobile.goalgame.android.detail;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.facebook.Session;

import net.peakgames.mobile.goalgame.android.R;
import net.peakgames.mobile.goalgame.android.factory.FragmentFactory;

/**
 * A fragment representing a single Item detail screen.
 * This fragment is either contained in a {@link net.peakgames.mobile.goalgame.android.ItemListActivity}
 * in two-pane mode (on tablets) or a {@link net.peakgames.mobile.goalgame.android.ItemDetailActivity}
 * on handsets.
 */
public class CheckFacebookSessionStateFragment extends Fragment {
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
    public CheckFacebookSessionStateFragment() {
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
        View rootView = inflater.inflate(R.layout.check_facebook_session_state_fragment, container, false);

        // Show the dummy content as text in a TextView.
        if (mItem != null) {
            ((TextView) rootView.findViewById(R.id.item_detail)).setText(mItem.content);
        }
        checkFacebookSession(rootView);

        return rootView;
    }

    private void checkFacebookSession(View rootView) {
        Session activeSession = Session.getActiveSession();

        if(activeSession != null) {
            if(activeSession.isOpened()) {
                ((TextView)rootView.findViewById(R.id.facebookSessionStateLabel)).setText("activeSession is opened!!!");
            } else {
                ((TextView)rootView.findViewById(R.id.facebookSessionStateLabel)).setText("activeSession is not opened!!!");
            }
        } else {
            ((TextView)rootView.findViewById(R.id.facebookSessionStateLabel)).setText("activeSession is null!!!");
        }
    }
}
