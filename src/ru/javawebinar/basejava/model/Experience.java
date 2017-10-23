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

     @Override
     public boolean equals(Object o) {
         if (this == o) return true;
         if (!(o instanceof Experience)) return false;

         Experience that = (Experience) o;

         if (!company.equals(that.company)) return false;
         if (!datesFromTo.equals(that.datesFromTo)) return false;
         return position.equals(that.position);
     }

     @Override
     public int hashCode() {
         int result = company.hashCode();
         result = 31 * result + datesFromTo.hashCode();
         result = 31 * result + position.hashCode();
         return result;
     }
 }
