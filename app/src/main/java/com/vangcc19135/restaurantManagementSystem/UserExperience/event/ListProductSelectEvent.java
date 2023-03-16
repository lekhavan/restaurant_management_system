package com.vangcc19135.restaurantManagementSystem.UserExperience.event;

import com.vangcc19135.restaurantManagementSystem.UserExperience.entity.Drink;

import java.util.ArrayList;

public class ListProductSelectEvent {
   private ArrayList<Drink> list=new ArrayList<>();

   public ListProductSelectEvent(ArrayList<Drink> list) {
      this.list = list;
   }

   public ArrayList<Drink> getList() {
      return list;
   }

   public void setList(ArrayList<Drink> list) {
      this.list = list;
   }
}
