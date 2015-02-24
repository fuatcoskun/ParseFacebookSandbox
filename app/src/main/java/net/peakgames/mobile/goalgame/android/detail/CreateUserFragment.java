package net.peakgames.mobile.goalgame.android.detail;

import net.peakgames.mobile.goalgame.android.R;
import net.peakgames.mobile.goalgame.android.factory.FragmentFactory;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SaveCallback;
import com.parse.SignUpCallback;

/**
 * A fragment representing a single Item detail screen.
 * This fragment is either contained in a {@link net.peakgames.mobile.goalgame.android.ItemListActivity}
 * in two-pane mode (on tablets) or a {@link net.peakgames.mobile.goalgame.android.ItemDetailActivity}
 * on handsets.
 */
public class CreateUserFragment extends Fragment {
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
    public CreateUserFragment() {
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
        View rootView = inflater.inflate(R.layout.create_user_fragment, container, false);

        // Show the dummy content as text in a TextView.
        if (mItem != null) {
            ((TextView) rootView.findViewById(R.id.item_detail)).setText(mItem.content);
        }

        ((Button)rootView.findViewById(R.id.saveButton)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveParseUser();
            }
        });
        initializeInfo(rootView);

        return rootView;
    }

    private void initializeInfo(View rootView) {
        ParseUser parseUser = ParseUser.getCurrentUser();
        if(parseUser != null) {
            ((EditText)rootView.findViewById(R.id.username)).setText(parseUser.getUsername());
            ((EditText)rootView.findViewById(R.id.email)).setText(parseUser.getEmail());
        }
    }

    private void saveParseUser() {
        if(ParseUser.getCurrentUser() != null) {
            updateParseUser();
        } else {
            createParseUser();
        }
    }

    private void updateParseUser() {
        ParseUser parseUser = ParseUser.getCurrentUser();
        fillUserData(parseUser);

        parseUser.saveInBackground(new SaveCallback() {
            @Override
            public void done(ParseException e) {
                if(e != null) {
                    ((TextView)getView().findViewById(R.id.result)).setText("User couldn't be saved : " + e.getMessage());
                } else {
                    ((TextView)getView().findViewById(R.id.result)).setText("User updated!");
                }
            }
        });

    }

    private void createParseUser() {
        ParseUser parseUser = new ParseUser();
        fillUserData(parseUser);

        parseUser.signUpInBackground(new SignUpCallback() {
            @Override
            public void done(ParseException e) {
                if(e != null) {
                    ((TextView)getView().findViewById(R.id.result)).setText("User couldn't be saved : " + e.getMessage());
                } else {
                    ((TextView)getView().findViewById(R.id.result)).setText("User saved!");
                }
            }
        });
    }

    private void fillUserData(ParseUser parseUser) {
        parseUser.setUsername(((EditText)getView().findViewById(R.id.username)).getText().toString());
        parseUser.setPassword(((EditText)getView().findViewById(R.id.pass)).getText().toString());
        parseUser.setEmail(((EditText)getView().findViewById(R.id.email)).getText().toString());
    }
}
