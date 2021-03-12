package com.example.fitifit.Model;

public class ToDo {
    private String titledoes;
    private String datedoes;
    private String descdoes;
    private String task;

    public ToDo() {

    }

    public ToDo(String titledoes, String descdoes, String datedoes, String task) {
        this.titledoes = titledoes;
        this.datedoes = datedoes;
        this.descdoes = descdoes;
        this.task = task;
    }

    public String getTitledoes() {
        return titledoes;
    }

    public String getTask() { return task; }

    public void setTask(String task) { this.task = task; }

    public void setTitledoes(String titledoes) {
        this.titledoes = titledoes;
    }

    public String getDatedoes() {
        return datedoes;
    }

    public void setDatedoes(String datedoes) {
        this.datedoes = datedoes;
    }

    public String getDescdoes() {
        return descdoes;
    }

    public void setDescdoes(String descdoes) {
        this.descdoes = descdoes;
    }


}
