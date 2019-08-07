package com.spo.wo.model;

public class Workforce
{
    private int senior;
    private int junior;
    
    
    public Workforce(int senior, int junior)
    {
        super();
        this.senior = senior;
        this.junior = junior;
    }

    public int getSenior()
    {
        return senior;
    }
    
    public int getJunior()
    {
        return junior;
    }
   
}
