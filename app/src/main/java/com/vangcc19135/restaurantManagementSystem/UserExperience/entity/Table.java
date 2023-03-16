package com.vangcc19135.restaurantManagementSystem.UserExperience.entity;

import android.os.Parcel;
import android.os.Parcelable;

public class Table implements Parcelable {
   private int id;//mã bàn
   private String listDrink;//danh sách đồ uống cho bàn

   private String note;

   public Table() {
   }

   public Table(String listDrink) {
      this.listDrink = listDrink;
   }

   public Table(int id, String listDrink) {
      this.id = id;
      this.listDrink = listDrink;
   }

   protected Table(Parcel in) {
      id = in.readInt();
      listDrink = in.readString();
   }

   @Override
   public void writeToParcel(Parcel dest, int flags) {
      dest.writeInt(id);
      dest.writeString(listDrink);
   }

   @Override
   public int describeContents() {
      return 0;
   }

   public static final Creator<Table> CREATOR = new Creator<Table>() {
      @Override
      public Table createFromParcel(Parcel in) {
         return new Table(in);
      }

      @Override
      public Table[] newArray(int size) {
         return new Table[size];
      }
   };

   public int getId() {
      return id;
   }

   public void setId(int id) {
      this.id = id;
   }

   public String getListDrink() {
      return listDrink;
   }

   public void setListDrink(String listDrink) {
      this.listDrink = listDrink;
   }

   public String getNote() { return note;
   }
   public void setNote(String setNote){ this.note = setNote;}
}
