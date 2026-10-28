package com.zenazo.app;

import android.app.Activity;
import android.os.Bundle;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.GradientDrawable;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {

    private final int navy = Color.rgb(13, 31, 55);
    private final int navyLight = Color.rgb(24, 48, 78);
    private final int turquoise = Color.rgb(0, 190, 190);
    private final int background = Color.rgb(245, 248, 252);
    private final int white = Color.WHITE;
    private final int dark = Color.rgb(32, 45, 65);
    private final int grey = Color.rgb(110, 125, 145);

    private LinearLayout root;
    private LinearLayout content;
    private String currentPage = "Home";
    private String profileName = "ZenApp Member";
    private String biography = "Welcome to my ZenApp profile.";
    private int followers = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        showPage("Home");
    }

    private GradientDrawable shape(int color, int radius) {
        GradientDrawable d = new GradientDrawable();
        d.setColor(color);
        d.setCornerRadius(radius);
        return d;
    }

    private TextView text(String value, int size, int color,
                          boolean bold) {
        TextView t = new TextView(this);
        t.setText(value);
        t.setTextSize(size);
        t.setTextColor(color);
        t.setGravity(Gravity.CENTER_VERTICAL);
        t.setPadding(12, 10, 12, 10);

        if (bold) {
            t.setTypeface(null, Typeface.BOLD);
        }
        return t;
    }

    private LinearLayout column() {
        LinearLayout l = new LinearLayout(this);
        l.setOrientation(LinearLayout.VERTICAL);
        return l;
    }

    private LinearLayout row() {
        LinearLayout l = new LinearLayout(this);
        l.setOrientation(LinearLayout.HORIZONTAL);
        l.setGravity(Gravity.CENTER_VERTICAL);
        return l;
    }

    private void gap(LinearLayout parent, int height) {
        View v = new View(this);
        parent.addView(v, new LinearLayout.LayoutParams(
                1, height));
    }

    private void addSpace(LinearLayout parent, int height) {
        View v = new View(this);
        parent.addView(v, new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, height));
    }

    private void showPage(String page) {
        currentPage = page;

        root = column();
        root.setBackgroundColor(background);

        LinearLayout header = column();
        header.setBackground(shape(navy, 0));

        LinearLayout headerRow = row();
        headerRow.setPadding(8, 8, 12, 8);

        TextView logo = text("Z  ZenApp", 25, white, true);
        headerRow.addView(logo, new LinearLayout.LayoutParams(
                0, -2, 1));

        TextView notification = text("♡", 27, turquoise, true);
        notification.setGravity(Gravity.CENTER);
        notification.setOnClickListener(v ->
                showMessage("Notifications will be available when connected."));
        headerRow.addView(notification);

        header.addView(headerRow);

        TextView subtitle = text(
                getSubtitle(page), 13, Color.LTGRAY, false);
        subtitle.setPadding(20, 0, 16, 14);
        header.addView(subtitle);

        root.addView(header);

        ScrollView scroll = new ScrollView(this);
        scroll.setFillViewport(true);

        content = column();
        content.setPadding(16, 16, 16, 20);
        scroll.addView(content);

        root.addView(scroll, new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, 0, 1));

        addBottomNavigation();

        setContentView(root);

        switch (page) {
            case "Home":
                buildHome();
                break;
            case "Find People":
                buildPeople();
                break;
            case "Explore":
                buildExplore();
                break;
            case "Messaging":
                buildMessages();
                break;
            case "My Profile":
                buildProfile();
                break;
            case "Chat":
                buildChat();
                break;
            case "Edit Profile":
                buildEditProfile();
                break;
        }
    }

    private String getSubtitle(String page) {
        switch (page) {
            case "Home": return "Your world. Your connections.";
            case "Find People": return "Meet people and grow your network.";
            case "Explore": return "Discover ideas, people and creativity.";
            case "Messaging": return "Your conversations, all in one place.";
            case "My Profile": return "Your identity. Your community.";
            case "Chat": return "A conversation on ZenApp.";
            case "Edit Profile": return "Make your profile your own.";
            default: return "Connect. Share. Belong.";
        }
    }

    private void addBottomNavigation() {
        LinearLayout nav = row();
        nav.setBackground(shape(navy, 0));
        nav.setPadding(2, 8, 2, 8);

        String[] pages = {
                "Home", "Find People", "Explore",
                "Messaging", "My Profile"
        };

        String[] icons = {
                "⌂", "♙", "◎", "▤", "●"
        };

        for (int i = 0; i < pages.length; i++) {
            final String destination = pages[i];

            LinearLayout item = column();
            item.setGravity(Gravity.CENTER);
            item.setPadding(0, 4, 0, 4);

            int color = currentPage.equals(destination)
                    ? turquoise : Color.LTGRAY;

            TextView icon = text(icons[i], 21, color, true);
            icon.setGravity(Gravity.CENTER);

            TextView label = text(
                    pages[i], 9, color, true);
            label.setGravity(Gravity.CENTER);
            label.setPadding(0, 3, 0, 0);

            item.addView(icon);
            item.addView(label);

            item.setOnClickListener(v -> showPage(destination));

            nav.addView(item, new LinearLayout.LayoutParams(
                    0, -2, 1));
        }

        root.addView(nav);
    }

    private void sectionTitle(String title, String action,
                              Runnable callback) {
        LinearLayout r = row();

        TextView heading = text(title, 19, dark, true);
        r.addView(heading, new LinearLayout.LayoutParams(
                0, -2, 1));

        if (action != null) {
            TextView more = text(action, 13, turquoise, true);
            more.setGravity(Gravity.CENTER);
            more.setOnClickListener(v -> callback.run());
            r.addView(more);
        }

        content.addView(r);
    }

    private void card(LinearLayout parent, View inner) {
        LinearLayout wrapper = column();
        wrapper.setPadding(10, 8, 10, 8);
        wrapper.setBackground(shape(white, 22));
        wrapper.setElevation(3);

        LinearLayout.LayoutParams p =
                new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT);
        p.setMargins(0, 5, 0, 12);

        wrapper.addView(inner);
        parent.addView(wrapper, p);
    }

    private Button button(String label, boolean primary,
                          Runnable callback) {
        Button b = new Button(this);
        b.setText(label);
        b.setTextSize(13);
        b.setAllCaps(false);
        b.setTextColor(primary ? navy : turquoise);
        b.setBackground(shape(
                primary ? turquoise : Color.rgb(225, 247, 247), 28));
        b.setPadding(14, 4, 14, 4);
        b.setOnClickListener(v -> callback.run());
        return b;
    }

    private void buildHome() {
        EditText search = new EditText(this);
        search.setSingleLine(true);
        search.setTextSize(14);
        search.setHint("⌕  Search people, posts and topics");
        search.setPadding(16, 4, 16, 4);
        search.setBackground(shape(white, 30));
        content.addView(search);

        addSpace(content, 16);

        sectionTitle("People to discover", "Find more", () ->
                showPage("Find People"));

        LinearLayout people = row();

        String[] names = {"Ama", "Kofi", "Akua", "Yaw"};
        String[] faces = {"👩🏾", "👨🏾", "👩🏿", "👨🏿"};

        for (int i = 0; i < names.length; i++) {
            LinearLayout person = column();
            person.setGravity(Gravity.CENTER);

            TextView avatar = text(faces[i], 28, dark, false);
            avatar.setGravity(Gravity.CENTER);
            avatar.setBackground(shape(
                    Color.rgb(226, 241, 246), 60));

            person.addView(avatar, new LinearLayout.LayoutParams(
                    56, 56));

            TextView name = text(names[i], 12, dark, true);
            name.setGravity(Gravity.CENTER);
            person.addView(name);

            people.addView(person, new LinearLayout.LayoutParams(
                    0, -2, 1));
        }

        content.addView(people);
        addSpace(content, 18);

        sectionTitle("Your community", null, null);

        EditText postInput = new EditText(this);
        postInput.setHint("What's on your mind?");
        postInput.setTextSize(14);
        postInput.setSingleLine(false);
        postInput.setMinLines(2);
        postInput.setPadding(14, 10, 14, 10);
        postInput.setBackground(shape(white, 18));

        content.addView(postInput);

        Button share = button("＋  Share an update", true, () -> {
            String value = postInput.getText().toString().trim();
            if (value.isEmpty()) {
                showMessage("Write something before sharing.");
            } else {
                showMessage("Update created on this screen.");
                postInput.setText("");
            }
        });

        content.addView(share);
        addSpace(content, 16);

        addPost("Ama Serwaa", "Good vibes only! Keep sharing your creativity. 🌿",
                "♡  Like       ◯  Comment       ↗  Share");

        addPost("Kofi Mensah", "Great day to learn something new. What inspires you?",
                "♡  Like       ◯  Comment       ↗  Share");

        TextView note = text(
                "Demo content is stored locally only. "
                + "Online sharing will require a backend.",
                12, grey, false);
        content.addView(note);
    }

    private void addPost(String author, String message,
                         String actions) {
        LinearLayout post = column();

        TextView name = text("●  " + author, 15, navy, true);
        post.addView(name);

        TextView body = text(message, 14, dark, false);
        post.addView(body);

        TextView actionRow = text(actions, 12, turquoise, true);
        post.addView(actionRow);

        actionRow.setOnClickListener(v ->
                showMessage("Post actions need a connected data service."));

        card(content, post);
    }

    private void buildPeople() {
        EditText search = new EditText(this);
        search.setSingleLine(true);
        search.setHint("Search by name or username");
        search.setBackground(shape(white, 25));
        search.setPadding(14, 6, 14, 6);
        content.addView(search);

        addSpace(content, 14);
        sectionTitle("Suggested connections", null, null);

        String[] names = {
                "Ama Serwaa", "Kofi Mensah", "Akua Dede",
                "Yaw Ofori", "Michael Bright", "Maame Adwoa"
        };

        String[] bios = {
                "Creative thinker and designer",
                "Photographer and storyteller",
                "Student and lifelong learner",
                "Travel enthusiast",
                "Artist and visual storyteller",
                "Community builder"
        };

        for (int i = 0; i < names.length; i++) {
            final String personName = names[i];

            LinearLayout r = row();

            TextView avatar = text("●", 26, turquoise, true);
            r.addView(avatar);

            LinearLayout details = column();

            TextView name = text(personName, 14, dark, true);
            TextView bio = text(bios[i], 12, grey, false);

            details.addView(name);
            details.addView(bio);

            r.addView(details, new LinearLayout.LayoutParams(
                    0, -2, 1));

            Button follow = button("Follow", true, () -> {
                followers++;
                showMessage("You selected " + personName
                        + ". Real following requires user accounts.");
            });

            r.addView(follow);
            card(content, r);
        }

        TextView disclaimer = text(
                "These are sample profiles, not registered users.",
                12, grey, false);
        content.addView(disclaimer);
    }

    private void buildExplore() {
        TextView intro = text(
                "Find something inspiring.", 23, navy, true);
        content.addView(intro);

        TextView sub = text(
                "Explore topics and ideas from your community.",
                14, grey, false);
        content.addView(sub);

        EditText search = new EditText(this);
        search.setSingleLine(true);
        search.setHint("Search topics and creators");
        search.setBackground(shape(white, 25));
        search.setPadding(14, 6, 14, 6);
        content.addView(search);

        addSpace(content, 18);
        sectionTitle("Explore categories", null, null);

        String[] categories = {
                "Art & Design", "Photography", "Fashion",
                "Technology", "Education", "Travel"
        };

        for (String category : categories) {
            LinearLayout r = row();

            TextView title = text(
                    "✦  " + category, 15, dark, true);

            r.addView(title, new LinearLayout.LayoutParams(
                    0, -2, 1));

            TextView arrow = text("›", 25, turquoise, true);
            r.addView(arrow);

            r.setOnClickListener(v ->
                    showMessage(category
                            + " selected. Live content is not connected yet."));

            card(content, r);
        }

        addSpace(content, 10);
        sectionTitle("Popular this week", null, null);

        addPost("Creative Minds",
                "Discover new ideas, experiment with design and share your work.",
                "♡  Like       ◯  Comment");

        addPost("Community Stories",
                "What is one skill you would like to learn this month?",
                "♡  Like       ◯  Comment");
    }

    private void buildMessages() {
        TextView info = text(
                "Your conversations", 22, navy, true);
        content.addView(info);

        TextView explanation = text(
                "Choose a sample conversation to preview the chat interface.",
                14, grey, false);
        content.addView(explanation);

        String[] names = {
                "Ama Serwaa", "Kofi Mensah",
                "Akua Dede", "Michael Bright"
        };

        String[] messages = {
                "Hello! How are you today?",
                "That design looks great!",
                "Let's catch up soon.",
                "Thanks for sharing."
        };

        for (int i = 0; i < names.length; i++) {
            final String name = names[i];
            final String message = messages[i];

            LinearLayout r = row();

            TextView avatar = text("●", 26, turquoise, true);
            r.addView(avatar);

            LinearLayout details = column();

            details.addView(text(name, 15, dark, true));
            details.addView(text(message, 13, grey, false));

            r.addView(details, new LinearLayout.LayoutParams(
                    0, -2, 1));

            TextView arrow = text("›", 25, turquoise, true);
            r.addView(arrow);

            r.setOnClickListener(v -> showChat(name, message));

            card(content, r);
        }

        addSpace(content, 12);

        TextView note = text(
                "Messages are demonstrations. Sending messages "
                + "between different devices is not implemented yet.",
                13, grey, false);
        content.addView(note);
    }

    private String chatName = "ZenApp Member";
    private String chatMessage = "";

    private void showChat(String name, String message) {
        chatName = name;
        chatMessage = message;
        showPage("Chat");
    }

    private void buildChat() {
        content.addView(text(
                "Conversation with " + chatName,
                18, navy, true));

        TextView received = text(
                chatMessage, 15, dark, false);
        received.setBackground(shape(white, 18));
        content.addView(received);

        addSpace(content, 12);

        LinearLayout conversation = column();
        content.addView(conversation);

        EditText input = new EditText(this);
        input.setHint("Type a message...");
        input.setSingleLine(true);
        input.setBackground(shape(white, 25));
        input.setPadding(14, 6, 14, 6);

        content.addView(input);

        Button send = button("Send message", true, () -> {
            String message = input.getText().toString().trim();

            if (message.isEmpty()) {
                showMessage("Type a message first.");
                return;
            }

            TextView bubble = text(
                    "You: " + message, 14, white, false);
            bubble.setBackground(shape(navyLight, 18));
            conversation.addView(bubble);

            input.setText("");

            showMessage("Preview message added locally.");
        });

        content.addView(send);

        Button back = button("Back to messages", false,
                () -> showPage("Messaging"));
        content.addView(back);

        addSpace(content, 16);

        content.addView(text(
                "This chat is a local demonstration, not a live conversation.",
                12, grey, false));
    }

    private void buildProfile() {
        LinearLayout profile = column();
        profile.setGravity(Gravity.CENTER_HORIZONTAL);
        profile.setPadding(8, 16, 8, 16);
        profile.setBackground(shape(navy, 24));

        TextView avatar = text("●", 44, turquoise, true);
        avatar.setGravity(Gravity.CENTER);
        profile.addView(avatar);

        profile.addView(text(
                profileName, 22, white, true));

        profile.addView(text(
                "@zenappmember", 14, Color.LTGRAY, false));

        profile.addView(text(
                biography, 14, white, false));

        LinearLayout stats = row();
        stats.setGravity(Gravity.CENTER);

        stats.addView(text("0\nPosts", 14, white, true));
        stats.addView(text(
                followers + "\nFollowers", 14, white, true));
        stats.addView(text("0\nFollowing", 14, white, true));

        profile.addView(stats);

        content.addView(profile);

        addSpace(content, 16);

        Button edit = button("Edit Profile", true,
                () -> showPage("Edit Profile"));
        content.addView(edit);

        addSpace(content, 18);

        sectionTitle("My activity", null, null);

        addPost("Your profile",
                "Your shared posts will appear here once posting is implemented.",
                "Profile activity");

        sectionTitle("Settings", null, null);

        addSetting("Account settings");
        addSetting("Privacy and security");
        addSetting("Notifications");
        addSetting("Help and support");
        addSetting("About ZenApp");
    }

    private void addSetting(String setting) {
        LinearLayout r = row();

        TextView title = text(
                "⚙  " + setting, 14, dark, false);
        r.addView(title, new LinearLayout.LayoutParams(
                0, -2, 1));

        r.addView(text("›", 23, turquoise, true));

        r.setOnClickListener(v ->
                showMessage(setting + " will be implemented later."));

        card(content, r);
    }

    private void buildEditProfile() {
        content.addView(text(
                "Edit your profile", 23, navy, true));

        addSpace(content, 12);

        EditText nameInput = new EditText(this);
        nameInput.setSingleLine(true);
        nameInput.setHint("Your display name");
        nameInput.setText(profileName);
        nameInput.setBackground(shape(white, 16));
        nameInput.setPadding(14, 8, 14, 8);
        content.addView(nameInput);

        addSpace(content, 12);

        EditText bioInput = new EditText(this);
        bioInput.setHint("Write a short biography");
        bioInput.setText(biography);
        bioInput.setMinLines(3);
        bioInput.setGravity(Gravity.TOP);
        bioInput.setBackground(shape(white, 16));
        bioInput.setPadding(14, 8, 14, 8);
        content.addView(bioInput);

        addSpace(content, 16);

        Button save = button("Save changes", true, () -> {
            String name = nameInput.getText().toString().trim();
            String bio = bioInput.getText().toString().trim();

            if (name.isEmpty()) {
                showMessage("Please enter your display name.");
                return;
            }

            profileName = name;
            biography = bio;

            showPage("My Profile");
            showMessage("Profile updated on this device.");
        });

        content.addView(save);

        addSpace(content, 10);

        content.addView(text(
                "Profile changes are stored in memory only. "
                + "They will reset when the app process restarts. "
                + "Persistent storage will be added later.",
                12, grey, false));
    }

    private void showMessage(String message) {
        Toast.makeText(
                this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onBackPressed() {
        if (!"Home".equals(currentPage)) {
            showPage("Home");
        } else {
            super.onBackPressed();
        }
    }
                }
