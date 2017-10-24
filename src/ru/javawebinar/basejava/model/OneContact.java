package ru.javawebinar.basejava.model;


 class OneContact {

     private String type;
     private String contactInfo;
     private String hyperlink;

     public OneContact(String type, String contactInfo, String hyperlink) {
         this.type = type;
         this.contactInfo = contactInfo;
         this.hyperlink = hyperlink;
     }

     public OneContact(String type, String contactInfo) {
         this.type = type;
         this.contactInfo = contactInfo;
         this.hyperlink = null;
     }

     @Override
     public boolean equals(Object o) {
         if (this == o) return true;
         if (!(o instanceof OneContact)) return false;

         OneContact oneContact = (OneContact) o;

         return contactInfo.equals(oneContact.contactInfo);
     }

     @Override
     public int hashCode() {
         return contactInfo.hashCode();
     }
 }
