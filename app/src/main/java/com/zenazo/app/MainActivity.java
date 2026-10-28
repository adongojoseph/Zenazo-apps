package com.zenazo.app;

import android.app.Activity;
import android.os.Bundle;
import android.graphics.Color;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.ScrollView;
import android.widget.Toast;

public class MainActivity extends Activity {

    private final int navy = Color.rgb(35, 45, 65);
    private final int blue = Color.rgb(55, 105, 210);
    private final int background = Color.rgb(245, 247, 251);

    private LinearLayout mainLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        showHome();
    }

    private TextView createText(String text, int size, int color) {
        TextView view = new TextView(this);
        view.setText(text);
        view.setTextSize(size);
        view.setTextColor(color);
        view.setPadding(16, 12, 16, 12);
        return view;
    }

    private void showHome() {
        ScrollView scrollView = new ScrollView(this);
        scrollView.setBackgroundColor(background);

        mainLayout = new LinearLayout(this);
        mainLayout.setOrientation(LinearLayout.VERTICAL);
        mainLayout.setPadding(20, 24, 20, 24);

        TextView title = createText("ZenApp", 32, navy);
        title.setTypeface(null, android.graphics.Typeface.BOLD);
        mainLayout.addView(title);

        TextView tagline = createText(
                "Your world. Your connections.", 16, Color.DKGRAY);
        mainLayout.addView(tagline);

        TextView welcome = createText(
                "Welcome to your world", 23, navy);
        welcome.setPadding(16, 36, 16, 12);
        welcome.setTypeface(null, android.graphics.Typeface.BOLD);
        mainLayout.addView(welcome);

        TextView description = createText(
                "Discover people, share ideas and stay connected.",
                16, Color.DKGRAY);
        mainLayout.addView(description);

        addFeature("👥  Find People",
                "Explore opportunities to connect with others.");
        addFeature("💬  Messages",
                "Your conversations will appear here when messaging is implemented.");
        addFeature("🌍  Explore",
                "Discover content and ideas from your community.");
        addFeature("👤  My Profile",
                "Your personal profile features will appear here.");

        TextView note = createText(
                "ZenApp is getting started. More features are coming.",
                14, Color.GRAY);
        note.setPadding(16, 32, 16, 16);
        mainLayout.addView(note);

        scrollView.addView(mainLayout);
        setContentView(scrollView);
    }

    private void addFeature(String heading, String description) {
        LinearLayout card = new LinearLayout(this);
        card.setOrientation(LinearLayout.VERTICAL);
        card.setPadding(12, 8, 12, 8);
        card.setBackgroundColor(Color.WHITE);

        LinearLayout.LayoutParams cardParams =
                new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT);
        cardParams.setMargins(0, 12, 0, 0);

        TextView title = createText(heading, 19, navy);
        title.setTypeface(null, android.graphics.Typeface.BOLD);

        TextView details = createText(description, 14, Color.DKGRAY);

        card.addView(title);
        card.addView(details);

        card.setElevation(3);
        card.setClickable(true);
        card.setFocusable(true);

        card.setOnClickListener(v ->
                Toast.makeText(
                        MainActivity.this,
                        heading + " selected",
                        Toast.LENGTH_SHORT
                ).show()
        );

        mainLayout.addView(card, cardParams);
    }
}
