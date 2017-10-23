package ru.javawebinar.basejava.model;


 class Experience {

     private String company;
     private String hyperlink;
     private String datesFromTo;
     private String position;
     private String description;

     public Experience(String company, String datesFromTo, String position, String description) {
         this.company = company;
         this.datesFromTo = datesFromTo;
         this.position = position;
         this.description = description;
     }
 }
