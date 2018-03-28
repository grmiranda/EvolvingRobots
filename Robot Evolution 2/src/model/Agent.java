package model;

import representation.IntegerArrayRep;
import util.StateEnum;

import java.util.ArrayList;

public class Agent implements Comparable<Agent>{

    private IntegerArrayRep representation;
    private StateEnum state;
    private double fitness;


    public Agent(IntegerArrayRep representation) {
        this.state = StateEnum.WAIT; // set actual state of agent to wait (wait to be simulate)
        this.representation = representation; // define data representation object
        this.fitness = -9999; // low fitness to initialize variable
    }

    public void setState(StateEnum newState){
        this.state = newState;
    }

    public StateEnum getState(){
        return state;
    }

    public IntegerArrayRep getRepresentation(){
        return representation;
    }

    public void setFitness(double fitness){
        this.fitness = fitness;
    }

    public double getFitness(){
        return this.fitness;
    }


    @Override
    public int compareTo(Agent a) {
        if(this.fitness > a.getFitness()){
            return 1;
        }else if(this.fitness < a.getFitness()){
            return -1;
        }else{
            return 0;
        }
    }
}
