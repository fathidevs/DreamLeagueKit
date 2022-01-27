package com.gmail.dreamleaguekit.modal;

public class KitsModal {

    private String team_name;
    private String league_name;

    private String team_logo;
    private String league_logo;

    private String home_kit;
    private String away_kit;
    private String third_kit;

    private String gk_home_kit;
    private String gk_away_kit;
    private String gk_third_kit;

    private String date;

    public KitsModal() {}
    public KitsModal(
            String team_name, String league_name, String team_logo, String league_logo,
            String home_kit, String away_kit, String third_kit, String gk_home_kit,
            String gk_away_kit, String gk_third_kit, String date) {
        this.team_name = team_name;
        this.league_name = league_name;
        this.team_logo = team_logo;
        this.league_logo = league_logo;
        this.home_kit = home_kit;
        this.away_kit = away_kit;
        this.third_kit = third_kit;
        this.gk_home_kit = gk_home_kit;
        this.gk_away_kit = gk_away_kit;
        this.gk_third_kit = gk_third_kit;
        this.date = date;
    }

    public String getTeam_name() {
        return team_name;
    }

    public void setTeam_name(String team_name) {
        this.team_name = team_name;
    }

    public String getLeague_name() {
        return league_name;
    }

    public void setLeague_name(String league_name) {
        this.league_name = league_name;
    }

    public String getTeam_logo() {
        return team_logo;
    }

    public void setTeam_logo(String team_logo) {
        this.team_logo = team_logo;
    }

    public String getLeague_logo() {
        return league_logo;
    }

    public void setLeague_logo(String league_logo) {
        this.league_logo = league_logo;
    }

    public String getHome_kit() {
        return home_kit;
    }

    public void setHome_kit(String home_kit) {
        this.home_kit = home_kit;
    }

    public String getAway_kit() {
        return away_kit;
    }

    public void setAway_kit(String away_kit) {
        this.away_kit = away_kit;
    }

    public String getThird_kit() {
        return third_kit;
    }

    public void setThird_kit(String third_kit) {
        this.third_kit = third_kit;
    }

    public String getGk_home_kit() {
        return gk_home_kit;
    }

    public void setGk_home_kit(String gk_home_kit) {
        this.gk_home_kit = gk_home_kit;
    }

    public String getGk_away_kit() {
        return gk_away_kit;
    }

    public void setGk_away_kit(String gk_away_kit) {
        this.gk_away_kit = gk_away_kit;
    }

    public String getGk_third_kit() {
        return gk_third_kit;
    }

    public void setGk_third_kit(String gk_third_kit) {
        this.gk_third_kit = gk_third_kit;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
