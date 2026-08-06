package com.lq.user.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "user_preferences")
public class UserPreference {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "user_profile_id")
    private UserProfile userProfile;

    private boolean emailNotifications = true;
    private boolean smsNotifications = false;
    private String preferredTheme = "DARK";
    private String language = "en";

    public UserPreference() {
    }

    public UserPreference(Long id, UserProfile userProfile, boolean emailNotifications, boolean smsNotifications, String preferredTheme, String language) {
        this.id = id;
        this.userProfile = userProfile;
        this.emailNotifications = emailNotifications;
        this.smsNotifications = smsNotifications;
        if (preferredTheme != null) this.preferredTheme = preferredTheme;
        if (language != null) this.language = language;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public UserProfile getUserProfile() {
        return userProfile;
    }

    public void setUserProfile(UserProfile userProfile) {
        this.userProfile = userProfile;
    }

    public boolean isEmailNotifications() {
        return emailNotifications;
    }

    public void setEmailNotifications(boolean emailNotifications) {
        this.emailNotifications = emailNotifications;
    }

    public boolean isSmsNotifications() {
        return smsNotifications;
    }

    public void setSmsNotifications(boolean smsNotifications) {
        this.smsNotifications = smsNotifications;
    }

    public String getPreferredTheme() {
        return preferredTheme;
    }

    public void setPreferredTheme(String preferredTheme) {
        this.preferredTheme = preferredTheme;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public static UserPreferenceBuilder builder() {
        return new UserPreferenceBuilder();
    }

    public static class UserPreferenceBuilder {
        private Long id;
        private UserProfile userProfile;
        private boolean emailNotifications = true;
        private boolean smsNotifications = false;
        private String preferredTheme = "DARK";
        private String language = "en";

        public UserPreferenceBuilder id(Long id) {
            this.id = id;
            return this;
        }

        public UserPreferenceBuilder userProfile(UserProfile userProfile) {
            this.userProfile = userProfile;
            return this;
        }

        public UserPreferenceBuilder emailNotifications(boolean emailNotifications) {
            this.emailNotifications = emailNotifications;
            return this;
        }

        public UserPreferenceBuilder smsNotifications(boolean smsNotifications) {
            this.smsNotifications = smsNotifications;
            return this;
        }

        public UserPreferenceBuilder preferredTheme(String preferredTheme) {
            this.preferredTheme = preferredTheme;
            return this;
        }

        public UserPreferenceBuilder language(String language) {
            this.language = language;
            return this;
        }

        public UserPreference build() {
            return new UserPreference(id, userProfile, emailNotifications, smsNotifications, preferredTheme, language);
        }
    }
}
