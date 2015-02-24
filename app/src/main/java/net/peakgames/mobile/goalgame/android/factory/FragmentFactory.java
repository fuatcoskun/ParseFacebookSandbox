package net.peakgames.mobile.goalgame.android.factory;

import android.support.v4.app.Fragment;

import net.peakgames.mobile.goalgame.android.detail.AndroidGamingLibFacebookLogin;
import net.peakgames.mobile.goalgame.android.detail.AnonymusUserFragment;
import net.peakgames.mobile.goalgame.android.detail.CheckCurrentUserFragment;
import net.peakgames.mobile.goalgame.android.detail.CheckFacebookSessionStateFragment;
import net.peakgames.mobile.goalgame.android.detail.CreateUserFragment;
import net.peakgames.mobile.goalgame.android.detail.FacebookLoginWithParseFragment;
import net.peakgames.mobile.goalgame.android.detail.LinkFacebookUserFragment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Helper class for providing sample content for user interfaces created by
 * Android template wizards.
 * <p/>
 * TODO: Replace all uses of this class before publishing your app.
 */
public class FragmentFactory {

    /**
     * An array of sample (dummy) items.
     */
    public static List<FragmentListItem> ITEMS = new ArrayList<FragmentListItem>();

    /**
     * A map of sample (dummy) items, by ID.
     */
    public static Map<String, FragmentListItem> ITEM_MAP = new HashMap<String, FragmentListItem>();

    static {
        // Add 3 sample items.
        addItem(new FragmentListItem("1", "Anonymus User"));
        addItem(new FragmentListItem("2", "Create User"));
        addItem(new FragmentListItem("3", "Check Current User"));
        addItem(new FragmentListItem("4", "Facebook Login With Parse"));
        addItem(new FragmentListItem("5", "Link User With Parse"));
        addItem(new FragmentListItem("6", "Check Facebook Session State"));
        addItem(new FragmentListItem("7", "AndroidGamingLib Facebook Login"));
    }

    private static void addItem(FragmentListItem item) {
        ITEMS.add(item);
        ITEM_MAP.put(item.id, item);
    }

    /**
     * A dummy item representing a piece of content.
     */
    public static class FragmentListItem {
        public String id;
        public String content;

        public FragmentListItem(String id, String content) {
            this.id = id;
            this.content = content;
        }

        @Override
        public String toString() {
            return content;
        }
    }

    public static Fragment getFragment(String id) {
        if(id.equals("1")) {
            return new AnonymusUserFragment();
        } else if(id.equals("2")) {
            return new CreateUserFragment();
        } else if(id.equals("3")) {
            return new CheckCurrentUserFragment();
        } else if(id.equals("4")) {
            return new FacebookLoginWithParseFragment();
        } else if(id.equals("5")) {
            return new LinkFacebookUserFragment();
        } else if(id.equals("6")) {
            return new CheckFacebookSessionStateFragment();
        } else if(id.equals("7")) {
            return new AndroidGamingLibFacebookLogin();
        }
        return null;
    }
}
