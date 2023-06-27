package org.example.model;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import org.example.control.Controller;

@XStreamAlias("player")

public class Player {

    String username;

    String password;

    String nickname;

    String email;

    String securityQuestion;

    String securityQuestionAnswer;

    String slogan;

    int maxStore = 0;
    int profImage = 0;

    public Player(String username, String password, String nickname, String email, String slogan) {
        this.username = username;
        this.password = DataBase.hashWithApacheCommons(password);
        this.nickname = nickname;
        this.email = email;
        if (!slogan.equals("null"))
            this.slogan = slogan;
    }


    public boolean checkPassword(String password) {
        return this.password.equals(DataBase.hashWithApacheCommons(password));
    }

    public boolean checkSecurityQuestion(String answer) {
        return this.securityQuestionAnswer.equals(DataBase.hashWithApacheCommons(answer));
    }

    public void updateMaxScore(int score) {
        maxStore = Math.max(score, maxStore);
    }

    public int getMaxStore() {
        return maxStore;
    }

    public String toStringScore(){
        return Integer.valueOf(getMaxStore()).toString();
    }
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = DataBase.hashWithApacheCommons(password);
    }


    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSlogan() {
        return slogan;
    }

    public void setSlogan(String slogan) {
        this.slogan = slogan;
    }

    public int getProfImage() {
        return profImage;
    }

    public void setProfImage(int profImage) {
        this.profImage = profImage;
    }

    public void setSecurityQuestionAnswer(String securityQuestionAnswer) {
        this.securityQuestionAnswer = DataBase.hashWithApacheCommons(securityQuestionAnswer);
    }

    public String getSecurityQuestion() {
        return securityQuestion;
    }

    public void setSecurityQuestion(String securityQuestion) {
        this.securityQuestion = securityQuestion;
    }

    public void message(String text){
    }
}
