package com.peprally.jeremy.peprally;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.HashMap;
import java.util.Map;

public class ProfileViewFragment extends Fragment {

    private boolean profileLoaded = false;

    private static final String TAG = ProfileViewFragment.class.getSimpleName();

    Map<String, String>  baseballPositions = new HashMap<String, String>();
    Map<String, String>  basketballPositions = new HashMap<String, String>();
    Map<String, String>  footballPositions = new HashMap<String, String>();
    Map<String, String>  soccerPositions = new HashMap<String, String>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile_view, container, false);
//        setupUserProfile(view, getArguments());
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        initializePositionMaps();
        setupUserProfile(getView(), getArguments());
    }

    public void setupUserProfile(View view, Bundle UPB) {
        if (UPB != null) {
            if (UPB.getBoolean("IS_VARSITY_PLAYER") && !profileLoaded) {
                LinearLayout parent = (LinearLayout) view.findViewById(R.id.profile_view_container);
                LinearLayout playerInfoLayout = new LinearLayout(getActivity());
                playerInfoLayout.setOrientation(LinearLayout.VERTICAL);
                LinearLayout.LayoutParams llparams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                llparams.setMargins((int) getResources().getDimension(R.dimen.activity_horizontal_margin),
                        0,
                        (int) getResources().getDimension(R.dimen.activity_horizontal_margin),
                        (int) getResources().getDimension(R.dimen.activity_horizontal_margin));
                playerInfoLayout.setLayoutParams(llparams);

                LinearLayout.LayoutParams tvparams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                tvparams.setMargins(0, 0, 0, 4);
                if (UPB.getString("POSITION") != null) {
                    String text = "Position: <b>";
                    String pos = UPB.getString("POSITION");
                    switch (UPB.getString("TEAM")) {
                        case "Baseball":
                            if (pos.indexOf("/") != -1) {
                                text = text + baseballPositions.get(pos.split("/")[0]) + "/"
                                            + baseballPositions.get(pos.split("/")[1]);
                            }
                            else {
                                text += baseballPositions.get(pos).toString();
                            }
                            break;
                        case "Basketball":
                            if (pos.indexOf("/") != -1) {
                                text = text + basketballPositions.get(pos.split("/")[0]) + "/"
                                            + basketballPositions.get(pos.split("/")[1]);
                            }
                            else {
                                text += basketballPositions.get(pos).toString();
                            }
                            break;
                        case "Football":
                            if (pos.indexOf("/") != -1) {
                                text = text + footballPositions.get(pos.split("/")[0]) + "/"
                                            + footballPositions.get(pos.split("/")[1]);
                            }
                            else {
                                text += footballPositions.get(pos).toString();
                            }
                            break;
                        default:
                            text += pos;
                            break;
                    }
                    TextView tv_position = new TextView(getActivity());
                    tv_position.setText(Html.fromHtml(text + "</b"));
                    tv_position.setLayoutParams(tvparams);
                    playerInfoLayout.addView(tv_position);
                }

                if (UPB.getString("HEIGHT") != null) {
                    TextView tv_height = new TextView(getActivity());
                    tv_height.setText(Html.fromHtml("Height: <b>" + UPB.getString("HEIGHT") + "</b>"));
                    tv_height.setLayoutParams(tvparams);
                    playerInfoLayout.addView(tv_height);
                }

                if (UPB.getString("WEIGHT") != null) {
                    TextView tv_weight = new TextView(getActivity());
                    tv_weight.setText(Html.fromHtml("Weight: <b>" + UPB.getString("WEIGHT") + "</b>"));
                    tv_weight.setLayoutParams(tvparams);
                    playerInfoLayout.addView(tv_weight);
                }

                if (UPB.getString("YEAR") != null) {
                    TextView tv_year = new TextView(getActivity());
                    tv_year.setText(Html.fromHtml("Year: <b>" + UPB.getString("YEAR") + "</b>"));
                    tv_year.setLayoutParams(tvparams);
                    playerInfoLayout.addView(tv_year);
                }

                if (UPB.getString("HOMETOWN") != null) {
                    String[] sa = UPB.getString("HOMETOWN").split("/");
                    TextView tv_hometown = new TextView(getActivity());
                    tv_hometown.setText(Html.fromHtml("Hometown: <b>" + sa[0].substring(0, sa[0].length() - 1) + "</b>"));
                    tv_hometown.setLayoutParams(tvparams);
                    playerInfoLayout.addView(tv_hometown);

                    TextView tv_highschool = new TextView(getActivity());
                    tv_highschool.setText(Html.fromHtml("High School: <b>" + sa[1].substring(1) + "</b>"));
                    tv_highschool.setLayoutParams(tvparams);
                    playerInfoLayout.addView(tv_highschool);
                }
                if (!UPB.getBoolean("HAS_USER_PROFILE")) {
                    TextView tv_no_profile = new TextView(getActivity());
                    LinearLayout.LayoutParams msg_params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                    msg_params.setMargins(0,
                            (int) getResources().getDimension(R.dimen.activity_vertical_margin),
                            0,
                            0);
                    tv_no_profile.setText(UPB.getString("FIRST_NAME") +
                            " has not made a Pep Rally profile yet. Tell your friends about Pep Rally to" +
                            " help promote school spirit!");
                    tv_no_profile.setTypeface(null, Typeface.ITALIC);
                    tv_no_profile.setLayoutParams(msg_params);
                    tv_no_profile.setGravity(Gravity.CENTER_HORIZONTAL);
                    playerInfoLayout.addView(tv_no_profile);
                }
                parent.addView(playerInfoLayout, 2);
                profileLoaded = true;
            }

            TextView textViewFirstName = (TextView) view.findViewById(R.id.profile_view_name_age);
            TextView textViewNickname = (TextView) view.findViewById(R.id.profile_view_nickname);
            TextView textViewFavTeam = (TextView) view.findViewById(R.id.profile_view_fav_team);
            TextView textViewFavPlayer = (TextView) view.findViewById(R.id.profile_view_fav_player);
            TextView textViewPepTalk = (TextView) view.findViewById(R.id.profile_view_pep_talk);
            TextView textViewTrashTalk = (TextView) view.findViewById(R.id.profile_view_trash_talk);

            textViewFirstName.setText(UPB.getString("FIRST_NAME") + ", " + Integer.toString(23));
            if (UPB.getString("NICKNAME") == null) {
                textViewNickname.setVisibility(View.INVISIBLE);
                LinearLayout.LayoutParams llp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                llp.setMargins(0,0,0,0);
                textViewNickname.setLayoutParams(llp);
                textViewNickname.setTextSize(TypedValue.COMPLEX_UNIT_SP, 4);
//                textViewNickname.setTypeface(null, Typeface.ITALIC);
//                textViewNickname.setText(getResources().getString(R.string.default_nickname));
            } else {
                textViewNickname.setText(UPB.getString("NICKNAME"));
            }
            if (UPB.getString("FAVORITE_TEAM") == null) {
                LinearLayout ll = (LinearLayout) view.findViewById(R.id.profile_layout_fav_team);
                ll.setVisibility(View.INVISIBLE);
//                textViewFavTeam.setTypeface(null, Typeface.ITALIC);
//                textViewFavTeam.setText(getResources().getString(R.string.default_fav_team));
            } else {
                textViewFavTeam.setText(UPB.getString("FAVORITE_TEAM"));
            }
            if (UPB.getString("FAVORITE_PLAYER") == null) {
                LinearLayout ll = (LinearLayout) view.findViewById(R.id.profile_layout_fav_player);
                ll.setVisibility(View.INVISIBLE);
//                textViewFavPlayer.setTypeface(null, Typeface.ITALIC);
//                textViewFavPlayer.setText(getResources().getString(R.string.default_fav_player));
            } else {
                textViewFavPlayer.setText(UPB.getString("FAVORITE_PLAYER"));
            }
            if (UPB.getString("PEP_TALK") == null) {
                LinearLayout ll = (LinearLayout) view.findViewById(R.id.profile_layout_pep_talk);
                ll.setVisibility(View.INVISIBLE);
//                textViewPepTalk.setTypeface(null, Typeface.ITALIC);
//                textViewPepTalk.setText(getResources().getString(R.string.default_pep_talk));
            } else {
                textViewPepTalk.setText(UPB.getString("PEP_TALK"));
            }
            if (UPB.getString("TRASH_TALK") == null) {
                LinearLayout ll = (LinearLayout) view.findViewById(R.id.profile_layout_trash_talk);
                ll.setVisibility(View.INVISIBLE);
//                textViewTrashTalk.setTypeface(null, Typeface.ITALIC);
//                textViewTrashTalk.setText(getResources().getString(R.string.default_trash_talk));
            } else {
                textViewTrashTalk.setText(UPB.getString("TRASH_TALK"));
            }

        }
    }

    void initializePositionMaps() {
        // Baseball
        baseballPositions.put("C", "Catcher");
        baseballPositions.put("INF", "Infield");
        baseballPositions.put("OF", "Outfield");
        baseballPositions.put("RHP", "Right Handed Pitcher");
        baseballPositions.put("LHP", "Left Handed Pitcher");
        // Basketball
        basketballPositions.put("G", "Guard");
        basketballPositions.put("F", "Forward");
        basketballPositions.put("C", "Center");
        // Football
        footballPositions.put("QB", "Quarterback");
        footballPositions.put("RB", "Runningback");
        footballPositions.put("WR", "Wide Receiver");
        footballPositions.put("TE", "Tight End");
        footballPositions.put("OL", "Offensive Lineman");
        footballPositions.put("OG", "Offensive Guard");
        footballPositions.put("OT", "Offensive Tackle");
        footballPositions.put("CB", "Cornerback");
        footballPositions.put("DB", "Defensive Back");
        footballPositions.put("S", "Safety");
        footballPositions.put("LB", "Linebacker");
        footballPositions.put("DL", "Defensive Lineman");
        footballPositions.put("DT", "Defensive Tackle");
        footballPositions.put("DE", "Defensive End");
        footballPositions.put("DS", "Deep Safety");
        footballPositions.put("P", "Punter");
        footballPositions.put("PK", "Punter/Kicker");
    }
}