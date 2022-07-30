package com.example.hostelmaintenance;

public class democ1 {
    private String Email;
    private String Name;
    private String Room_No;
    private String Complaint_Title;

    public democ1() {
    }

    public democ1(String email, String name, String room_No, String complaint_Title) {
        Email = email;
        Name = name;
        Room_No = room_No;
        Complaint_Title = complaint_Title;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getRoom_No() {
        return Room_No;
    }

    public void setRoom_No(String room_No) {
        Room_No = room_No;
    }

    public String getComplaint_Title() {
        return Complaint_Title;
    }

    public void setComplaint_Title(String complaint_Title) {
        Complaint_Title = complaint_Title;
    }



}
