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

     @Override
     public boolean equals(Object o) {
         if (this == o) return true;
         if (!(o instanceof Contact)) return false;

         Contact contact = (Contact) o;

         return contactInfo.equals(contact.contactInfo);
     }

     @Override
     public int hashCode() {
         return contactInfo.hashCode();
     }
 }
