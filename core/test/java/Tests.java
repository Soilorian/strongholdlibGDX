import org.example.control.enums.EntranceMenuMessages;
import org.example.control.enums.ProfileMenuMessages;
import org.example.control.menucontrollers.ProfileMenuController;
import org.example.model.Player;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;


public class Tests {
    @Test
    public void changeUsernameSuccess() {
        Player player = new Player("elina", "Eli83$", "lazy", "hozh@gmail.com", "Sleep");
        DataBase.setCurrentPlayer(player);
        String response = ProfileMenuController.changeUsername("armin");
        Assertions.assertEquals(ProfileMenuMessages.SUCCEED.toString(), response);
    }

    @Test
    public void changeUsernameErrors() {
        Player player = new Player("elina", "Eli83$", "lazy", "hozh@gmail.com", "Sleep");
        DataBase.setCurrentPlayer(player);
        DataBase.getPlayers().add(player);
        String response = ProfileMenuController.changeUsername("");
        Assertions.assertEquals(ProfileMenuMessages.EMPTY_USERNAME.toString(), response);
        response = ProfileMenuController.changeUsername("elina");
        Assertions.assertEquals(ProfileMenuMessages.USERNAME_ALREADY_EXISTS.toString(), response);
        response = ProfileMenuController.changeUsername("12.3$");
        Assertions.assertEquals(ProfileMenuMessages.USERNAME_INVALID.toString(), response);
    }

    @Test
    public void changeNicknameSuccess() {
        Player player = new Player("elina", "Eli83$", "lazy", "hozh@gmail.com", "Sleep");
        DataBase.setCurrentPlayer(player);
        String response = ProfileMenuController.changeNickname("Banoo");
        Assertions.assertEquals(ProfileMenuMessages.SUCCEED.toString(), response);
    }

    @Test
    public void changeNicknameErrors() {
        Player player = new Player("elina", "Eli83$", "lazy", "hozh@gmail.com", "Sleep");
        DataBase.setCurrentPlayer(player);
        String response = ProfileMenuController.changeNickname("");
        Assertions.assertEquals(ProfileMenuMessages.EMPTY_NICKNAME.toString(), response);
        response = ProfileMenuController.changeNickname("lazy");
        Assertions.assertEquals(ProfileMenuMessages.NICKNAME_EXISTS.toString(), response);
    }

    @Test
    public void changePasswordSuccess() {
        Player player = new Player("elina", "Eli83$", "lazy", "hozh@gmail.com", "Sleep");
        DataBase.setCurrentPlayer(player);
        String response = ProfileMenuController.changePassword("Eli83$", "Eln2004$");
        Assertions.assertEquals(ProfileMenuMessages.SUCCEED.toString(), response);
    }

    @Test
    public void changePasswordErrors() {
        Player player = new Player("elina", "Eli83$", "lazy", "hozh@gmail.com", "Sleep");
        DataBase.setCurrentPlayer(player);
        String response = ProfileMenuController.changePassword("", "ELn2004$");
        Assertions.assertEquals(ProfileMenuMessages.EMPTY_OLD_PASSWORD.toString(), response);
        response = ProfileMenuController.changePassword("Eli83$", "");
        Assertions.assertEquals(ProfileMenuMessages.EMPTY_NEW_PASSWORD.toString(), response);
        response = ProfileMenuController.changePassword("Eli$", "ELn2004$");
        Assertions.assertEquals(ProfileMenuMessages.INCORRECT_PASSWORD.toString(), response);
        response = ProfileMenuController.changePassword("Eli83$", "Eli83$");
        Assertions.assertEquals(ProfileMenuMessages.PASSWORD_ALREADY_EXISTS.toString(), response);
        response = ProfileMenuController.changePassword("Eli83$", "ELN2004$");
        Assertions.assertEquals(EntranceMenuMessages.LOWERCASE_PASSWORD.toString(), response);
        response = ProfileMenuController.changePassword("Eli83$", "ELn2004");
        Assertions.assertEquals(EntranceMenuMessages.SYMBOl_PASSWORD.toString(), response);
    }

    @Test
    public void changeEmailSuccess() {
        Player player = new Player("elina", "Eli83$", "lazy", "hozh@gmail.com", "Sleep");
        DataBase.setCurrentPlayer(player);
        String response = ProfileMenuController.changeEmail("elen@gmail.com");
        Assertions.assertEquals(ProfileMenuMessages.SUCCEED.toString(), response);
    }

    @Test
    public void changeEmailErrors() {
        Player player = new Player("elina", "Eli83$", "lazy", "hozh@gmail.com", "Sleep");
        DataBase.setCurrentPlayer(player);
        DataBase.getPlayers().add(player);
        String response = ProfileMenuController.changeEmail("");
        Assertions.assertEquals(ProfileMenuMessages.EMPTY_EMAIL.toString(), response);
        response = ProfileMenuController.changeEmail("elen@gmail");
        Assertions.assertEquals(ProfileMenuMessages.INVALID_EMAIL.toString(), response);
        response = ProfileMenuController.changeEmail("hozh@gmail.com");
        Assertions.assertEquals(ProfileMenuMessages.EMAIL_ALREADY_EXISTS.toString(), response);
    }

    @Test
    public void changeSloganSuccess() {
        Player player = new Player("elina", "Eli83$", "lazy", "hozh@gmail.com", "Sleep");
        DataBase.setCurrentPlayer(player);
        String response = ProfileMenuController.changeSlogan("This is not fare!");
        Assertions.assertEquals(ProfileMenuMessages.SUCCEED.toString(), response);
    }

    @Test
    public void changeSloganErrors() {
        Player player = new Player("elina", "Eli83$", "lazy", "hozh@gmail.com", "Sleep");
        DataBase.setCurrentPlayer(player);
        DataBase.getPlayers().add(player);
        String response = ProfileMenuController.changeSlogan("");
        Assertions.assertEquals(ProfileMenuMessages.EMPTY_SLOGAN.toString(), response);
        response = ProfileMenuController.changeSlogan("Sleep");
        Assertions.assertEquals(ProfileMenuMessages.SLOGAN_EXISTS.toString(), response);
    }

    @Test
    public void removeSlogan() {
        Player player = new Player("elina", "Eli83$", "lazy", "hozh@gmail.com", "Sleep");
        DataBase.setCurrentPlayer(player);
        String response = ProfileMenuController.removeSlogan();
        Assertions.assertEquals(ProfileMenuMessages.SUCCEED.toString(), response);
    }

    @Test
    public void showMaxScore() {
        Player player = new Player("elina", "Eli83$", "lazy", "hozh@gmail.com", "Sleep");
        DataBase.setCurrentPlayer(player);
        String response = ProfileMenuController.showMaxScore();
        Assertions.assertEquals("MaxScore: " + player.getMaxStore(), response);
    }

    @Test
    public void showSlogan() {
        Player player1 = new Player("elina", "Eli83$", "lazy", "hozh@gmail.com", "Sleep");
        DataBase.setCurrentPlayer(player1);
        String response = ProfileMenuController.showSlogan();
        Assertions.assertEquals("slogan: Sleep", response);

        Player player2 = new Player("elina", "Eli83$", "lazy", "hozh@gmail.com", "");
        DataBase.setCurrentPlayer(player2);
        response = ProfileMenuController.showSlogan();
        Assertions.assertEquals("slogan: ", response);
    }

    @Test
    public void showUsername() {
        Player player = new Player("elina", "Eli83$", "lazy", "hozh@gmail.com", "Sleep");
        DataBase.setCurrentPlayer(player);
        String response = ProfileMenuController.showUsername();
        Assertions.assertEquals("your username is: elina", response);
    }

    @Test
    public void showNickname() {
        Player player = new Player("elina", "Eli83$", "lazy", "hozh@gmail.com", "Sleep");
        DataBase.setCurrentPlayer(player);
        String response = ProfileMenuController.showNickname();
        Assertions.assertEquals("your nickname is: lazy", response);
    }

    @Test
    public void showRank() {
        Player player1 = new Player("elina", "Eli83$", "lazy", "hozh@gmail.com", "Sleep");
        DataBase.setCurrentPlayer(player1);
        DataBase.getPlayers().add(player1);
        Player player2 = new Player("mahdi", "mahdi83$", "gamer", "mahdi@gmail.com", "commit kon");
        DataBase.getPlayers().add(player2);
        String response = ProfileMenuController.showRank();
        Assertions.assertEquals("your in game rank is: " + (DataBase.getPlayers().indexOf(DataBase.getCurrentPlayer()) + 1), response);
    }

    @Test
    public void showProfile() {
        Player player = new Player("elina", "Eli83$", "lazy", "hozh@gmail.com", "Sleep");
        DataBase.setCurrentPlayer(player);
        String response = ProfileMenuController.showProfile("username");
        Assertions.assertEquals("your username is: elina", response);
        response = ProfileMenuController.showProfile("nickname");
        Assertions.assertEquals("your nickname is: lazy", response);
        response = ProfileMenuController.showProfile("slogan");
        Assertions.assertEquals("slogan: Sleep", response);
        response = ProfileMenuController.showProfile("max score");
        Assertions.assertEquals("MaxScore: " + player.getMaxStore(), response);
        response = ProfileMenuController.showProfile("rank");
        Assertions.assertEquals("your in game rank is: " + (DataBase.getPlayers().indexOf(DataBase.getCurrentPlayer()) + 1), response);
        response = ProfileMenuController.showProfile("profile");
        Assertions.assertEquals(ProfileMenuMessages.WRONG_FIELD.toString(), response);
    }
}
