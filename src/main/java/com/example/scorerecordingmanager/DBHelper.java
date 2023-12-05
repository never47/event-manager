package com.example.scorerecordingmanager;

// Saving database information for using it on different scenes
public class DBHelper {
    private static int user_id;
    private static int event_id;
    private static String eventName;
    private static int team_id1;
    private static String teamName1;
    private static int team_id2;
    private static String teamName2;
    private static int team_member1_count;
    private static int team_member2_count;
    private static boolean isScoreSaved;
    private static int activeTeam; // 1 : 2

    public DBHelper() {
        user_id=-1;
        resetEvent();
    }

    public static boolean isIsScoreSaved() {
        return isScoreSaved;
    }

    public static String getEventName() {
        return eventName;
    }

    public static String getTeamName1() {
        return teamName1;
    }

    public static String getTeamName2() {
        return teamName2;
    }

    public static int getActiveTeam() {
        return activeTeam;
    }

    public static int getTeam_member1_count() {
        return team_member1_count;
    }

    public static int getTeam_member2_count() {
        return team_member2_count;
    }

    public static int getUser_id() {
        return user_id;
    }

    public static int getEvent_id() {
        return event_id;
    }

    public static int getTeam_id1() {
        return team_id1;
    }

    public static int getTeam_id2() {
        return team_id2;
    }

    public static void setUser_id(int user_id) {
        DBHelper.user_id = user_id;
        event_id=-1;
        team_id1=-1;
        team_id2=-1;
        team_member1_count=-1;
        team_member2_count=-1;
        eventName=null;
        teamName1=null;
        teamName2=null;
    }

    public static void setEvent(int event_id, String eventName) {
        DBHelper.event_id = event_id;
        DBHelper.eventName = eventName;
        team_id1=-1;
        team_id2=-1;
        team_member1_count=-1;
        team_member2_count=-1;
        teamName1=null;
        teamName2=null;
    }

    public static void setTeam1(int team_id1, String teamName1) {
        DBHelper.team_id1 = team_id1;
        DBHelper.teamName1 = teamName1;
        team_member1_count=-1;
    }

    public static void setTeam2(int team_id2, String teamName2) {
        DBHelper.team_id2 = team_id2;
        DBHelper.teamName2 = teamName2;
        team_member2_count=-1;
    }

    public static void setTeam_member1_count(int team_member1_count) {
        DBHelper.team_member1_count = team_member1_count;
    }

    public static void setTeam_member2_count(int team_member2_count) {
        DBHelper.team_member2_count = team_member2_count;
    }

    public static void setActiveTeam(int activeTeam) {
        DBHelper.activeTeam = activeTeam;
    }

    public static void setIsScoreSaved(boolean isScoreSaved) {
        DBHelper.isScoreSaved = isScoreSaved;
    }

    public static void resetEvent(){
        event_id=-1;
        team_id1=-1;
        team_id2=-1;
        team_member1_count=-1;
        team_member2_count=-1;
        activeTeam=-1;
        eventName=null;
        teamName1=null;
        teamName2=null;
        isScoreSaved = false;
    }

    public static void resetUser() {
        resetEvent();
        user_id = -1;
    }
}