package ru.javawebinar.basejava.model;


 class Contact {

     private String type;
     private String contactInfo;
     private String hyperlink;

     public Contact(String type, String contactInfo, String hyperlink) {
         this.type = type;
         this.contactInfo = contactInfo;
         this.hyperlink = hyperlink;
     }

     public Contact(String type, String contactInfo) {
         this.type = type;
         this.contactInfo = contactInfo;
         this.hyperlink = null;
     }

}
