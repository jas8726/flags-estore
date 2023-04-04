package com.flags.api.flagsapi.model;
import java.util.*;

import com.fasterxml.jackson.databind.ser.std.StdArraySerializers.IntArraySerializer;

public class CustomFlag {
    //i hate my life


    int stripe_num_hor = 0;
    int stripe_num_vert = 0;
    int id;
    ArrayList<Integer> hor_colors;
    ArrayList<Integer> vert_colors;


    public CustomFlag(){
        hor_colors = new ArrayList<Integer>(stripe_num_hor);
        vert_colors = new ArrayList<Integer>(stripe_num_vert);
    }

    /*have a none value, make it -1 or something so they have option of having it be see through (aka have no color be set in the div)
     * in the arraylists the values are hexadecimal color values or -1
     * horizontal goes left to right, leftmost being index 0
     * vertical goes top to bottom, topmost being index 0
     * not sur what to do about the id
     */

     public void setHorStripes(int x){
        while(hor_colors.size() < x){
            hor_colors.add(-1);
        }
        if(hor_colors.size() > x){
            for(int i=this.stripe_num_hor; i > x; i--){
                hor_colors.remove(i);
            }
        }
        this.stripe_num_hor = x;
     }

     public int getHorStripes(){
        return this.stripe_num_hor;
     }



    //setter/getters for vertical array/size
     public void setVertStripes(int x){
        while(vert_colors.size() < x){
            vert_colors.add(-1);
        }
        if(vert_colors.size() > x){
            for(int i=this.stripe_num_vert; i > x; i--){
                vert_colors.remove(i);
            }
        }
        this.stripe_num_vert = x;
     }

     public int getVertStripes(){
        return this.stripe_num_vert;
     }


     /*methods for getting a color from each array based on an index */
     public Integer getHColor(int i){
        return hor_colors.get(i);
     }

     public Integer getVColor(int i){
        return vert_colors.get(i);
     }


}
