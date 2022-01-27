package com.gmail.dreamleaguekit.modal;

public class SavesModel
{
    private String saved_team_name;
    private String saved_team_logo;
    private String saved_team_league_name;
    private String saved_team_league_logo;

    public SavesModel(){}
    public SavesModel(String saved_team_name, String saved_team_logo,
                      String saved_team_league_name, String saved_team_league_logo) {
        this.saved_team_name = saved_team_name;
        this.saved_team_logo = saved_team_logo;
        this.saved_team_league_name = saved_team_league_name;
        this.saved_team_league_logo = saved_team_league_logo;
    }

    public String getSaved_team_name() {
        return saved_team_name;
    }

    public void setSaved_team_name(String saved_team_name) {
        this.saved_team_name = saved_team_name;
    }

    public String getSaved_team_logo() {
        return saved_team_logo;
    }

    public void setSaved_team_logo(String saved_team_logo) {
        this.saved_team_logo = saved_team_logo;
    }

    public String getSaved_team_league_name() {
        return saved_team_league_name;
    }

    public void setSaved_team_league_name(String saved_team_league_name) {
        this.saved_team_league_name = saved_team_league_name;
    }

    public String getSaved_team_league_logo() {
        return saved_team_league_logo;
    }

    public void setSaved_team_league_logo(String saved_team_league_logo) {
        this.saved_team_league_logo = saved_team_league_logo;
    }
}
