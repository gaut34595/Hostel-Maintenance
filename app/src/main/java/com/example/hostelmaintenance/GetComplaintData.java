package com.example.hostelmaintenance;

public class GetComplaintData {
    public GetComplaintData(String email, String name, String complaint_Title, String room_No, String description,
                            String complaint_Type, String complaint_Time, String complaint_Date, int complaint_Status) {
        Email = email;
        Name = name;
        Complaint_Title = complaint_Title;
        Room_No = room_No;
        Description = description;
        Complaint_Type = complaint_Type;
        Complaint_Time = complaint_Time;
        Complaint_Date = complaint_Date;
        Complaint_Status = complaint_Status;
    }

    public GetComplaintData() {
    }

    String Email, Name, Complaint_Title, Room_No, Description, Complaint_Type,Complaint_Time,Complaint_Date;
    int Complaint_Status;

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

    public String getComplaint_Title() {
        return Complaint_Title;
    }

    public void setComplaint_Title(String complaint_Title) {
        Complaint_Title = complaint_Title;
    }

    public String getRoom_No() {
        return Room_No;
    }

    public void setRoom_No(String room_No) {
        Room_No = room_No;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public String getComplaint_Type() {
        return Complaint_Type;
    }

    public void setComplaint_Type(String complaint_Type) {
        Complaint_Type = complaint_Type;
    }

    public String getComplaint_Time() {
        return Complaint_Time;
    }

    public void setComplaint_Time(String complaint_Time) {
        Complaint_Time = complaint_Time;
    }

    public String getComplaint_Date() {
        return Complaint_Date;
    }

    public void setComplaint_Date(String complaint_Date) {
        Complaint_Date = complaint_Date;
    }

    public int getComplaint_Status() {


        return Complaint_Status;
    }

    public void setComplaint_Status(int complaint_Status) {
        Complaint_Status = complaint_Status;
    }
}